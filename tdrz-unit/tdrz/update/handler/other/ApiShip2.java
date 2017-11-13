package tdrz.update.handler.other;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.data.word.WordShip;
import tdrz.update.handler.UnitHandler;
import tdrz.update.handler.deck.ApiDeck;
import tdrz.update.unit.UnitManager;
import tool.function.FunctionUtils;

public class ApiShip2 extends UnitHandler {
	private final UnitHandler deck;
	private final List<WordShip> ships;

	public ApiShip2(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		JsonObject json = (JsonObject) api_data;
		this.ships = json.getJsonArray("api_ship").getValuesAs(JsonObject.class).stream().map(WordShip::new).collect(Collectors.toCollection(LinkedList::new));
		this.deck = new ApiDeck(unitManager, time, fields, json.get("api_data_deck"));
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
		return FunctionUtils.asList(this.deck);
	}
}
