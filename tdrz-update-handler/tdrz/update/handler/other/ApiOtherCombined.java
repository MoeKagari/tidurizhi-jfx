package tdrz.update.handler.other;

import java.util.Map;

import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.handler.UnitHandler;

public class ApiOtherCombined extends UnitHandler {
	private int api_combined_type;

	public ApiOtherCombined(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		this.api_combined_type = Integer.parseInt(fields.get("api_combined_type"));
		//api_combined = ((JsonObject) api_data).getInt("api_combined");
	}

	@Override
	public Integer getCombined() {
		return this.api_combined_type;
	}
}
