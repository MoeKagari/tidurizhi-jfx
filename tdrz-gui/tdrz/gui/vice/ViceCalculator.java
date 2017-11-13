package tdrz.gui.vice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableView;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import tdrz.core.translator.ShipDtoTranslator;
import tdrz.core.util.ExpUtils.Eval;
import tdrz.core.util.ExpUtils.SeaExp;
import tdrz.core.util.ExpUtils.ShipExp;
import tdrz.update.data.word.WordMasterData.WordMasterShip;
import tdrz.update.unit.UnitManager;
import tdrz.update.data.word.WordShip;
import tool.FXUtils;
import tool.function.FunctionUtils;

public class ViceCalculator extends ViceWindowBase {
	private final LeftOptionBox left;
	private final RightTable right;

	public ViceCalculator(UnitManager unitManager, Stage primaryStage) {
		super(unitManager, "计算器");

		this.right = new RightTable();
		HBox.setHgrow(this.right, Priority.ALWAYS);

		this.left = new LeftOptionBox();
		FXUtils.setMinMaxWidth(this.left, 240);

		this.setScene(new Scene(new HBox(this.left, this.right), 1100, 600));
	}

	private void update() {
		LeftOptionBox.AbstractDataBox selected = this.left.getSelectedBox();
		this.right.setItems(FXCollections.observableList(
				IntStream.rangeClosed(selected.getCurrentLevel(), selected.getMaxLevel())//
						.mapToObj(targetLevel -> new CalcuExpData(selected.getCurrentLevel(), selected.getCurrentExp(), targetLevel))//
						.collect(Collectors.toList())
		/**/));
	}

	private int calcuBaseExp(int seaExp) {
		double exp = seaExp;

		if (this.left.flagship.isSelected()) exp *= 1.5;
		if (this.left.mvp.isSelected()) exp *= 2;
		exp *= Eval.EVALMAP.get(this.left.eval.getSelectionModel().getSelectedItem().name).value;

		return (int) Math.floor(exp);
	}

	private class LeftOptionBox extends VBox {
		private final CheckBox flagship, mvp;
		private final ComboBox<Eval> eval;
		private final Label gaizhaoLevel;
		private final VBox shipListBox;
		private final DefaultDataBox defaultShip;

		public LeftOptionBox() {
			Button updateDataButton = new Button("更新");
			updateDataButton.setOnAction(ev -> FunctionUtils.ifRunnable(this.updateShipData(), ViceCalculator.this::update));

			Button secretary = new Button("秘书舰");
			secretary.setOnAction(ev -> FunctionUtils.ifRunnable(this.selectSecretaryShip(), ViceCalculator.this::update));

			this.flagship = new CheckBox("旗舰");
			this.flagship.setSelected(true);
			this.flagship.setOnAction(ev -> ViceCalculator.this.update());

			this.mvp = new CheckBox("MVP");
			this.mvp.setSelected(true);
			this.mvp.setOnAction(ev -> ViceCalculator.this.update());

			this.eval = new ComboBox<>();
			this.eval.getItems().addAll(Eval.values());
			this.eval.getSelectionModel().select(Eval.S);
			this.eval.setOnAction(ev -> ViceCalculator.this.update());

			this.gaizhaoLevel = new Label();
			HBox.setHgrow(this.gaizhaoLevel, Priority.ALWAYS);
			this.gaizhaoLevel.setTextAlignment(TextAlignment.LEFT);

			this.shipListBox = new VBox();
			VBox.setVgrow(this.shipListBox, Priority.ALWAYS);

			this.defaultShip = new DefaultDataBox();
			this.defaultShip.select();

			this.setSpacing(2);
			this.getChildren().addAll(
					FXUtils.createHBox(2, Pos.CENTER_LEFT, false, updateDataButton, this.flagship, this.mvp, this.eval),
					FXUtils.createHBox(5, Pos.CENTER_LEFT, false, secretary, new Label("改造等级 :"), this.gaizhaoLevel),
					this.defaultShip,
					FXUtils.createScrollPane(this.shipListBox, scrollPane -> {
						VBox.setVgrow(scrollPane, Priority.ALWAYS);
						scrollPane.setBorder(FXUtils.createNewBorder());
						scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
					})
			/**/);
		}

		private AbstractDataBox getSelectedBox() {
			return Stream.concat(Stream.of(LeftOptionBox.this.defaultShip), FunctionUtils.down(LeftOptionBox.this.shipListBox.getChildren().stream(), AbstractDataBox.class))
					.filter(AbstractDataBox::isSelected).findFirst().get();
		}

		/**
		 * 更新左侧的data
		 * 
		 * @return 是否重新更新table
		 */
		private boolean updateShipData() {
			List<WordShip> datas = new ArrayList<>(ViceCalculator.this.getUnitManager().shipUnit.shipMap.values());
			Predicate<WordShip> dataFilter = data -> false;
			//			if (AppConfig.get().isNotCalcuExpForLevel1Ship()) {
			//				dataFilter.or(data -> data.getLevel() == 1);
			//			}
			//			if (AppConfig.get().isNotCalcuExpForLevel99Ship()) {
			//				dataFilter.or(data -> data.getLevel() == 99);
			//			}
			//			if (AppConfig.get().isNotCalcuExpForLevel165Ship()) {
			//				dataFilter.or(data -> data.getLevel() == 165);
			//			}
			datas.removeIf(dataFilter);
			datas.sort(Comparator.comparingInt(WordShip::getLevel).reversed());//等级从大到小

			AbstractDataBox selected = this.getSelectedBox();

			this.shipListBox.getChildren().clear();
			FunctionUtils.toListUseIndex(datas, (index, data) -> new ShipDataBox(index + 1, data)).forEach(sdb -> {
				this.shipListBox.getChildren().add(sdb);
			});

			//先前为默认选择,则返回false
			if (selected == this.defaultShip) {
				return false;
			}

			int shipID = selected.getShipId();
			int currentExp = selected.getCurrentExp();

			Optional<ShipDataBox> op = FunctionUtils.down(this.shipListBox.getChildren().stream(), ShipDataBox.class)
					.filter(shipBox -> shipBox.shipId == shipID)
					.findFirst();
			if (op.isPresent()) {
				//先前选择的舰娘被除籍,则不会有值
				ShipDataBox shipBox = op.get();
				shipBox.select();
				return shipBox.currentExp != currentExp;//舰娘的经验是否发生了变化
			} else {
				//未发现与先前选择一样的,则默认选择,并返回true
				this.defaultShip.select();
				return true;
			}
		}

		private boolean selectSecretaryShip() {
			WordShip secretaryShip = ViceCalculator.this.getUnitManager().getSecretaryShip();
			if (secretaryShip != null) {
				AbstractDataBox selected = this.getSelectedBox();

				//如果秘书舰已经被选中
				if (selected.getShipId() == secretaryShip.getId()) {
					return false;
				}

				for (Node node : this.shipListBox.getChildren()) {
					ShipDataBox shipBox = (ShipDataBox) node;
					if (shipBox.getShipId() == secretaryShip.getId()) {
						selected.notSelect();
						shipBox.select();
						return true;
					}
				}
			}
			return false;
		}

		private abstract class AbstractDataBox extends HBox {
			public final RadioButton check;

			AbstractDataBox(String text) {
				this.check = new RadioButton();
				FXUtils.setMinMaxWidth(this.check, 50);
				this.check.setText(text);
				this.check.setOnAction(ev -> {
					Stream.concat(Stream.of(LeftOptionBox.this.defaultShip), FunctionUtils.down(LeftOptionBox.this.shipListBox.getChildren().stream(), AbstractDataBox.class))
							.filter(AbstractDataBox::isSelected)
							.filter(adb -> adb != this)
							.findFirst()
							.ifPresent(other -> {
								other.notSelect();
								ViceCalculator.this.update();
							});
					this.select();
				});

				this.setAlignment(Pos.CENTER_LEFT);
				this.getChildren().add(this.check);
			}

			public final void select() {
				String text = FunctionUtils.ifFunction(this.getGaizhaoLevel(), gaizhaoLevel -> gaizhaoLevel != 0, String::valueOf, "");
				LeftOptionBox.this.gaizhaoLevel.setText(text);
				this.check.setSelected(true);
			}

			public final void notSelect() {
				this.check.setSelected(false);
			}

			public final boolean isSelected() {
				return this.check.isSelected();
			}

			public final int getMaxLevel() {
				return ShipExp.EXPMAP.size();
			}

			public abstract int getCurrentLevel();

			public abstract int getCurrentExp();

			public abstract int getShipId();

			public abstract int getGaizhaoLevel();
		}

		private class DefaultDataBox extends LeftOptionBox.AbstractDataBox {
			private final Spinner<Integer> spinner;

			DefaultDataBox() {
				super("默认");

				int min = 1, max = this.getMaxLevel(), initial = 1;
				this.spinner = new Spinner<>(min, max, initial);
				this.spinner.setEditable(true);
				FXUtils.setMinMaxWidth(this.spinner, 70);
				this.spinner.getEditor().setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 100, new UnaryOperator<Change>() {
					@Override
					public Change apply(Change change) {
						Integer value;

						try {
							value = Integer.parseInt(change.getControlNewText());
						} catch (Exception ex) {
							value = null;
						}

						if (value != null) {
							if (value >= min && value <= max) {
								return change;
							}
						}
						return null;
					}
				}));
				this.spinner.getEditor().textProperty().addListener((source, oldValue, newValue) -> {
					if (this.isSelected()) {
						ViceCalculator.this.update();
					}
				});
				this.getChildren().add(this.spinner);
			}

			@Override
			public int getCurrentLevel() {
				return Integer.parseInt(this.spinner.getEditor().getText());
			}

			@Override
			public int getCurrentExp() {
				return ShipExp.EXPMAP.get(this.getCurrentLevel());
			}

			@Override
			public int getShipId() {
				return -1;
			}

			@Override
			public int getGaizhaoLevel() {
				return 0;
			}
		}

		private class ShipDataBox extends LeftOptionBox.AbstractDataBox {
			private final int shipId;
			private final int currentLevel;
			private final int currentExp;
			private final int gaizhaoLevel;

			private final Label levelLabel;
			private final Label nameLabel;

			ShipDataBox(int index, WordShip ship) {
				super(String.valueOf(index));

				this.levelLabel = new Label();
				this.levelLabel.setText(String.format("Lv.%d", ship.getLevel()));
				FXUtils.setMinMaxWidth(this.levelLabel, 42);
				this.getChildren().add(this.levelLabel);

				this.nameLabel = new Label();
				this.nameLabel.setText(ShipDtoTranslator.getName(ship));
				this.getChildren().add(this.nameLabel);

				this.shipId = ship.getId();
				this.currentLevel = ship.getLevel();
				this.currentExp = ship.getCurrentExp();
				this.gaizhaoLevel = FunctionUtils.notNull(ship.getMasterData(), WordMasterShip::getGaizhaoLv, 0);
			}

			@Override
			public int getCurrentLevel() {
				return this.currentLevel;
			}

			@Override
			public int getCurrentExp() {
				return this.currentExp;
			}

			@Override
			public int getShipId() {
				return this.shipId;
			}

			@Override
			public int getGaizhaoLevel() {
				return this.gaizhaoLevel;
			}
		}
	}

	private class RightTable extends TableView<CalcuExpData> {
		public RightTable() {
			FXUtils.addNewColumn(this, "目标等级", FXUtils.getStringTableCellFactory(), rd -> {
				if (rd.targetIsCurrent()) {
					return String.format("当前等级:%d", rd.currentLevel);
				} else {
					return String.valueOf(rd.targetLevel);
				}
			});
			FXUtils.addNewColumn(this, "升级所需", FXUtils.getStringTableCellFactory(), rd -> {
				if (rd.targetIsCurrent()) {
					return String.format("当前经验:%d", rd.currentExp);
				} else {
					return String.valueOf(rd.needExp());
				}
			});
			FunctionUtils.toMapForEach(
					Arrays.stream("1-5,2-3,3-2,4-3,5-1,5-3,5-4".split(",")).filter(SeaExp.SEAEXPMAP::containsKey),
					FunctionUtils::returnSelf, SeaExp.SEAEXPMAP::get,
					(sea, exp) -> FXUtils.addNewColumn(this, sea, FXUtils.getStringTableCellFactory(), rd -> {
						int baseExp = ViceCalculator.this.calcuBaseExp(exp);
						if (rd.targetIsCurrent()) {
							return String.format("基础经验:%d", baseExp);
						} else {
							int needExp = rd.needExp();
							int count = (needExp / baseExp) + ((needExp % baseExp) != 0 ? 1 : 0);
							return String.valueOf(count);
						}
					})
			/**/);
		}
	}

	private class CalcuExpData {
		private final int currentLevel;
		private final int currentExp;
		private final int targetLevel;
		private final int targetExp;

		public CalcuExpData(int currentLevel, int currentExp, int targetLevel) {
			this.currentLevel = currentLevel;
			this.currentExp = currentExp;
			this.targetLevel = targetLevel;
			this.targetExp = ShipExp.EXPMAP.get(targetLevel);
		}

		public boolean targetIsCurrent() {
			return this.targetLevel == this.currentLevel;
		}

		public int needExp() {
			return this.targetExp - this.currentExp;
		}
	}
}
