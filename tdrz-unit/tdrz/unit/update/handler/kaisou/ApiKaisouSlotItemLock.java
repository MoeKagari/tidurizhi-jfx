package tdrz.unit.update.handler.kaisou;

import java.util.Map;import tdrz.unit.update.UnitManager;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.unit.update.UnitHandler;
import tdrz.unit.update.part.other.SlotItemUnit.SlotItemLock;

public class ApiKaisouSlotItemLock extends UnitHandler {
	private final SlotItemLock lock;

	public ApiKaisouSlotItemLock(UnitManager unitManager,long time, Map<String, String> fields, JsonValue api_data) {
		int id = Integer.parseInt(fields.get("api_slotitem_id"));
		int lock_value = ((JsonObject) api_data).getInt("api_locked");
		this.lock = new SlotItemLock(id, lock_value == 1);
	}

	@Override
	public SlotItemLock getSlotItemLock() {
		return this.lock;
	}
}
