package tdrz.update.handler.kdock;

import java.util.Map;

import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.data.memory.MemoryKdock;
import tdrz.update.data.memory.kdock.MemoryKdockSpeedChange;
import tdrz.update.data.word.WordKdock;
import tdrz.update.handler.UnitHandler;
import tdrz.update.unit.UnitMaterial.MaterialChangeOption;
import tdrz.update.unit.UnitMemory.MemoryChange;

public class ApiKdockSpeedChange extends UnitHandler {
	private final int[] newMaterial;
	private final MemoryKdockSpeedChange memoryKdockSpeedChange;

	public ApiKdockSpeedChange(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		int api_kdock_id = Integer.parseInt(fields.get("api_kdock_id"));
		WordKdock kdock = unitManager.getKdock(api_kdock_id - 1);
		int[] oldMaterial = unitManager.getCurrentMaterial().getAmount();
		int[] newMaterial = MaterialChangeOption.getNewMaterial(
				oldMaterial,
				new int[] { 0, 0, 0, 0, kdock.largeFlag() ? 10 : 1, 0, 0, 0 },
				MaterialChangeOption.DECREASE
		/**/);

		this.newMaterial = newMaterial;
		this.memoryKdockSpeedChange = new MemoryKdockSpeedChange(time, oldMaterial, newMaterial);
	}

	@Override
	public int[] getNewMaterial() {
		return this.newMaterial;
	}

	@Override
	public MemoryChange<MemoryKdock> getMemoryKdockChange() {
		return new MemoryChange<MemoryKdock>() {
			@Override
			public MemoryKdock getMemoryChange() {
				return ApiKdockSpeedChange.this.memoryKdockSpeedChange;
			}
		};
	}
}
