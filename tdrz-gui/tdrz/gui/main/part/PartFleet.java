package tdrz.gui.main.part;

import java.util.stream.IntStream;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import tdrz.gui.main.ApplicationMainPart;
import tdrz.update.unit.UnitManager;
import tool.FXUtils;

public class PartFleet extends VBox implements ApplicationMainPart {
	private final FleetInfoBox fleetInfoBox = new FleetInfoBox();
	private final ShipInfoBox[] shipIndoBoxs = IntStream.range(0, 6)
			.mapToObj(id -> {
				ShipInfoBox shipIndoBox = new ShipInfoBox(id);
				HBox.setHgrow(shipIndoBox, Priority.ALWAYS);
				return shipIndoBox;
			}).toArray(ShipInfoBox[]::new);

	public PartFleet(UnitManager unitManager, Stage primaryStage) {
		this.getChildren().addAll(
				this.fleetInfoBox,
				FXUtils.createVBox(2, Pos.CENTER, false,
						FXUtils.createHBox(2, Pos.CENTER, false, this.shipIndoBoxs[0], this.shipIndoBoxs[1]),
						FXUtils.createHBox(2, Pos.CENTER, false, this.shipIndoBoxs[2], this.shipIndoBoxs[3]),
						FXUtils.createHBox(2, Pos.CENTER, false, this.shipIndoBoxs[4], this.shipIndoBoxs[5])
				/**/)
		/**/);
	}

	private class FleetInfoBox extends HBox {
		private final Label fleetNameLabel = FXUtils.createNewLabel("第一舰队", label -> {
			HBox.setHgrow(label, Priority.ALWAYS);
			label.setMaxWidth(Double.MAX_VALUE);
			label.setAlignment(Pos.CENTER);
		});
		private final Label speedLabel = FXUtils.createNewLabel("高速", label -> {
			label.setMaxWidth(Double.MAX_VALUE);
			label.setAlignment(Pos.CENTER);
		});
		private final Label zhikongLabel = new Label("0000");
		private final Label suodiLabel = new Label("000");
		private final Label totalLevelLabel = new Label("000");

		public FleetInfoBox() {
			this.setAlignment(Pos.CENTER);
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
		private final Label shipNameLabel = FXUtils.createNewLabel("舰娘名", label -> {
			HBox.setHgrow(label, Priority.ALWAYS);
			label.setMaxWidth(Double.MAX_VALUE);
		});
		private final Label shipLevelLabel = new Label("Lv.100");
		private final Label shipCondLabel = new Label("49");
		private final Label shipHPLabel = new Label("100/100"), shipHPStringLabel = new Label("完好");
		private final Label shipFuelLabel = new Label("100%"), shipBullLabel = new Label("100%");
		private final Label[] shipEquipNameLabels = IntStream.range(0, 5)
				.mapToObj(index -> {
					Label shipEquipNameLabel = new Label("(00/00) 381mm/50 三联装炮改");
					HBox.setHgrow(shipEquipNameLabel, Priority.ALWAYS);
					shipEquipNameLabel.setMaxWidth(Double.MAX_VALUE);
					shipEquipNameLabel.setFont(new Font(10));
					return shipEquipNameLabel;
				}).toArray(Label[]::new);

		public ShipInfoBox(int id) {
			this.setSpacing(0);
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
									this.shipEquipNameLabels[0],
									this.shipEquipNameLabels[2],
									this.shipEquipNameLabels[4]
							/**/),
							FXUtils.createVBox(0, Pos.TOP_LEFT, false,
									box -> {
										HBox.setHgrow(box, Priority.ALWAYS);
									},
									this.shipEquipNameLabels[1],
									this.shipEquipNameLabels[3]
							/**/)
					/**/)
			/**/);
		}
	}
}
