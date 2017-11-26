package tdrz.update.handler.other;

import java.util.Map;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.data.word.WordAirbase;
import tdrz.update.data.word.WordMapinfo;
import tdrz.update.handler.UnitHandler;

public class ApiMapInfo extends UnitHandler {
	private final WordAirbase airBase;
	private final WordMapinfo mapInfo;

	public ApiMapInfo(UnitManager unitManager,long time, Map<String, String> fields, JsonValue api_data) {
		JsonObject json = (JsonObject) api_data;
		this.airBase = new WordAirbase(json.getJsonArray("api_air_base"));
		this.mapInfo = new WordMapinfo(json.getJsonArray("api_map_info"));
	}

	@Override
	public WordAirbase getAirBase() {
		return this.airBase;
	}

	@Override
	public WordMapinfo getMapInfo() {
		return this.mapInfo;
	}
}
