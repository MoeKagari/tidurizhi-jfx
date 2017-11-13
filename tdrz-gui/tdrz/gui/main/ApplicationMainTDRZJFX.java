package tdrz.gui.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
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
import tdrz.gui.main.part.PartDeckMission;
import tdrz.gui.main.part.PartFleet;
import tdrz.gui.main.part.PartMapInfo;
import tdrz.gui.main.part.PartMaterial;
import tdrz.gui.main.part.PartNdock;
import tdrz.gui.main.part.PartPractice;
import tdrz.gui.main.part.PartPrint;
import tdrz.gui.main.part.PartQuest;
import tdrz.gui.vice.ViceCalculator;
import tdrz.server.TDRZServerSevlet;
import tdrz.update.unit.UnitManager;
import tool.FXUtils;
import tool.function.FunctionUtils;

public final class ApplicationMainTDRZJFX {
	private final UnitManager unitManager;
	private final Stage primaryStage;
	private final ApplicationTray tray;

	private final ViceCalculator calculator;

	public ApplicationMainTDRZJFX(Stage stage, UnitManager unitManager) {
		this.unitManager = unitManager;

		this.primaryStage = stage;
		this.primaryStage.setTitle("提督日志");
		this.primaryStage.setResizable(false);
		this.primaryStage.setScene(new Scene(this.createParent(), 1010, 600));
		this.primaryStage.setOnCloseRequest(ev -> FunctionUtils.ifNotRunnable(this.exit(), ev::consume));

		this.calculator = new ViceCalculator(unitManager, this.primaryStage);

		this.tray = new ApplicationTray();
	}

	private Parent createParent() {
		Button shipListButton = new Button("舰娘(100/100)");
		HBox.setHgrow(shipListButton, Priority.ALWAYS);

		Button slotItemListButton = new Button("装备(1000/1000)");
		HBox.setHgrow(slotItemListButton, Priority.ALWAYS);

		PartMaterial material = new PartMaterial(this.unitManager, this.primaryStage);

		PartDeckMission deckMission = new PartDeckMission(this.unitManager, this.primaryStage);
		FXUtils.setMinMaxWidth(deckMission, 200);

		PartNdock ndock = new PartNdock(this.unitManager, this.primaryStage);
		FXUtils.setMinMaxWidth(ndock, 200);

		PartFleet fleet = new PartFleet(this.unitManager, this.primaryStage);
		PartPrint print = new PartPrint(this.unitManager, this.primaryStage);
		PartQuest quest = new PartQuest(this.unitManager, this.primaryStage);
		PartPractice practice = new PartPractice(this.unitManager, this.primaryStage);
		PartBasic basic = new PartBasic(this.unitManager, this.primaryStage);
		PartMapInfo mapInfo = new PartMapInfo(this.unitManager, this.primaryStage);
		PartAirbase airbase = new PartAirbase(this.unitManager, this.primaryStage);

		PartBattle battle = new PartBattle(this.unitManager, this.primaryStage);
		VBox.setVgrow(battle, Priority.ALWAYS);

		return FXUtils.createHBox(0, Pos.CENTER, false,
				FXUtils.createVBox(2, Pos.CENTER, false,
						box -> HBox.setHgrow(box, Priority.ALWAYS),
						FXUtils.createHBox(0, Pos.CENTER, false,
								box -> box.setBackground(FXUtils.createNewBackground(Color.gray(0.85))),
								FXUtils.createVBox(0, Pos.CENTER, false,
										box -> HBox.setHgrow(box, Priority.ALWAYS),
										FXUtils.createHBox(0, Pos.CENTER, false,
												box -> VBox.setVgrow(box, Priority.ALWAYS),
												shipListButton,
												slotItemListButton
										/**/),
										material
								/**/),
								new Separator(Orientation.VERTICAL),
								deckMission,
								new Separator(Orientation.VERTICAL),
								ndock
						/**/),
						new TabPane() {
							{
								VBox.setVgrow(this, Priority.ALWAYS);
								this.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
								this.setTabMinWidth(50);
								this.getSelectionModel().selectedItemProperty().addListener((source, oldValue, newValue) -> {});
								this.getTabs().addAll(
										new Tab("常用", new SplitPane(fleet, print) {
											{
												this.setBackground(FXUtils.createNewBackground(Color.gray(0.85)));
												this.setOrientation(Orientation.VERTICAL);
											}
										}),
										new Tab("任务", quest),
										new Tab("演习", practice),
										new Tab("提督", basic),
										new Tab("地图", mapInfo),
										new Tab("基地", airbase)
								/**/);
							}
						}
				/**/ ),
				FXUtils.createVBox(0, Pos.CENTER, false, box -> FXUtils.setMinMaxWidth(box, 400), battle)
		/**/);
	}

	private boolean exit() {
		if (FXUtils.showCloseConfirmationWindow(this.primaryStage, "退出", "退出提督日志?")) {
			Platform.exit();
			this.tray.exit();
			return true;
		} else {
			return false;
		}
	}

	private class ApplicationTray {
		private final java.awt.TrayIcon trayIcon;

		public ApplicationTray() {
			this.trayIcon = new java.awt.TrayIcon(java.awt.Toolkit.getDefaultToolkit().getImage("logo.jpg"));
			this.trayIcon.setToolTip("提督日志");
			this.trayIcon.setImageAutoSize(true);
			this.trayIcon.setPopupMenu(this.createPopupMenu());
			this.trayIcon.addMouseListener(new java.awt.event.MouseAdapter() {
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
			});
			try {
				java.awt.SystemTray.getSystemTray().add(this.trayIcon);
			} catch (java.awt.AWTException e) {
				e.printStackTrace();
			}
		}

		private java.awt.PopupMenu createPopupMenu() {
			java.awt.PopupMenu popupMenu = new java.awt.PopupMenu();

			{
				java.awt.MenuItem calculatorMenuItem = new java.awt.MenuItem("计算器");
				calculatorMenuItem.addActionListener(ev -> Platform.runLater(() -> {
					ApplicationMainTDRZJFX.this.calculator.show();
				}));
				popupMenu.add(calculatorMenuItem);
			}

			{
				java.awt.MenuItem exit = new java.awt.MenuItem("退出");
				exit.addActionListener(ev -> Platform.runLater(ApplicationMainTDRZJFX.this::exit));
				popupMenu.add(exit);
			}

			return popupMenu;
		}

		private void exit() {
			java.awt.SystemTray.getSystemTray().remove(this.trayIcon);
		}
	}

	public static class MainStart extends Application {
		public static void main(String[] args) {
			Logger LOG = LogManager.getLogger(MainStart.class);

			TDRZServerSevlet server = new TDRZServerSevlet(() -> 22223, () -> false, () -> "127.0.0.1", () -> 1080);
			try {
				server.start();
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
