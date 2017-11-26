package tdrz.gui.main.part;

import java.util.function.IntFunction;

import javafx.scene.control.TableView;
import tdrz.gui.main.ApplicationMainPart;
import tdrz.gui.main.ApplicationMainTDRZJFX;
import tdrz.server.RawApiDataType;
import tdrz.update.data.word.WordQuest;
import tool.FXUtils;

public class PartQuest extends TableView<WordQuest> implements ApplicationMainPart {
	public PartQuest(ApplicationMainTDRZJFX main) {
		FXUtils.addNewColumn(this, "编号", FXUtils.getIntegerTableCellFactory(), WordQuest::getNo);
		FXUtils.addNewColumn(this, "状态", FXUtils.getStringTableCellFactory(), WordQuest::getStateString);
		FXUtils.addNewColumn(this, "进度", FXUtils.getStringTableCellFactory(), WordQuest::getProcess);
		FXUtils.addNewColumn(this, "任务", FXUtils.getStringTableCellFactory(), WordQuest::getTitle);
		FXUtils.addNewColumn(this, "种类", FXUtils.getStringTableCellFactory(), WordQuest::getCategoryString);
		FXUtils.addNewColumn(this, "类型", FXUtils.getStringTableCellFactory(), WordQuest::getTypeString);
		IntFunction<String> materialString = material -> material <= 0 ? "" : String.valueOf(material);
		FXUtils.addNewColumn(this, "油", FXUtils.getStringTableCellFactory(), rd -> materialString.apply(rd.getMaterial()[0]));
		FXUtils.addNewColumn(this, "弹", FXUtils.getStringTableCellFactory(), rd -> materialString.apply(rd.getMaterial()[0]));
		FXUtils.addNewColumn(this, "钢", FXUtils.getStringTableCellFactory(), rd -> materialString.apply(rd.getMaterial()[0]));
		FXUtils.addNewColumn(this, "铝", FXUtils.getStringTableCellFactory(), rd -> materialString.apply(rd.getMaterial()[0]));
		FXUtils.addNewColumn(this, "详细", FXUtils.getStringTableCellFactory(), WordQuest::getDetail);

		main.getUnitManager().addListener(type -> {
			if (type == RawApiDataType.QUEST_LIST) {
				this.getItems().setAll(main.getUnitManager().getQuests());
			}
		});
	}
}
