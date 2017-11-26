package tdrz.gui.main;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tdrz.gui.main.part.PartAirbase;
import tdrz.gui.main.part.PartBasic;
import tdrz.gui.main.part.PartBattle;
import tdrz.gui.main.part.PartFleet;
import tdrz.gui.main.part.PartMapInfo;
import tdrz.gui.main.part.PartMaterial;
import tdrz.gui.main.part.PartPractice;
import tdrz.gui.main.part.PartPrint;
import tdrz.gui.main.part.PartQuest;
import tdrz.gui.main.part.PartTimer;
import tdrz.gui.vice.ViceCalculator;
import tdrz.server.TDRZServerSevlet;
import tdrz.update.UnitManager;
import tool.FXUtils;
import tool.function.FunctionUtils;

public final class ApplicationMainTDRZJFX {
	private final UnitManager unitManager;
	private final Stage primaryStage;

	private final ViceCalculator calculator;

	private final ApplicationTray tray;

	public ApplicationMainTDRZJFX(Stage stage, UnitManager um) {
		this.unitManager = um;

		this.primaryStage = stage;
		this.primaryStage.setTitle("提督日志");
		this.primaryStage.setResizable(false);
		this.primaryStage.setOnCloseRequest(ev -> FunctionUtils.ifNotRunnable(this.exit(), ev::consume));
		this.primaryStage.setScene(new Scene(this.createParent(), 1050, 600));

		this.calculator = new ViceCalculator(this, this.primaryStage);
		this.tray = new ApplicationTray();
	}

	private Parent createParent() {
		PartShipSlotItemButton buttons = FunctionUtils.use(new PartShipSlotItemButton(this), part -> {
			VBox.setVgrow(part, Priority.ALWAYS);
		});
		PartMaterial material = FunctionUtils.use(new PartMaterial(this), part -> {
			VBox.setVgrow(part, Priority.ALWAYS);
		});
		PartTimer deckMission = FunctionUtils.use(new PartTimer(this), part -> {
			FXUtils.setMinMaxWidth(part, 200);
		});
		PartTimer ndock = FunctionUtils.use(new PartTimer(this), part -> {
			FXUtils.setMinMaxWidth(part, 200);
		});

		PartFleet fleet = FunctionUtils.use(new PartFleet(this), part -> {
			part.setBackground(FXUtils.createBackground(Color.gray(0.85)));
		});

		PartPrint print = new PartPrint(this);
		PartQuest quest = new PartQuest(this);
		PartPractice practice = new PartPractice(this);
		PartBasic basic = new PartBasic(this);
		PartMapInfo mapInfo = new PartMapInfo(this);
		PartAirbase airbase = new PartAirbase(this);

		PartBattle battle = FunctionUtils.use(new PartBattle(this), part -> {
			VBox.setVgrow(part, Priority.ALWAYS);
		});

		int spacing = 3;
		return FXUtils.createHBox(spacing, Pos.CENTER, false,
				FXUtils.createVBox(spacing, Pos.CENTER, false,
						box -> HBox.setHgrow(box, Priority.ALWAYS),
						FXUtils.createHBox(0, Pos.CENTER, false,
								box -> box.setBackground(FXUtils.createBackground(Color.gray(0.85))),
								FXUtils.createVBox(0, Pos.CENTER, false,
										box -> HBox.setHgrow(box, Priority.ALWAYS),
										buttons,
										material
								/**/),
								new Separator(Orientation.VERTICAL),
								deckMission,
								new Separator(Orientation.VERTICAL),
								ndock
						/**/),
						fleet,
						FXUtils.createTabPane(
								tabPane -> {
									VBox.setVgrow(tabPane, Priority.ALWAYS);
									tabPane.setBorder(FXUtils.createNewBorder(0, 1, 0, 0));
									tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
									tabPane.setTabMinWidth(50);
									tabPane.getSelectionModel().selectedItemProperty().addListener((source, oldValue, newValue) -> {});
								},
								new Tab("事务", print),
								new Tab("任务", quest),
								new Tab("演习", practice),
								new Tab("提督", basic),
								new Tab("地图", mapInfo),
								new Tab("基地", airbase)
						/**/)
				/**/),
				FXUtils.createVBox(spacing, Pos.CENTER, false,
						box -> FXUtils.setMinMaxWidth(box, 400),
						battle
				/**/)
		/**/);
	}

	public UnitManager getUnitManager() {
		return this.unitManager;
	}

	private boolean exit() {
		if (FXUtils.showCloseConfirmationWindow(this.primaryStage, "退出", "退出提督日志?")) {
			Platform.exit();
			java.awt.SystemTray.getSystemTray().remove(this.tray.trayIcon);
			return true;
		} else {
			return false;
		}
	}

	private class ApplicationTray extends java.awt.event.MouseAdapter {
		private final java.awt.TrayIcon trayIcon;

		public ApplicationTray() {
			this.trayIcon = new java.awt.TrayIcon(java.awt.Toolkit.getDefaultToolkit().getImage("logo.jpg"));
			this.trayIcon.setToolTip("提督日志");
			this.trayIcon.setImageAutoSize(true);
			this.trayIcon.setPopupMenu(this.createPopupMenu());
			this.trayIcon.addMouseListener(this);
			try {
				java.awt.SystemTray.getSystemTray().add(this.trayIcon);
			} catch (java.awt.AWTException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void mouseClicked(java.awt.event.MouseEvent ev) {
			if (ev.getButton() == java.awt.event.MouseEvent.BUTTON1) {
				FunctionUtils.use(ApplicationMainTDRZJFX.this.primaryStage, stage -> {
					Platform.runLater(() -> {
						stage.setIconified(false);
						stage.show();
						stage.toFront();
					});
				});
			}
		}

		private java.awt.PopupMenu createPopupMenu() {
			return FunctionUtils.use(new java.awt.PopupMenu(), popupMenu -> {
				FunctionUtils.asList(
						FunctionUtils.use(new java.awt.MenuItem("计算器"), menuItem -> {
							menuItem.addActionListener(ev -> {
								Platform.runLater(ApplicationMainTDRZJFX.this.calculator::show);
							});
						}),
						FunctionUtils.use(new java.awt.MenuItem("交换"), menuItem -> {
							menuItem.addActionListener(ev -> {
								Platform.runLater(() -> {
									FunctionUtils.use((HBox) ApplicationMainTDRZJFX.this.primaryStage.getScene().getRoot(), root -> {
										root.getChildren().setAll(FunctionUtils.use(new ArrayList<>(root.getChildren()), Collections::reverse));
									});
								});
							});
						}),
						FunctionUtils.use(new java.awt.MenuItem("退出"), menuItem -> {
							menuItem.addActionListener(ev -> {
								Platform.runLater(ApplicationMainTDRZJFX.this::exit);
							});
						})
				/**/).forEach(popupMenu::add);
			});
		}
	}

	public static class MainStart extends Application {
		public static void main(String[] args) {
			Logger LOG = LogManager.getLogger(MainStart.class);

			TDRZServerSevlet server = new TDRZServerSevlet(() -> 22223, () -> false, () -> "127.0.0.1", () -> 1080);
			try {
				//server.start();
			} catch (Exception ex) {
				LOG.warn(ex);
				LOG.warn("\r\n");
				try {
					server.end();
				} catch (Exception ex1) {
					LOG.warn(ex1);
					LOG.warn("\r\n");
				}
				Platform.exit();
				return;
			}

			//使 UnitManager 初始化
			UnitManager.getUnitManager();

			//即使关闭所有的窗口,也不退出 jfx 线程
			Platform.setImplicitExit(false);

			//堵塞
			MainStart.launch(args);

			try {
				server.end();
			} catch (Exception ex) {
				LOG.warn(ex);
				LOG.warn("\r\n");
			}
		}

		@Override
		public void start(Stage primaryStage) throws Exception {
			ApplicationMainTDRZJFX main = new ApplicationMainTDRZJFX(primaryStage, UnitManager.getUnitManager());
			main.primaryStage.show();
		}
	}
}
