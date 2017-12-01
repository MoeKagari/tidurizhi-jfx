package tdrz.update.handler.kaisou;

import java.util.Map;
import java.util.Optional;

import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.data.AbstractMemory.MemoryObjShip;
import tdrz.update.data.memory.MemoryShip;
import tdrz.update.data.memory.ship.MemoryShipOpenExSlot;
import tdrz.update.handler.UnitHandler;
import tdrz.update.unit.UnitMemory.MemoryChange;

public class ApiKaisouOpenExSlot extends UnitHandler {
	private final OpenExSlot openExSlot;
	private final MemoryShipOpenExSlot memoryShipOpenExSlot;

	public ApiKaisouOpenExSlot(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		int api_id = Integer.parseInt(fields.get("api_id"));
		this.openExSlot = new OpenExSlot(api_id);
		this.memoryShipOpenExSlot = Optional.ofNullable(unitManager.getShip(api_id))
				.map(ship -> new MemoryShipOpenExSlot(time, new MemoryObjShip(ship, unitManager::getSlotItem)))
				.orElse(null);
	}

	@Override
	public MemoryChange<MemoryShip> getMemoryShipChange() {
		return new MemoryChange<MemoryShip>() {
			@Override
			public MemoryShip getMemoryChange() {
				return ApiKaisouOpenExSlot.this.memoryShipOpenExSlot;
			}
		};
	}

	@Override
	public OpenExSlot getOpenExSlot() {
		return this.openExSlot;
	}
}
