package tdrz.update.handler.other;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.data.memory.MemoryOther;
import tdrz.update.data.memory.other.MemoryMaterial;
import tdrz.update.handler.UnitHandler;
import tdrz.update.unit.UnitMaterial.MaterialChangeOption;
import tdrz.update.unit.UnitMemory.MemoryChange;
import tool.function.FunctionUtils;

public class ApiOtherMaterial extends UnitHandler {
	private final MemoryMaterial memoryMaterial;
	private final int[] newMaterial;

	public ApiOtherMaterial(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		int[] oldMaterial = unitManager.getCurrentMaterial().getAmount();
		int[] materialChange = ((JsonArray) api_data).getValuesAs(JsonObject.class).stream()
				.sorted(Comparator.comparingInt(json -> json.getInt("api_id")))// 1到8
				.mapToInt(json -> json.getInt("api_value")).toArray();
		int[] newMaterial = MaterialChangeOption.getNewMaterial(oldMaterial, materialChange, MaterialChangeOption.UPDATE);

		if (FunctionUtils.isFalse(Arrays.equals(oldMaterial, newMaterial))) {
			this.newMaterial = newMaterial;
			this.memoryMaterial = new MemoryMaterial(time, "刷新", oldMaterial, newMaterial);
		} else {
			this.newMaterial = null;
			this.memoryMaterial = null;
		}
	}

	@Override
	public int[] getNewMaterial() {
		return this.newMaterial;
	}

	@Override
	public MemoryChange<MemoryOther> getMemoryOtherChange() {
		return new MemoryChange<MemoryOther>() {
			@Override
			public MemoryOther getMemoryChange() {
				return ApiOtherMaterial.this.memoryMaterial;
			}
		};
	}
}
