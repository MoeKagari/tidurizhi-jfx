package tdrz.unit.update.handler.other;

import java.util.Map;import tdrz.unit.update.UnitManager;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.unit.update.UnitHandler;

@SuppressWarnings("unused")
public class ApiCombined extends UnitHandler {
	private int api_combined_type, api_combined;

	public ApiCombined(UnitManager unitManager,long time, Map<String, String> fields, JsonValue api_data) {
		this.api_combined_type = Integer.parseInt(fields.get("api_combined_type"));
		this.api_combined = ((JsonObject) api_data).getInt("api_combined");
	}
}
