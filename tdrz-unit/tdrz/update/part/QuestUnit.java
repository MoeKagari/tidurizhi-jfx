package tdrz.update.part;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.Unit;
import tdrz.update.UnitManager;
import tdrz.update.data.word.WordQuest;
import tdrz.update.part.QuestUnit.QuestUnitHandler;
import tool.function.FunctionUtils;

public class QuestUnit extends Unit<QuestUnitHandler> {
	private int count;//总任务数
	private int pageCount;//总页数
	private int execCount;//正在执行的任务数
	private final List<WordQuest> questList = new ArrayList<>();	//所有任务

	public int getCount() {
		return this.count;
	}

	public int getPageCount() {
		return this.pageCount;
	}

	public int getExecCount() {
		return this.execCount;
	}

	public List<WordQuest> getQuestlist() {
		return this.questList;
	}

	@Override
	public void accept(UnitManager unitManager, QuestUnitHandler unitHandler) {
		FunctionUtils.notNull(unitHandler.getQuestListChange(), this::changeQuestList);
	}

	private void changeQuestList(QuestListChangeHolder qch) {
		//TODO 未完善
		int tab_id = qch.tab_id;
		JsonObject json = qch.json;

		this.count = json.getInt("api_count");
		this.pageCount = json.getInt("api_page_count");
		this.execCount = json.getInt("api_exec_count");

		JsonValue value = json.get("api_list");
		int currentPage = json.getInt("api_disp_page");
		//进行差分处理
		if (value instanceof JsonArray) {
			JsonArray array = (JsonArray) value;
			for (int no = 0; no < array.size(); no++) {
				JsonValue questValue = array.get(no);
				if (questValue instanceof JsonObject) {
					WordQuest quest = new WordQuest(currentPage, no, (JsonObject) questValue);
					//移除quest_no相同或者(page和no)相同的任务
					this.questList.removeIf(ele -> (ele.getInformation().getNo() == quest.getInformation().getNo() || (ele.getPage() == quest.getPage() && ele.getNo() == quest.getNo())));
					this.questList.add(quest);
				} else if (questValue instanceof JsonNumber) {
					//类别最后一页空位为-1
					//清除之后的任务
					//TODO
				}
			}
		} else if (value instanceof JsonNumber) {
			//空页为-1,清除当前类别的所有任务
			if (tab_id == 0) {//所有任务
				this.questList.clear();
			} else if (tab_id == 9) {//进行中的任务
				this.questList.removeIf(quest -> quest.getInformation().getState() == 2);
			} else {
				this.questList.removeIf(quest -> quest.getInformation().getType() == tab_id);
			}
		} else if (value == JsonValue.NULL) {
			//最后页只有一个任务,完成任务后,重新获取任务列表时,此项为null
			//此时清除存储的当前页以及以后页的任务
			this.questList.removeIf(quest -> quest.getPage() >= currentPage);
		} else {
			throw new RuntimeException("有未完成的case\n");
		}

		//按照no排序
		this.questList.sort(Comparator.comparingInt(obj -> obj.getInformation().getNo()));
	}

	public static interface QuestUnitHandler {
		public default QuestListChangeHolder getQuestListChange() {
			return null;
		}
	}

	public static class QuestListChangeHolder {
		private final int tab_id;
		private final JsonObject json;

		public QuestListChangeHolder(int tab_id, JsonObject json) {
			this.tab_id = tab_id;
			this.json = json;
		}
	}
}
