package tdrz.update.handler.kaisou;

import java.util.Map;

import javax.json.JsonValue;

import tdrz.update.UnitHandler;
import tdrz.update.UnitManager;
import tdrz.update.part.other.ShipUnit.OpenExSlot;

public class ApiKaisouOpenExSlot extends UnitHandler {
	private final OpenExSlot oes;

	public ApiKaisouOpenExSlot(UnitManager unitManager,long time, Map<String, String> fields, JsonValue api_data) {
		this.oes = new OpenExSlot(Integer.parseInt(fields.get("api_id")));
	}

	@Override
	public OpenExSlot getOpenExSlot() {
		return this.oes;
	}
}