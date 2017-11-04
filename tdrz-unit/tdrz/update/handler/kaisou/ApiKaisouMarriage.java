package tdrz.update.handler.kaisou;

import java.util.List;
import java.util.Map;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.UnitHandler;
import tdrz.update.UnitManager;
import tdrz.update.data.word.WordShip;
import tool.function.FunctionUtils;

public class ApiKaisouMarriage extends UnitHandler {
	private final WordShip newShip;

	public ApiKaisouMarriage(UnitManager unitManager,long time, Map<String, String> fields, JsonValue api_data) {
		this.newShip = new WordShip((JsonObject) api_data);
	}

	@Override
	public List<WordShip> getAddShipList() {
		return FunctionUtils.asList(this.newShip);
	}
}