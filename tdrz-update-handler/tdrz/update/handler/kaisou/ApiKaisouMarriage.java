package tdrz.update.handler.kaisou;

import java.util.List;
import java.util.Map;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.data.AbstractMemory.MemoryObjShip;
import tdrz.update.data.memory.MemoryShip;
import tdrz.update.data.memory.ship.MemoryShipMarriage;
import tdrz.update.data.word.WordShip;
import tdrz.update.handler.UnitHandler;
import tdrz.update.unit.UnitMemory.MemoryChange;
import tool.function.FunctionUtils;

public class ApiKaisouMarriage extends UnitHandler {
	private final WordShip newShip;
	private final MemoryShipMarriage memoryShipMarriage;

	public ApiKaisouMarriage(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		this.newShip = new WordShip((JsonObject) api_data);
		this.memoryShipMarriage = new MemoryShipMarriage(time, new MemoryObjShip(this.newShip, unitManager::getSlotItem));
	}

	@Override
	public MemoryChange<MemoryShip> getMemoryShipChange() {
		return new MemoryChange<MemoryShip>() {
			@Override
			public MemoryShip getMemoryChange() {
				return ApiKaisouMarriage.this.memoryShipMarriage;
			}
		};
	}

	@Override
	public List<WordShip> getAddShipList() {
		return FunctionUtils.asList(this.newShip);
	}
}
