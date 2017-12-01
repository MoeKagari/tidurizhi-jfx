package tdrz.update.handler.quest;

import java.util.Map;
import java.util.Optional;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.data.memory.MemoryOther;
import tdrz.update.data.memory.other.MemoryQuestClear;
import tdrz.update.handler.UnitHandler;
import tdrz.update.unit.UnitMaterial.MaterialChangeOption;
import tdrz.update.unit.UnitMemory.MemoryChange;
import tool.JsonUtils;

public class ApiQuestClear extends UnitHandler {
	private final int[] newMaterial;
	private final MemoryQuestClear memoryQuestClear;

	public ApiQuestClear(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		JsonObject json = (JsonObject) api_data;

		int api_quest_id = Integer.parseInt(fields.get("api_quest_id"));
		int[] oldMaterial = unitManager.getCurrentMaterial().getAmount();
		int[] newMaterial = MaterialChangeOption.getNewMaterial(oldMaterial, JsonUtils.getIntArray(json, "api_material"), MaterialChangeOption.INCREASE_MAIN);

		//https://github.com/andanteyk/ElectronicObserver/blob/develop/ElectronicObserver/Other/Information/apilist.txt?ts=4#L894

		this.newMaterial = newMaterial;
		this.memoryQuestClear = Optional.ofNullable(unitManager.getQuest(api_quest_id))
				.map(quest -> {
					return new MemoryQuestClear(time, quest, oldMaterial, newMaterial, json.getJsonArray("api_bounus"));
				}).orElse(null);
	}

	@Override
	public MemoryChange<MemoryOther> getMemoryOtherChange() {
		return new MemoryChange<MemoryOther>() {
			@Override
			public MemoryOther getMemoryChange() {
				return ApiQuestClear.this.memoryQuestClear;
			}
		};
	}

	@Override
	public int[] getNewMaterial() {
		return this.newMaterial;
	}
}
