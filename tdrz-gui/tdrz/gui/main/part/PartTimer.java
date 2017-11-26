package tdrz.gui.main.part;

import java.util.stream.IntStream;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import tdrz.gui.main.ApplicationMainPart;
import tdrz.gui.main.ApplicationMainTDRZJFX;
import tool.FXUtils;

/**
 * deck mission 和 ndock 计时器
 * 
 * @author MoeKagari
 */
public class PartTimer extends GridPane implements ApplicationMainPart {
	private final static int LENGTH = 4;
	private final Label[] nameLabels = IntStream.range(0, LENGTH).mapToObj(index -> {
		Label label = FXUtils.createNewLabel("name" + (index + 1), Pos.CENTER_LEFT);
		label.setMaxWidth(Double.MAX_VALUE);
		GridPane.setHgrow(label, Priority.ALWAYS);
		return label;
	}).toArray(Label[]::new);
	private final Label[] timeLabels = IntStream.range(0, LENGTH).mapToObj(index -> {
		Label label = FXUtils.createNewLabel("00时00分00秒", 79, Pos.CENTER_RIGHT);
		GridPane.setHgrow(label, Priority.NEVER);
		return label;
	}).toArray(Label[]::new);

	public PartTimer(ApplicationMainTDRZJFX main) {
		this.addColumn(0, this.nameLabels);
		this.addColumn(1, this.timeLabels);
		this.setAlignment(Pos.CENTER);
	}
}
