package tdrz.gui.main;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import tdrz.update.data.word.WordBasic;
import tool.function.FunctionUtils;

/**
 * shipbutton and slotitembutton
 * 
 * @author MoeKagari
 */
public class PartShipSlotItemButton extends TilePane implements ApplicationMainPart {
	private final Button shipListButton = FunctionUtils.use(new Button("舰娘(100/100)"), button -> {
		button.setMaxWidth(Double.MAX_VALUE);
	});
	private final Button slotItemListButton = FunctionUtils.use(new Button("装备(1000/1000)"), button -> {
		button.setMaxWidth(Double.MAX_VALUE);
	});

	public PartShipSlotItemButton(ApplicationMainTDRZJFX main) {
		this.setOrientation(Orientation.HORIZONTAL);
		this.setAlignment(Pos.CENTER);
		this.setTileAlignment(Pos.CENTER);
		this.getChildren().addAll(this.shipListButton, this.slotItemListButton);

		main.getUnitManager().addListener(type -> {
			WordBasic basic = main.getUnitManager().getBasicInformation();
			if (basic == null) return;
			String text;

			text = String.format("舰娘(%d/%d)", main.getUnitManager().getShips().size(), basic.getMaxChara());
			if (FunctionUtils.isFalse(text.equals(this.shipListButton.getText()))) {
				this.shipListButton.setText(text);
			}

			text = String.format("装备(%d/%d)", main.getUnitManager().getSlotItems().size(), basic.getMaxSlotItem());
			if (FunctionUtils.isFalse(text.equals(this.slotItemListButton.getText()))) {
				this.slotItemListButton.setText(text);
			}
		});
	}
}
