package tdrz.unit.update.handler.kaisou;

import java.util.Map;import tdrz.unit.update.UnitManager;

import javax.json.JsonValue;

import tdrz.unit.update.UnitHandler;
import tdrz.unit.update.part.other.ShipUnit.OpenExSlot;

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
