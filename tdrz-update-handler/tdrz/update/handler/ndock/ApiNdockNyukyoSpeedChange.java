package tdrz.update.handler.ndock;

import java.util.Map;

import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.data.AbstractMemory.MemoryObjShip;
import tdrz.update.data.memory.MemoryNdock;
import tdrz.update.data.memory.ndock.MemoryNdockNyukyoSpeedChange;
import tdrz.update.handler.UnitHandler;
import tdrz.update.unit.UnitMaterial.MaterialChangeOption;
import tdrz.update.unit.UnitMemory.MemoryChange;

public class ApiNdockNyukyoSpeedChange extends UnitHandler {
	private final int ndockNyukyoSpeedChange;
	private final MemoryNdockNyukyoSpeedChange memoryNdockNyukyoSpeedChange;

	public ApiNdockNyukyoSpeedChange(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		int api_ndock_id = Integer.parseInt(fields.get("api_ndock_id"));
		int[] oldMaterial = unitManager.getCurrentMaterial().getAmount();
		int[] newMaterial = MaterialChangeOption.getNewMaterial(oldMaterial, new int[] { 0, 0, 0, 0, 0, 1, 0, 0 }, MaterialChangeOption.DECREASE);

		this.ndockNyukyoSpeedChange = api_ndock_id;
		this.memoryNdockNyukyoSpeedChange = new MemoryNdockNyukyoSpeedChange(
				time, new MemoryObjShip(unitManager.getShip(unitManager.getNdock(api_ndock_id - 1).getShipId()), unitManager::getSlotItem),
				oldMaterial, newMaterial
		/**/);
	}

	@Override
	public MemoryChange<MemoryNdock> getMemoryNdockChange() {
		return new MemoryChange<MemoryNdock>() {
			@Override
			public MemoryNdock getMemoryChange() {
				return ApiNdockNyukyoSpeedChange.this.memoryNdockNyukyoSpeedChange;
			}
		};
	}

	@Override
	public Integer getNdockNyukyoSpeedChange() {
		return this.ndockNyukyoSpeedChange;
	}
}
