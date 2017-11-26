package tdrz.update.handler.mission;

import java.util.Map;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.core.translator.MasterDataTranslator;
import tdrz.update.UnitManager;
import tdrz.update.data.AbstractMemory.MemoryObjDeck;
import tdrz.update.data.memory.MemoryMission;
import tdrz.update.data.memory.mission.MemoryMissionReturn;
import tdrz.update.data.word.WordDeck;
import tdrz.update.handler.UnitHandler;
import tdrz.update.unit.UnitMemory.MemoryChange;

public class ApiMissionReturn extends UnitHandler {
	private final MemoryMissionReturn memoryMissionReturn;

	public ApiMissionReturn(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		JsonObject json = (JsonObject) api_data;

		//[0]={0=未出撃, 1=遠征中, 2=遠征帰投, 3=強制帰還中}, [1]=遠征先ID, [2]=帰投時間, [3]=0
		JsonArray api_mission = json.getJsonArray("api_mission");
		int api_deck_id = Integer.parseInt(fields.get("api_deck_id"));

		WordDeck deck = unitManager.getDeck(api_deck_id - 1);
		this.memoryMissionReturn = new MemoryMissionReturn(
				new MemoryObjDeck(api_deck_id, deck, unitManager::getShip, unitManager::getSlotItem),
				MasterDataTranslator.getMissionName(api_mission.getInt(1)),
				api_mission.getJsonNumber(2).longValue()
		/**/);
	}

	@Override
	public MemoryChange<MemoryMission> getMemoryMissionChange() {
		return new MemoryChange<MemoryMission>() {
			@Override
			public MemoryMission getMemoryChange() {
				return ApiMissionReturn.this.memoryMissionReturn;
			}
		};
	}
}
