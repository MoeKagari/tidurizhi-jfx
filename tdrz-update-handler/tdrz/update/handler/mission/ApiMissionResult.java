package tdrz.update.handler.mission;

import java.util.Map;
import java.util.Optional;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import org.apache.commons.lang3.ArrayUtils;

import tdrz.core.util.JsonUtils;
import tdrz.update.UnitManager;
import tdrz.update.data.AbstractMemory.MemoryObjDeck;
import tdrz.update.data.memory.MemoryMission;
import tdrz.update.data.memory.mission.MemoryMissionResult;
import tdrz.update.data.memory.mission.MemoryMissionResult.MissionResultItem;
import tdrz.update.data.word.WordDeck;
import tdrz.update.handler.UnitHandler;
import tdrz.update.unit.UnitMemory.MemoryChange;

public class ApiMissionResult extends UnitHandler {
	private final MemoryMissionResult memoryMissionResult;

	public ApiMissionResult(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		JsonObject json = (JsonObject) api_data;

		int api_deck_id = Integer.parseInt(fields.get("api_deck_id"));
		int api_get_exp = json.getInt("api_get_exp");
		//int api_member_lv = json.getInt("api_member_lv");
		int api_member_exp = json.getInt("api_member_exp");
		//int[] api_ship_id = ArrayUtils.remove(JsonUtils.getIntArray(json, "api_ship_id"), 0);
		int[] api_get_ship_exp = JsonUtils.getIntArray(json, "api_get_ship_exp");
		int[][] api_get_exp_lvup = json.getJsonArray("api_get_exp_lvup").getValuesAs(JsonArray.class).stream().map(JsonUtils::getIntArray).toArray(int[][]::new);
		String api_maparea_name = json.getString("api_maparea_name");
		String api_detail = json.getString("api_detail");
		String api_quest_name = json.getString("api_quest_name");
		int api_clear_result = json.getInt("api_clear_result");
		int[] api_get_material = Optional.ofNullable(json.get("api_get_material"))//有可能为  -1
				.map(jsonValue -> jsonValue instanceof JsonArray ? (JsonArray) jsonValue : null)
				.map(JsonUtils::getIntArray)
				.orElse(new int[] { 0, 0, 0, 0 });
		MissionResultItem[] missionResultItems = { null, null };
		int[] flags = JsonUtils.getIntArray(json, "api_useitem_flag");
		if (flags[0] != 0) {
			missionResultItems[0] = new MissionResultItem(json.getJsonObject("api_get_item1"), flags[0]);
		}
		if (flags[1] != 0) {
			missionResultItems[1] = new MissionResultItem(json.getJsonObject("api_get_item2"), flags[1]);
		}

		int[] oldMaterial = ArrayUtils.clone(unitManager.getCurrentMaterial().getAmount());
		WordDeck deck = unitManager.getDeck(api_deck_id - 1);
		this.memoryMissionResult = new MemoryMissionResult(
				time,
				new MemoryObjDeck(api_deck_id, deck, unitManager::getShip, unitManager::getSlotItem),
				api_maparea_name, api_detail, api_quest_name, api_clear_result,
				api_get_exp, api_member_exp,
				api_get_ship_exp, api_get_exp_lvup,
				oldMaterial, api_get_material,
				missionResultItems
		/**/);
	}

	@Override
	public MemoryChange<MemoryMission> getMemoryMissionChange() {
		return new MemoryChange<MemoryMission>() {
			@Override
			public MemoryMission getMemoryChange() {
				return ApiMissionResult.this.memoryMissionResult;
			}
		};
	}
}
