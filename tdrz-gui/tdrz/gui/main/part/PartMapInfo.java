package tdrz.gui.main.part;

import java.util.Arrays;
import java.util.function.Function;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;
import tdrz.gui.main.ApplicationMainPart;
import tdrz.update.data.word.WordMapinfo.OneMapInfo;
import tdrz.update.unit.UnitManager;
import tool.FXUtils;
import tool.function.FunctionUtils;

public class PartMapInfo extends TableView<OneMapInfo> implements ApplicationMainPart {
	public PartMapInfo(UnitManager unitManager, Stage primaryStage) {
		this.addNewColumn("地图", FXUtils.getStringTableCellFactory(), rd -> String.format("%d-%d", rd.getArea(), rd.getNo()));
		this.addNewColumn("血量", FXUtils.getStringTableCellFactory(), rd -> Arrays.toString(rd.getHP()));
		this.addNewColumn("基地航空队", FXUtils.getStringTableCellFactory(), rd -> FunctionUtils.ifFunction(rd.getAirBaseDeckCount(), i -> i > 0, String::valueOf, ""));
		this.addNewColumn("活动地图", FXUtils.getStringTableCellFactory(), rd -> rd.isEventMap() ? "是" : "");
		this.addNewColumn("Rank", FXUtils.getStringTableCellFactory(), rd -> rd.isEventMap() ? rd.getEventMap().getRank() : "");
		this.addNewColumn("血条类型", FXUtils.getStringTableCellFactory(), rd -> rd.isEventMap() ? rd.getEventMap().getHptype() : "");
	}

	private <T> void addNewColumn(String name, Callback<TableColumn<OneMapInfo, T>, TableCell<OneMapInfo, T>> cell, Function<OneMapInfo, T> cellValue) {
		TableColumn<OneMapInfo, T> tableColumn = new TableColumn<>();
		tableColumn.setSortable(false);
		tableColumn.setText(name);
		tableColumn.setCellFactory(cell);
		tableColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(cellValue.apply(param.getValue())));
		this.getColumns().add(tableColumn);
	}
}
