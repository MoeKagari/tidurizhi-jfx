package tdrz.update.handler.ndock;

import java.util.Map;

import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.data.AbstractMemory.MemoryObjShip;
import tdrz.update.data.memory.MemoryNdock;
import tdrz.update.data.memory.ndock.MemoryNdockNyukyoStart;
import tdrz.update.data.word.WordShip;
import tdrz.update.handler.UnitHandler;
import tdrz.update.unit.UnitMaterial.MaterialChangeOption;
import tdrz.update.unit.UnitMemory.MemoryChange;

public class ApiNdockNyukyoStart extends UnitHandler {
	private final MemoryNdockNyukyoStart memoryNdockNyukyoStart;

	public ApiNdockNyukyoStart(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		//int api_ndock_id = Integer.parseInt(fields.get("api_ndock_id"));
		int api_ship_id = Integer.parseInt(fields.get("api_ship_id"));
		boolean api_highspeed = Integer.parseInt(fields.get("api_highspeed")) == 1;
		if (api_highspeed) {
			//使用高速修复,后无ndock
			//但无需刷新ndock
		} else {
			//不使用高速修复,后接ndock
			//无需手动刷新ndock
		}

		WordShip nyukyoedShip = unitManager.getShip(api_ship_id);
		int[] oldMaterial = unitManager.getCurrentMaterial().getAmount();
		int[] newMaterial = MaterialChangeOption.getNewMaterial(oldMaterial, nyukyoedShip.getNyukyoCost(), MaterialChangeOption.DECREASE);
		if (api_highspeed) {
			newMaterial = MaterialChangeOption.getNewMaterial(newMaterial, new int[] { 0, 0, 0, 0, 0, 1, 0, 0 }, MaterialChangeOption.DECREASE);
		}
		this.memoryNdockNyukyoStart = new MemoryNdockNyukyoStart(
				time, new MemoryObjShip(nyukyoedShip, unitManager::getSlotItem),
				oldMaterial, newMaterial
		/**/);
	}

	@Override
	public MemoryChange<MemoryNdock> getMemoryNdockChange() {
		return new MemoryChange<MemoryNdock>() {
			@Override
			public MemoryNdock getMemoryChange() {
				return ApiNdockNyukyoStart.this.memoryNdockNyukyoStart;
			}
		};
	}
}
