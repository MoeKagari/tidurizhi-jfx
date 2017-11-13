package tdrz.update.handler.quest;

import java.util.Map;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.core.util.JsonUtils;
import tdrz.update.handler.UnitHandler;
import tdrz.update.unit.UnitManager;
import tdrz.update.unit.other.MaterialUnit.MaterialChangeHodler;
import tdrz.update.unit.other.MaterialUnit.MaterialChangeHodler.MaterialChangeOption;

public class ApiQuestClear extends UnitHandler {
	private final int api_quest_id;
	private final MaterialChangeHodler mch;

	public ApiQuestClear(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		this.api_quest_id = Integer.parseInt(fields.get("api_quest_id"));
		this.mch = new MaterialChangeHodler(time, "任务完成", JsonUtils.getIntArray((JsonObject) api_data, "api_material"), MaterialChangeOption.INCREASE_MAIN);
	}

	@Override
	public MaterialChangeHodler getMaterialChangeHodler() {
		return this.mch;
	}

	@Override
	public Integer getQuestClear() {
		return this.api_quest_id;
	}
}
