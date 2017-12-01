package tdrz.update.handler.other;

import java.util.List;
import java.util.Map;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.data.AbstractMemory.MemoryObjShip;
import tdrz.update.data.memory.MemoryShip;
import tdrz.update.data.memory.ship.MemoryShipLostDestroy;
import tdrz.update.handler.UnitHandler;
import tdrz.update.unit.UnitMaterial.MaterialChangeOption;
import tdrz.update.unit.UnitMemory.MemoryChange;
import tool.JsonUtils;
import tool.function.FunctionUtils;

public class ApiOtherDestroyShip extends UnitHandler {
	private final int removedShipId;
	private final int[] newMaterial;
	private final MemoryShipLostDestroy memoryShipLostDestroy;

	public ApiOtherDestroyShip(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		this.removedShipId = Integer.parseInt(fields.get("api_ship_id"));
		int[] oldMaterial = unitManager.getCurrentMaterial().getAmount();
		this.newMaterial = MaterialChangeOption.getNewMaterial(oldMaterial, JsonUtils.getIntArray((JsonObject) api_data, "api_material"), MaterialChangeOption.UPDATE_MAIN);
		this.memoryShipLostDestroy = new MemoryShipLostDestroy(
				time,
				new MemoryObjShip[] { new MemoryObjShip(unitManager.getShip(this.removedShipId), unitManager::getSlotItem) },
				oldMaterial, this.newMaterial
		/**/);
	}

	@Override
	public List<Integer> getRemovedShipList() {
		return FunctionUtils.asList(this.removedShipId);
	}

	@Override
	public int[] getNewMaterial() {
		return this.newMaterial;
	}

	@Override
	public MemoryChange<MemoryShip> getMemoryShipChange() {
		return new MemoryChange<MemoryShip>() {
			@Override
			public MemoryShip getMemoryChange() {
				return ApiOtherDestroyShip.this.memoryShipLostDestroy;
			}
		};
	}
}
