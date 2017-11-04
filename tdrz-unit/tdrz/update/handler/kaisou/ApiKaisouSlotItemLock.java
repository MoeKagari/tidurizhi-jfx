package tdrz.update.handler.kaisou;

import java.util.Map;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.UnitHandler;
import tdrz.update.UnitManager;
import tdrz.update.part.other.SlotItemUnit.SlotItemLock;

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
