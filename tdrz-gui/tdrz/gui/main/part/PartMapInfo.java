package tdrz.gui.main.part;

import java.util.Arrays;
import java.util.Optional;

import javafx.scene.control.TableView;
import tdrz.gui.main.ApplicationMainPart;
import tdrz.gui.main.ApplicationMainTDRZJFX;
import tdrz.server.RawApiDataType;
import tdrz.update.data.word.WordMapinfo;
import tdrz.update.data.word.WordMapinfo.OneMapInfo;
import tool.FXUtils;
import tool.function.FunctionUtils;

public class PartMapInfo extends TableView<OneMapInfo> implements ApplicationMainPart {
	public PartMapInfo(ApplicationMainTDRZJFX main) {
		FXUtils.addNewColumn(this, "地图", FXUtils.getStringTableCellFactory(), rd -> String.format("%d-%d", rd.getArea(), rd.getNo()));
		FXUtils.addNewColumn(this, "血量", FXUtils.getStringTableCellFactory(), rd -> Arrays.toString(rd.getHP()));
		FXUtils.addNewColumn(this, "基地航空队", FXUtils.getStringTableCellFactory(), rd -> FunctionUtils.ifFunction(rd.getAirBaseDeckCount(), i -> i > 0, String::valueOf, ""));
		FXUtils.addNewColumn(this, "活动地图", FXUtils.getStringTableCellFactory(), rd -> rd.isEventMap() ? "是" : "");
		FXUtils.addNewColumn(this, "Rank", FXUtils.getStringTableCellFactory(), rd -> rd.isEventMap() ? rd.getEventMap().getRank() : "");
		FXUtils.addNewColumn(this, "血条类型", FXUtils.getStringTableCellFactory(), rd -> rd.isEventMap() ? rd.getEventMap().getHptype() : "");

		main.getUnitManager().addListener(type -> {
			if (type == RawApiDataType.MAPINFO) {
				this.getItems().clear();
				Optional.ofNullable(main.getUnitManager().getMapInfo())
						.map(WordMapinfo::getMaps)
						.orElse(FunctionUtils.emptyList())
						.stream()
						.filter(map -> map.getHP()[0] > 0)
						.forEach(this.getItems()::add);
			}
		});
	}
}
