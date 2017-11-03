package tdrz.unit.update.handler.deck;

import java.util.Map;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.unit.update.UnitHandler;
import tdrz.unit.update.UnitManager;
import tdrz.unit.update.part.other.ShipUnit.ShipLock;

public class ApiDeckShipLock extends UnitHandler {
	private final ShipLock shipLock;

	public ApiDeckShipLock(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		int api_ship_id = Integer.parseInt(fields.get("api_ship_id"));
		boolean api_locked = ((JsonObject) api_data).getInt("api_locked") == 1;
		this.shipLock = new ShipLock(api_ship_id, api_locked);
	}

	@Override
	public ShipLock getShipLock() {
		return this.shipLock;
	}
}
