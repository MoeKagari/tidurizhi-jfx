package tdrz.update.handler.other;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.data.memory.MemoryOther;
import tdrz.update.data.memory.other.MemoryCharge;
import tdrz.update.handler.UnitHandler;
import tdrz.update.unit.UnitMaterial.MaterialChangeOption;
import tdrz.update.unit.UnitMemory.MemoryChange;
import tool.JsonUtils;

public class ApiOtherCharge extends UnitHandler {
	private final List<Charge> chargeList;
	private final MemoryCharge memoryCharge;
	private final int[] newMaterial;

	public ApiOtherCharge(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		JsonObject json = (JsonObject) api_data;

		this.chargeList = json.getJsonArray("api_ship").getValuesAs(JsonObject.class).stream()
				.map(info -> {
					int api_id = info.getInt("api_id");
					int api_fuel = info.getInt("api_fuel");
					int api_bull = info.getInt("api_bull");
					int[] api_onslot = JsonUtils.getIntArray(info, "api_onslot");
					return new Charge(api_id, api_fuel, api_bull, api_onslot);
				})
				.collect(Collectors.toList());
		int[] oldMaterial = unitManager.getCurrentMaterial().getAmount();
		this.newMaterial = MaterialChangeOption.getNewMaterial(oldMaterial, JsonUtils.getIntArray(json, "api_material"), MaterialChangeOption.UPDATE_MAIN);
		this.memoryCharge = new MemoryCharge(time, oldMaterial, this.newMaterial);
	}

	@Override
	public MemoryChange<MemoryOther> getMemoryOtherChange() {
		return new MemoryChange<MemoryOther>() {
			@Override
			public MemoryOther getMemoryChange() {
				return ApiOtherCharge.this.memoryCharge;
			}
		};
	}

	@Override
	public int[] getNewMaterial() {
		return this.newMaterial;
	}

	@Override
	public List<Charge> getChargeList() {
		return this.chargeList;
	}
}
