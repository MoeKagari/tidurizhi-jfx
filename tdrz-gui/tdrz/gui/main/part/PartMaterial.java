package tdrz.gui.main.part;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import tdrz.gui.main.ApplicationMainPart;
import tdrz.gui.main.ApplicationMainTDRZJFX;
import tdrz.update.data.word.WordMaterial;
import tool.function.FunctionUtils;

public class PartMaterial extends GridPane implements ApplicationMainPart {
	private final IndexLabel[] materialLabels = IntStream.of(0, 2, 5, 6, 1, 3, 4, 7).mapToObj(IndexLabel::new).toArray(IndexLabel[]::new);

	public PartMaterial(ApplicationMainTDRZJFX main) {
		this.addRow(0, Arrays.copyOfRange(this.materialLabels, 0, 4));
		this.addRow(1, Arrays.copyOfRange(this.materialLabels, 4, 8));
		this.setAlignment(Pos.CENTER);

		main.getUnitManager().addListener(type -> {
			Optional.ofNullable(main.getUnitManager().getCurrentMaterial()).ifPresent(material -> {
				FunctionUtils.forEach(this.materialLabels, materialLabel -> {
					String text = String.valueOf(material.getAmount()[materialLabel.index]);
					if (FunctionUtils.isFalse(text.equals(materialLabel.getText()))) {
						materialLabel.setText(text);
					}
				});
			});
		});
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
