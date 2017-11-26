package tdrz.update.handler.quest;

import java.util.Map;

import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.handler.UnitHandler;

public class ApiQuestClear extends UnitHandler {
	private final int api_quest_id;

	public ApiQuestClear(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		this.api_quest_id = Integer.parseInt(fields.get("api_quest_id"));
		//		this.materialChangeHolder = Optional.ofNullable(unitManager.getCurrentMaterial())
		//				.map(material -> {
		//					return new MaterialChange(material.getAmount(), JsonUtils.getIntArray((JsonObject) api_data, "api_material"), UnitMaterial.MaterialChangeOption.INCREASE_MAIN);
		//				}).orElse(null);
	}

	@Override
	public Integer getQuestClear() {
		return this.api_quest_id;
	}
}
