package tdrz.update.handler.other;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.data.word.WordShip;
import tdrz.update.handler.UnitHandler;
import tdrz.update.handler.deck.ApiDeck;
import tool.function.FunctionUtils;

public class ApiOtherShip2 extends UnitHandler {
	private final UnitHandler apiDeck;
	private final List<WordShip> ships;

	public ApiOtherShip2(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		JsonObject json = (JsonObject) api_data;
		this.ships = json.getJsonArray("api_ship").getValuesAs(JsonObject.class).stream().map(WordShip::new).collect(Collectors.toList());
		this.apiDeck = new ApiDeck(unitManager, time, fields, json.get("api_data_deck"));
	}

	@Override
	public boolean needClearShipList() {
		return true;
	}

	@Override
	public List<WordShip> getAddShipList() {
		return this.ships;
	}

	@Override
	public List<UnitHandler> otherUnitHandler() {
		return FunctionUtils.asList(this.apiDeck);
	}
}
