package tdrz.unit.update.handler.other;

import java.util.Map;import tdrz.unit.update.UnitManager;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.unit.update.UnitHandler;
import tdrz.unit.update.dto.word.AirbaseDto;
import tdrz.unit.update.dto.word.MapinfoDto;

public class ApiMapInfo extends UnitHandler {
	private final AirbaseDto airBase;
	private final MapinfoDto mapInfo;

	public ApiMapInfo(UnitManager unitManager,long time, Map<String, String> fields, JsonValue api_data) {
		JsonObject json = (JsonObject) api_data;
		this.airBase = new AirbaseDto(json.getJsonArray("api_air_base"));
		this.mapInfo = new MapinfoDto(json.getJsonArray("api_map_info"));
	}

	@Override
	public AirbaseDto getAirBase() {
		return this.airBase;
	}

	@Override
	public MapinfoDto getMapInfo() {
		return this.mapInfo;
	}
}
