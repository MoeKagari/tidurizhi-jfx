package tdrz.update.handler.kaisou;

import java.util.Map;

import javax.json.JsonValue;

import tdrz.core.translator.ShipTranslator;
import tdrz.update.UnitManager;
import tdrz.update.data.AbstractMemory.MemoryObjShip;
import tdrz.update.data.memory.MemoryShip;
import tdrz.update.data.memory.ship.MemoryShipRemodeling;
import tdrz.update.data.word.WordShip;
import tdrz.update.handler.UnitHandler;
import tdrz.update.unit.UnitMaterial.MaterialChangeOption;
import tdrz.update.unit.UnitMemory.MemoryChange;

public class ApiKaisouRemodeling extends UnitHandler {
	private final MemoryShipRemodeling memoryShipRemodeling;

	public ApiKaisouRemodeling(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		//后接ship3,slot_item,material , 不需要属性资源 , 只需记录
		WordShip oldShip = unitManager.getShip(Integer.parseInt(fields.get("api_id")));
		int[] oldMaterial = unitManager.getCurrentMaterial().getAmount();
		int[] newMaterial = MaterialChangeOption.getNewMaterial(oldMaterial, ShipTranslator.getRemodelConsumption(oldShip), MaterialChangeOption.DECREASE);

		this.memoryShipRemodeling = new MemoryShipRemodeling(
				time,
				new MemoryObjShip(oldShip, unitManager::getSlotItem),
				oldMaterial, newMaterial,
				ShipTranslator.getRemodelDrawing(oldShip),
				ShipTranslator.getRemodelCatapult(oldShip)
		/**/);
	}

	@Override
	public MemoryChange<MemoryShip> getMemoryShipChange() {
		return new MemoryChange<MemoryShip>() {
			@Override
			public MemoryShip getMemoryChange() {
				return ApiKaisouRemodeling.this.memoryShipRemodeling;
			}
		};
	}
}
