package tdrz.unit.update.handler.kaisou;

import java.util.List;
import java.util.Map;import tdrz.unit.update.UnitManager;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.unit.update.UnitHandler;
import tdrz.unit.update.dto.word.ShipDto;
import tool.function.FunctionUtils;

public class ApiKaisouMarriage extends UnitHandler {
	private final ShipDto newShip;

	public ApiKaisouMarriage(UnitManager unitManager,long time, Map<String, String> fields, JsonValue api_data) {
		this.newShip = new ShipDto((JsonObject) api_data);
	}

	@Override
	public List<ShipDto> getAddShipList() {
		return FunctionUtils.asList(this.newShip);
	}
}
