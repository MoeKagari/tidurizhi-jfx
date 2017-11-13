package tdrz.gui.main.part;

import java.util.Arrays;
import java.util.stream.IntStream;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import tdrz.gui.main.ApplicationMainPart;
import tdrz.update.data.word.WordMaterial;
import tdrz.update.unit.UnitManager;

public class PartMaterial extends GridPane implements ApplicationMainPart {
	private final IndexLabel[] labels;

	public PartMaterial(UnitManager unitManager, Stage primaryStage) {
		this.labels = IntStream.of(0, 2, 5, 6, 1, 3, 4, 7).mapToObj(IndexLabel::new).toArray(IndexLabel[]::new);
		this.addRow(0, Arrays.copyOfRange(this.labels, 0, 4));
		this.addRow(1, Arrays.copyOfRange(this.labels, 4, 8));
		this.setAlignment(Pos.CENTER);
	}

	private class IndexLabel extends Label {
		public final int index;

		public IndexLabel(int index) {
			this.index = index;
			this.setText("000000");
			this.setTooltip(new Tooltip(WordMaterial.getMaterialText()[this.index]));
			GridPane.setHgrow(this, Priority.ALWAYS);
			GridPane.setHalignment(this, HPos.RIGHT);
		}
	}
}
