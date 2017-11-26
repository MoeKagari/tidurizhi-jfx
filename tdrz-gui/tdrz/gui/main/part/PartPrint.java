package tdrz.gui.main.part;

import java.text.SimpleDateFormat;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseButton;
import tdrz.gui.main.ApplicationMainPart;
import tdrz.gui.main.ApplicationMainTDRZJFX;
import tdrz.update.unit.UnitPrint.PrintItem;
import tool.function.FunctionUtils;

public class PartPrint extends ListView<PrintItem> implements ApplicationMainPart {
	public PartPrint(ApplicationMainTDRZJFX main) {
		this.setEditable(false);
		this.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		this.setOnMouseClicked(ev -> {
			if (ev.getButton() == MouseButton.SECONDARY) {
				this.getSelectionModel().clearSelection();
			}
		});

		this.setItems(FXCollections.observableArrayList(new PrintItem() {
			@Override
			public String toString() {
				return new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
			}
		}));

		main.getUnitManager().addPrinter(items -> {
			int SIZE_LIMIT = 400;
			Platform.runLater(() -> {
				this.getItems().addAll(items);
				FunctionUtils.ifConsumer(this.getItems().size() - SIZE_LIMIT, extra -> extra > 0, extra -> {
					this.getItems().remove(0, extra);
				});
				this.getSelectionModel().select(this.getItems().size() - 1);
				this.scrollTo(this.getItems().size() - 1);
			});
		});
	}
}
