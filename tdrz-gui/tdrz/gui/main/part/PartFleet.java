package tdrz.gui.main.part;

import java.text.DecimalFormat;
import java.util.function.Function;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import tdrz.core.logic.HPMessage;
import tdrz.core.translator.DeckDtoTranslator;
import tdrz.core.translator.ShipTranslator;
import tdrz.core.translator.SlotItemTranslator;
import tdrz.gui.main.ApplicationMainPart;
import tdrz.gui.main.ApplicationMainTDRZJFX;
import tdrz.server.RawApiDataType;
import tdrz.update.data.word.WordDeck;
import tdrz.update.data.word.WordShip;
import tdrz.update.data.word.WordSlotItem;
import tool.FXUtils;
import tool.function.FunctionUtils;

public class PartFleet extends VBox implements ApplicationMainPart {
	private final ApplicationMainTDRZJFX main;
	private final FleetInfoBox fleetInfoBox = new FleetInfoBox();
	private final ShipInfoBox[] shipInfoBoxs = IntStream.range(0, 6)
			.mapToObj(id -> {
				ShipInfoBox shipIndoBox = new ShipInfoBox();
				GridPane.setHgrow(shipIndoBox, Priority.ALWAYS);
				return shipIndoBox;
			}).toArray(ShipInfoBox[]::new);

	public PartFleet(ApplicationMainTDRZJFX main) {
		this.main = main;
		this.getChildren().addAll(
				this.fleetInfoBox,
				new GridPane() {
					{
						this.setHgap(2);
						this.setVgap(2);
						this.addRow(0, PartFleet.this.shipInfoBoxs[0], PartFleet.this.shipInfoBoxs[1]);
						this.addRow(1, PartFleet.this.shipInfoBoxs[2], PartFleet.this.shipInfoBoxs[3]);
						this.addRow(2, PartFleet.this.shipInfoBoxs[4], PartFleet.this.shipInfoBoxs[5]);
					}
				}
		/**/);
		main.getUnitManager().addListener(this);
	}

	@Override
	public void update(RawApiDataType type) {
		WordDeck deck = this.main.getUnitManager().getDeck(0);

		switch (type) {
			default:
				return;

			case BATTLE_GOBACK_PORT:
			case BATTLE_NEXT:
			case BATTLE_RESULT:
			case BATTLE_SHIPDECK:
			case COMBINEBATTLE_RESULT:

			case CHARGE:

			case COMBINED:

			case DECK:
			case DECK_CHANGE:
			case DECK_ITEMUSE_COND:
			case DECK_PRESET_SELECT:

			case DECK_SHIP_LOCK:

			case DECK_UPDATEDECKNAME:

			case DESTROYSHIP:

			case KAISOU_MARRIAGE:
			case KAISOU_OPEN_EXSLOT:
			case KAISOU_POWERUP:
			case KAISOU_REMODELING:
			case KAISOU_SHIP3:
			case KAISOU_SLOTITEM_LOCK:
			case KAISOU_SLOTSET:
			case KAISOU_SLOTSET_EX:
			case KAISOU_SLOT_DEPRIVE:
			case KAISOU_SLOT_EXCHANGE:
			case KAISOU_UNSETSLOT_ALL:

			case NDOCK:
			case NDOCK_NYUKYO_SPEEDCHANGE:
			case NDOCK_NYUKYO_START:

			case PORT:

			case RADIO_PLAY:
			case RECORD:
			case SHIP2:
		}

		if (deck != null) {
			FunctionUtils.asList(
					this.fleetInfoBox.fleetNameLabel,
					this.fleetInfoBox.speedLabel,
					this.fleetInfoBox.zhikongLabel,
					this.fleetInfoBox.suodiLabel,
					this.fleetInfoBox.totalLevelLabel
			/**/).forEach(label -> {
				label.update(deck);
			});
			for (int index = 0; index < this.shipInfoBoxs.length; index++) {
				ShipInfoBox shipInfoBox = this.shipInfoBoxs[index];
				WordShip ship = this.main.getUnitManager().getShip(deck.getShipArray()[index]);
				FunctionUtils.asList(
						shipInfoBox.shipNameLabel, shipInfoBox.shipLevelLabel, shipInfoBox.shipCondLabel,
						shipInfoBox.shipHPLabel, shipInfoBox.shipHPStringLabel,
						shipInfoBox.shipFuelLabel, shipInfoBox.shipBullLabel,
						shipInfoBox.shipEquipNameLabel0, shipInfoBox.shipEquipNameLabel1,
						shipInfoBox.shipEquipNameLabel2, shipInfoBox.shipEquipNameLabel3,
						shipInfoBox.shipEquipNameLabel4
				/**/).forEach(label -> {
					label.update(ship);
				});
			}
		}
	}

	private class ShowInfoLabel<T> extends Label {
		private final Function<T, String> infoFunction;
		private final Function<T, String> tooltipFunction;
		private final Tooltip tooltip = new Tooltip();

		public ShowInfoLabel(Function<T, String> infoFunction) {
			this(infoFunction, null);
		}

		public ShowInfoLabel(Function<T, String> infoFunction, Function<T, String> tooltipFunction) {
			this.infoFunction = infoFunction;
			this.tooltipFunction = tooltipFunction;
		}

		public void update(T data) {
			String text = FunctionUtils.notNull(data, this.infoFunction, "");
			if (FunctionUtils.isFalse(text.equals(this.getText()))) {
				this.setText(text);
			}

			this.setTooltip(null);
			if (this.tooltipFunction != null && data != null) {
				String tooltipText = this.tooltipFunction.apply(data);
				if (StringUtils.isNotBlank(tooltipText)) {
					this.tooltip.setText(tooltipText);
					this.setTooltip(this.tooltip);
				}
			}
		}
	}

	private class FleetInfoBox extends HBox {
		private final ShowInfoLabel<WordDeck> fleetNameLabel = FunctionUtils.use(
				new ShowInfoLabel<>(WordDeck::getDeckName),
				label -> {
					label.setText("第一舰队");
					HBox.setHgrow(label, Priority.ALWAYS);
					label.setMaxWidth(Double.MAX_VALUE);
					label.setMaxHeight(Double.MAX_VALUE);
					label.setAlignment(Pos.CENTER);
				}
		/**/);
		private final ShowInfoLabel<WordDeck> speedLabel = FunctionUtils.use(
				new ShowInfoLabel<>(deck -> DeckDtoTranslator.highspeed(deck) ? "高速" : "低速"),
				label -> {
					label.setText("高速");
					label.setMaxWidth(Double.MAX_VALUE);
					label.setMaxHeight(Double.MAX_VALUE);
					label.setAlignment(Pos.CENTER);
				}
		/**/);
		private final ShowInfoLabel<WordDeck> zhikongLabel = FunctionUtils.use(
				new ShowInfoLabel<>(deck -> String.valueOf(DeckDtoTranslator.getZhikong(deck))),
				label -> label.setText("0000")
		/**/);
		private final ShowInfoLabel<WordDeck> suodiLabel = FunctionUtils.use(
				new ShowInfoLabel<>(deck -> String.valueOf(DeckDtoTranslator.getSuodi(deck))),
				label -> label.setText("000")
		/**/);
		private final ShowInfoLabel<WordDeck> totalLevelLabel = FunctionUtils.use(
				new ShowInfoLabel<>(deck -> String.valueOf(DeckDtoTranslator.getTotalLevel(deck))),
				label -> label.setText("000")
		/**/);

		public FleetInfoBox() {
			this.getChildren().addAll(
					this.fleetNameLabel,
					FXUtils.createVBox(0, Pos.CENTER_RIGHT, false,
							box -> {
								box.setBorder(FXUtils.createNewBorder(Color.gray(0.80), 0, 1, 0, 1));
								FXUtils.setMinMaxWidth(box, 32);
							},
							this.speedLabel
					/**/),
					FXUtils.createVBox(0, Pos.CENTER_RIGHT, false,
							box -> {
								box.setBorder(FXUtils.createNewBorder(Color.gray(0.80), 0, 1, 0, 0));
								FXUtils.setMinMaxWidth(box, 35);
							},
							FXUtils.createNewLabel("制空"),
							this.zhikongLabel
					/**/),
					FXUtils.createVBox(0, Pos.CENTER_RIGHT, false,
							box -> {
								box.setBorder(FXUtils.createNewBorder(Color.gray(0.80), 0, 1, 0, 0));
								FXUtils.setMinMaxWidth(box, 29);
							},
							FXUtils.createNewLabel("索敌"),
							this.suodiLabel
					/**/),
					FXUtils.createVBox(0, Pos.CENTER_RIGHT, false,
							box -> {
								box.setBorder(FXUtils.createNewBorder(Color.gray(0.80), 0, 1, 0, 0));
								FXUtils.setMinMaxWidth(box, 42);
							},
							FXUtils.createNewLabel("总等级"),
							this.totalLevelLabel
					/**/)
			/**/);
		}
	}

	private class ShipInfoBox extends VBox {
		private final ShowInfoLabel<WordShip> shipNameLabel = FunctionUtils.use(
				new ShowInfoLabel<>(ShipTranslator::getName, ShipTranslator::getDetail),
				label -> {
					label.setText("舰娘名");
					HBox.setHgrow(label, Priority.ALWAYS);
					label.setMaxWidth(Double.MAX_VALUE);
				}
		/**/);
		private final ShowInfoLabel<WordShip> shipLevelLabel = FunctionUtils.use(
				new ShowInfoLabel<>(ship -> String.format("Lv.%d", ship.getLevel())),
				label -> label.setText("Lv.100")
		/**/);
		private final ShowInfoLabel<WordShip> shipCondLabel = FunctionUtils.use(
				new ShowInfoLabel<>(ship -> String.valueOf(ship.getCond())),
				label -> label.setText("49")
		/**/);
		private final ShowInfoLabel<WordShip> shipHPLabel = FunctionUtils.use(
				new ShowInfoLabel<>(ship -> String.format("%d/%d", ship.getNowHp(), ship.getMaxHp())),
				label -> label.setText("100/100")
		/**/);
		private final ShowInfoLabel<WordShip> shipHPStringLabel = FunctionUtils.use(
				new ShowInfoLabel<>(ship -> HPMessage.getString(ShipTranslator.getHPPercent(ship))),
				label -> label.setText("完好")
		/**/);
		private final DecimalFormat FUEL_BULL_PERCENT_FORMAT = new DecimalFormat("00%");
		private final ShowInfoLabel<WordShip> shipFuelLabel = FunctionUtils.use(
				new ShowInfoLabel<>(
						ship -> {
							return FunctionUtils.notNull(ship.getMasterData(), wms -> this.FUEL_BULL_PERCENT_FORMAT.format(ship.getFuel() * 1.0 / wms.getFuelMax()), "");
						},
						ship -> {
							return FunctionUtils.notNull(ship.getMasterData(), wms -> String.format("%d/%d", ship.getFuel(), wms.getFuelMax()), "");
						}
				/**/),
				label -> label.setText("100%")
		/**/);
		private final ShowInfoLabel<WordShip> shipBullLabel = FunctionUtils.use(
				new ShowInfoLabel<>(
						ship -> {
							return FunctionUtils.notNull(ship.getMasterData(), wms -> this.FUEL_BULL_PERCENT_FORMAT.format(ship.getBull() * 1.0 / wms.getBullMax()), "");
						},
						ship -> {
							return FunctionUtils.notNull(ship.getMasterData(), wms -> String.format("%d/%d", ship.getBull(), wms.getBullMax()), "");
						}
				/**/),
				label -> label.setText("100%")
		/**/);
		private final ShowInfoLabel<WordShip> shipEquipNameLabel0 = this.createShipEquipLabel(0);
		private final ShowInfoLabel<WordShip> shipEquipNameLabel1 = this.createShipEquipLabel(1);
		private final ShowInfoLabel<WordShip> shipEquipNameLabel2 = this.createShipEquipLabel(2);
		private final ShowInfoLabel<WordShip> shipEquipNameLabel3 = this.createShipEquipLabel(3);
		private final ShowInfoLabel<WordShip> shipEquipNameLabel4 = this.createShipEquipLabel(4);

		private ShowInfoLabel<WordShip> createShipEquipLabel(int id) {
			return FunctionUtils.use(
					new ShowInfoLabel<>(ship -> {
						int slotItemId;
						if (id == 4) {
							slotItemId = ship.getSlotex();
						} else {
							slotItemId = ship.getSlots()[id];
						}

						if (slotItemId <= 0) {
							return "";
						}

						WordSlotItem slotItem = PartFleet.this.main.getUnitManager().getSlotItem(slotItemId);
						return slotItem != null ? SlotItemTranslator.getName(slotItem) : "未知";
					}),
					label -> {
						label.setText("");
						HBox.setHgrow(label, Priority.ALWAYS);
						label.setMaxWidth(Double.MAX_VALUE);
						//label.setFont(new Font(10));
					}
			/**/);
		}

		public ShipInfoBox() {
			this.setBorder(FXUtils.createNewBorder(Color.gray(0.70)));
			this.getChildren().addAll(
					FXUtils.createHBox(0, Pos.CENTER, false,
							box -> {
								box.setBorder(FXUtils.createNewBorder(Color.gray(0.80), 0, 0, 1, 0));
							},
							this.shipNameLabel,
							FXUtils.createVBox(0, Pos.CENTER_RIGHT, false,
									box -> {
										box.setBorder(FXUtils.createNewBorder(Color.gray(0.80), 0, 1, 0, 1));
										FXUtils.setMinMaxWidth(box, 43);
									},
									this.shipLevelLabel,
									this.shipCondLabel
							/**/),
							FXUtils.createVBox(0, Pos.CENTER_RIGHT, false,
									box -> {
										box.setBorder(FXUtils.createNewBorder(Color.gray(0.80), 0, 1, 0, 0));
										FXUtils.setMinMaxWidth(box, 52);
									},
									this.shipHPLabel,
									this.shipHPStringLabel
							/**/),
							FXUtils.createVBox(0, Pos.CENTER_RIGHT, false,
									box -> {
										box.setBorder(FXUtils.createNewBorder(Color.gray(0.80), 0, 0, 0, 0));
										FXUtils.setMinMaxWidth(box, 37);
									},
									this.shipFuelLabel,
									this.shipBullLabel
							/**/)
					/**/),
					FXUtils.createHBox(0, Pos.CENTER, false,
							FXUtils.createVBox(0, Pos.TOP_LEFT, false,
									box -> {
										box.setBorder(FXUtils.createNewBorder(Color.gray(0.80), 0, 1, 0, 0));
										HBox.setHgrow(box, Priority.ALWAYS);
									},
									this.shipEquipNameLabel0,
									this.shipEquipNameLabel2,
									this.shipEquipNameLabel4
							/**/),
							FXUtils.createVBox(0, Pos.TOP_LEFT, false,
									box -> {
										HBox.setHgrow(box, Priority.ALWAYS);
									},
									this.shipEquipNameLabel1,
									this.shipEquipNameLabel3
							/**/)
					/**/)
			/**/);
		}
	}
}
