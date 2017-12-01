package tdrz.update.handler.kdock;

import java.util.Map;

import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.data.AbstractMemory.MemoryObjShip;
import tdrz.update.data.memory.kdock.MemoryKdockCreateShip;
import tdrz.update.handler.UnitHandler;
import tdrz.update.unit.UnitKdock.KdockTemp_MemoryKdockCreateShip;
import tdrz.update.unit.UnitMaterial.MaterialChangeOption;

public class ApiKdockCreateShip extends UnitHandler {
	private final KdockTemp_MemoryKdockCreateShip kdockTemp_MemoryKdockCreateShip;
	private final int[] newMaterial;

	public ApiKdockCreateShip(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		int api_kdock_id = Integer.parseInt(fields.get("api_kdock_id"));
		boolean highspeed = Integer.parseInt(fields.get("api_highspeed")) == 1;
		boolean large_flag = Integer.parseInt(fields.get("api_large_flag")) == 1;
		int[] materialChange = {//
				Integer.parseInt(fields.get("api_item1")),//
				Integer.parseInt(fields.get("api_item2")),//
				Integer.parseInt(fields.get("api_item3")),//
				Integer.parseInt(fields.get("api_item4")),//
				highspeed ? (large_flag ? 10 : 1) : 0,//高速建造材
				0, Integer.parseInt(fields.get("api_item5")), 0 //
		};
		int[] oldMaterial = unitManager.getCurrentMaterial().getAmount();
		this.newMaterial = MaterialChangeOption.getNewMaterial(oldMaterial, materialChange, MaterialChangeOption.DECREASE);

		this.kdockTemp_MemoryKdockCreateShip = new KdockTemp_MemoryKdockCreateShip(
				api_kdock_id,
				new MemoryKdockCreateShip(
						time,
						oldMaterial, this.newMaterial,
						new MemoryObjShip(unitManager.getSecretaryShip(), unitManager::getSlotItem),
						large_flag, highspeed
				/**/)
		/**/);
	}

	@Override
	public int[] getNewMaterial() {
		return this.newMaterial;
	}

	@Override
	public KdockTemp_MemoryKdockCreateShip getKdockTempMemoryKdockCreateShip() {
		return this.kdockTemp_MemoryKdockCreateShip;
	}
}
