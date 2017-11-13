package tdrz.gui.main.part;

import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tdrz.gui.main.ApplicationMainPart;
import tdrz.update.unit.UnitManager;

public class PartBattle extends ScrollPane implements ApplicationMainPart {
	private final VBox battleContentBox;

	public PartBattle(UnitManager unitManager, Stage primaryStage) {
		this.battleContentBox = new VBox();
		this.battleContentBox.setAlignment(Pos.TOP_CENTER);
		this.setContent(this.battleContentBox);
		this.setVbarPolicy(ScrollBarPolicy.ALWAYS);
	}

	public void clear() {
		this.battleContentBox.getChildren().clear();
	}
}
