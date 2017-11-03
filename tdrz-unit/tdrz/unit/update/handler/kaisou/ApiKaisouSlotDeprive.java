package tdrz.unit.update.handler.kaisou;

import java.util.List;
import java.util.Map;import tdrz.unit.update.UnitManager;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.unit.update.UnitHandler;
import tdrz.unit.update.dto.word.ShipDto;
import tool.function.FunctionUtils;

public class ApiKaisouSlotDeprive extends UnitHandler {
	private final ShipDto set_ship, unset_ship;

	public ApiKaisouSlotDeprive(UnitManager unitManager,long time, Map<String, String> fields, JsonValue api_data) {
		JsonObject json = ((JsonObject) api_data).getJsonObject("api_ship_data");
		this.set_ship = new ShipDto(json.getJsonObject("api_set_ship"));
		this.unset_ship = new ShipDto(json.getJsonObject("api_unset_ship"));
	}

	@Override
	public List<ShipDto> getAddShipList() {
		return FunctionUtils.asList(this.set_ship, this.unset_ship);
	}
}
