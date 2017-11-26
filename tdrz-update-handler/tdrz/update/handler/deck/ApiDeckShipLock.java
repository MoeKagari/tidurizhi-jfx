package tdrz.update.handler.deck;

import java.util.Map;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.handler.UnitHandler;

public class ApiDeckShipLock extends UnitHandler {
	private final DeckShipLock shipLock;

	public ApiDeckShipLock(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		int api_ship_id = Integer.parseInt(fields.get("api_ship_id"));
		boolean api_locked = ((JsonObject) api_data).getInt("api_locked") == 1;
		this.shipLock = new DeckShipLock(api_ship_id, api_locked);
	}

	@Override
	public DeckShipLock getDeckShipLock() {
		return this.shipLock;
	}
}
