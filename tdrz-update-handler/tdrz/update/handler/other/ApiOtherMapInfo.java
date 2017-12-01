package tdrz.update.handler.other;

import java.util.Map;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.data.word.WordAirbase;
import tdrz.update.data.word.WordMapinfo;
import tdrz.update.handler.UnitHandler;

public class ApiOtherMapInfo extends UnitHandler {
	private final WordAirbase apiAirBase;
	private final WordMapinfo apiMapinfo;

	public ApiOtherMapInfo(UnitManager unitManager,long time, Map<String, String> fields, JsonValue api_data) {
		JsonObject json = (JsonObject) api_data;
		this.apiAirBase = new WordAirbase(json.getJsonArray("api_air_base"));
		this.apiMapinfo = new WordMapinfo(json.getJsonArray("api_map_info"));
	}

	@Override
	public WordAirbase getAirBase() {
		return this.apiAirBase;
	}

	@Override
	public WordMapinfo getMapinfo() {
		return this.apiMapinfo;
	}
}
