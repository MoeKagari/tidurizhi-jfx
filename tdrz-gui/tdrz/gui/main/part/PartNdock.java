package tdrz.gui.main.part;

import java.util.stream.IntStream;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import tdrz.gui.main.ApplicationMainPart;
import tdrz.update.unit.UnitManager;

public class PartNdock extends GridPane implements ApplicationMainPart {
	private final Label[] nameLabels;
	private final Label[] timeLabels;

	public PartNdock(UnitManager unitManager, Stage primaryStage) {
		int length = 4;

		this.nameLabels = IntStream.range(0, length).mapToObj(index -> {
			Label label = new Label("渠" + (index + 1));
			label.setAlignment(Pos.CENTER);
			GridPane.setHgrow(label, Priority.ALWAYS);
			return label;
		}).toArray(Label[]::new);

		this.timeLabels = IntStream.range(0, length).mapToObj(index -> {
			Label label = new Label("00时00分00秒");
			label.setAlignment(Pos.CENTER);
			return label;
		}).toArray(Label[]::new);

		this.addColumn(0, this.nameLabels);
		this.addColumn(1, this.timeLabels);
		this.setVgap(3);
		this.setAlignment(Pos.CENTER);
	}
}
