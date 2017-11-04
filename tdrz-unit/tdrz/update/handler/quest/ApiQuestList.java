package tdrz.update.handler.quest;

import java.util.Map;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.UnitHandler;
import tdrz.update.UnitManager;
import tdrz.update.part.QuestUnit.QuestListChangeHolder;

public class ApiQuestList extends UnitHandler {
	private final QuestListChangeHolder questListChange;

	public ApiQuestList(UnitManager unitManager,long time, Map<String, String> fields, JsonValue api_data) {
		this.questListChange = new QuestListChangeHolder(Integer.parseInt(fields.get("api_tab_id")), (JsonObject) api_data);
	}

	@Override
	public QuestListChangeHolder getQuestListChange() {
		return this.questListChange;
	}
}
