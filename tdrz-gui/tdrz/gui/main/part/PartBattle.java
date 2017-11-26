package tdrz.gui.main.part;

import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import tdrz.gui.main.ApplicationMainPart;
import tdrz.gui.main.ApplicationMainTDRZJFX;
import tool.FXUtils;

public class PartBattle extends ScrollPane implements ApplicationMainPart {
	private final VBox battleContentBox = FXUtils.createVBox(0, Pos.TOP_CENTER, false);

	public PartBattle(ApplicationMainTDRZJFX main) {
		this.setContent(this.battleContentBox);
		this.setVbarPolicy(ScrollBarPolicy.ALWAYS);
	}

	public void clear() {
		this.battleContentBox.getChildren().clear();
	}
}
