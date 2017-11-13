package tdrz.update.unit.main;

import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.data.word.WordQuest;
import tdrz.update.unit.UnitManager;
import tdrz.update.unit.UnitManager.Unit;
import tdrz.update.unit.main.QuestUnit.QuestUnitHandler;
import tool.function.FunctionUtils;

public class QuestUnit extends Unit<QuestUnitHandler> {
	private final static int ONE_PAGE_QUEST_NUMBER = 5;

	@Override
	public void accept(UnitManager unitManager, QuestUnitHandler unitHandler) {
		FunctionUtils.notNull(unitHandler.getQuestListChange(), questListChange -> {

		});
		FunctionUtils.notNull(unitHandler.getQuestClear(), questClear -> {

		});
	}

	public static interface QuestUnitHandler {
		public default QuestListChange getQuestListChange() {
			return null;
		}

		/** 完成的任务的 id */
		public default Integer getQuestClear() {
			return null;
		}
	}

	public static class QuestListChange {
		public final int tab_id;
		public final JsonObject json;

		public QuestListChange(int tab_id, JsonObject json) {
			this.tab_id = tab_id;
			this.json = json;
		}
	}

	/** 未完成,暂无用 */
	public class QuestList {
		//各个 tab 下的任务
		//0=所有任务,9=进行中的任务 , 1,2,3,4,5=各种类别 , 其余 = 占位符
		private WordQuest[][] tabs = new WordQuest[10][];
		private int api_exec_count;

		public int getExecCount() {
			return this.api_exec_count;
		}

		public void changeQuestList(int tab_id, JsonObject json) {
			//进行中的任务数
			this.api_exec_count = json.getInt("api_exec_count");

			int api_count = json.getInt("api_count");//当前 tab 的任务总数
			//int api_page_count = json.getInt("api_page_count");//当前 tab 的总页数
			int api_disp_page = json.getInt("api_disp_page") - 1;//当前显示的页
			JsonValue api_list_value = json.get("api_list");

			WordQuest[] newValue = new WordQuest[api_count];
			for (int index = 0; index < newValue.length; index++) {
				newValue[index] = WordQuest.QUEST_UNKNOWN;
			}

			if (api_list_value instanceof JsonArray) {
				JsonArray api_list = (JsonArray) api_list_value;
				for (int no = 0, size = api_list.size(); no < size; no++) {
					JsonValue questValue = api_list.get(no);
					if (questValue instanceof JsonObject) {
						newValue[api_disp_page * ONE_PAGE_QUEST_NUMBER + no] = new WordQuest((JsonObject) questValue);
					} else if (questValue instanceof JsonNumber) {
						//类别最后一页空位为-1
						//无需处理
					} else {
						throw new RuntimeException("未处理的 questValue 类型");
					}
				}
			} else if (api_list_value == JsonValue.NULL) {
				//空页,无需处理
			} else {
				throw new RuntimeException("未处理的 api_list_value 类型");
			}

			this.tabs[tab_id] = this.combineTab(tab_id, api_disp_page, newValue);
		}

		private WordQuest[] combineTab(int tab_id, int change_page, WordQuest[] newValue) {
			WordQuest[] oldValue = this.tabs[tab_id];

			//程序正常接受全部 api 的情况下

			//无初始数据,直接返回
			if (oldValue == null) {
				//newValue 必然为第一页
				return newValue;
			}

			//newValue 不可能为 null

			//旧数据 = 0
			//表示在其它 tab 进行更改,导致此 tab 变化
			//无需合并,直接返回
			if (oldValue.length == 0) {
				return newValue;
			}

			//新数据 = 0
			//表示此 tab 已无数据
			//无需合并,直接返回
			if (newValue.length == 0) {
				return newValue;
			}

			WordQuest[] combine = new WordQuest[newValue.length];

			//旧数据的第一页必然有数据
			if (change_page == 0) {

			}

			return combine;
		}
	}
}
