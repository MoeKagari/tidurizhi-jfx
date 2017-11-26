package tdrz.update.handler.kaisou;

import java.util.Map;

import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.handler.UnitHandler;

public class ApiKaisouOpenExSlot extends UnitHandler {
	private final OpenExSlot openExSlot;

	public ApiKaisouOpenExSlot(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		this.openExSlot = new OpenExSlot(Integer.parseInt(fields.get("api_id")));
	}

	@Override
	public OpenExSlot getOpenExSlot() {
		return this.openExSlot;
	}
}
