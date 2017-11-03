package tdrz.unit.update.handler.kaisou;

import java.util.Map;import tdrz.unit.update.UnitManager;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.core.util.JsonUtils;
import tdrz.unit.update.UnitHandler;
import tdrz.unit.update.part.other.ShipUnit.SlotExchange;

public class ApiKaisouSlotExchange extends UnitHandler {
	private final SlotExchange se;

	public ApiKaisouSlotExchange(UnitManager unitManager,long time, Map<String, String> fields, JsonValue api_data) {
		int api_id = Integer.parseInt(fields.get("api_id"));
		int[] api_slot = JsonUtils.getIntArray((JsonObject) api_data, "api_slot");
		this.se = new SlotExchange(api_id, api_slot);
	}

	@Override
	public SlotExchange getSlotExchange() {
		return this.se;
	}
}
