package tdrz.gui.main.part;

import java.text.SimpleDateFormat;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import tdrz.gui.main.ApplicationMainPart;
import tdrz.update.unit.UnitManager;
import tdrz.update.unit.other.PrintUnit.PrintItem;
import tdrz.update.unit.other.PrintUnit.Printer;
import tool.function.FunctionUtils;

public class PartPrint extends ListView<PrintItem> implements ApplicationMainPart, Printer {
	private final static int SIZE_LIMIT = 400;

	public PartPrint(UnitManager unitManager, Stage primaryStage) {
		this.setEditable(false);
		this.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		this.setOnMouseClicked(ev -> {
			if (ev.getButton() == MouseButton.SECONDARY) {
				this.getSelectionModel().clearSelection();
			}
		});
		this.setItems(FXCollections.observableArrayList(new PrintItem(0, null) {
			@Override
			public String toString() {
				return new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
			}
		}));
		unitManager.printUnit.addPrinter(this);
	}

	@Override
	public void print(List<? extends PrintItem> items) {
		Platform.runLater(() -> {
			this.getItems().addAll(items);
			FunctionUtils.ifConsumer(this.getItems().size() - SIZE_LIMIT, extra -> extra > 0, extra -> {
				this.getItems().remove(0, extra);
			});
			this.getSelectionModel().select(this.getItems().size() - 1);
			this.scrollTo(this.getItems().size() - 1);
		});
	}
}
