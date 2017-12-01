package tdrz.update.handler.mission;

import java.util.Map;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.core.translator.MasterDataTranslator;
import tdrz.update.UnitManager;
import tdrz.update.data.AbstractMemory.MemoryObjDeck;
import tdrz.update.data.memory.MemoryMission;
import tdrz.update.data.memory.mission.MemoryMissionStart;
import tdrz.update.data.word.WordDeck;
import tdrz.update.handler.UnitHandler;
import tdrz.update.unit.UnitMemory.MemoryChange;

public class ApiMissionStart extends UnitHandler {
	private final MemoryMissionStart memoryMissionStart;

	public ApiMissionStart(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		JsonObject json = (JsonObject) api_data;

		int api_deck_id = Integer.parseInt(fields.get("api_deck_id"));
		int api_mission_id = Integer.parseInt(fields.get("api_mission_id"));
		long api_complatetime = json.getJsonNumber("api_complatetime").longValue();

		WordDeck deck = unitManager.getDeck(api_deck_id - 1);
		this.memoryMissionStart = new MemoryMissionStart(
				time,
				new MemoryObjDeck(api_deck_id, deck, unitManager::getShip, unitManager::getSlotItem),
				MasterDataTranslator.getMissionName(api_mission_id),
				api_complatetime
		/**/);
	}

	@Override
	public MemoryChange<MemoryMission> getMemoryMissionChange() {
		return new MemoryChange<MemoryMission>() {
			@Override
			public MemoryMission getMemoryChange() {
				return ApiMissionStart.this.memoryMissionStart;
			}
		};
	}
}
