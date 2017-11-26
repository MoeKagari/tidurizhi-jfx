package tdrz.update.handler.kaisou;

import java.util.List;
import java.util.Map;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.data.word.WordShip;
import tdrz.update.handler.UnitHandler;
import tool.function.FunctionUtils;

public class ApiKaisouSlotDeprive extends UnitHandler {
	private final WordShip set_ship, unset_ship;

	public ApiKaisouSlotDeprive(UnitManager unitManager,long time, Map<String, String> fields, JsonValue api_data) {
		JsonObject json = ((JsonObject) api_data).getJsonObject("api_ship_data");
		this.set_ship = new WordShip(json.getJsonObject("api_set_ship"));
		this.unset_ship = new WordShip(json.getJsonObject("api_unset_ship"));
	}

	@Override
	public List<WordShip> getAddShipList() {
		return FunctionUtils.asList(this.set_ship, this.unset_ship);
	}
}
