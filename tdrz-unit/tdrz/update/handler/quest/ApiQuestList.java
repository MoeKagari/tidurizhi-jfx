package tdrz.update.handler.quest;

import java.util.Map;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.handler.UnitHandler;
import tdrz.update.unit.UnitManager;
import tdrz.update.unit.main.QuestUnit.QuestListChange;

public class ApiQuestList extends UnitHandler {
	private final QuestListChange questListChange;

	public ApiQuestList(UnitManager unitManager,long time, Map<String, String> fields, JsonValue api_data) {
		this.questListChange = new QuestListChange(Integer.parseInt(fields.get("api_tab_id")), (JsonObject) api_data);
	}

	@Override
	public QuestListChange getQuestListChange() {
		return this.questListChange;
	}
}
