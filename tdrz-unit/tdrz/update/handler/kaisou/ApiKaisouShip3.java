package tdrz.update.handler.kaisou;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.UnitHandler;
import tdrz.update.UnitManager;
import tdrz.update.data.word.WordShip;
import tdrz.update.handler.deck.ApiDeck;
import tool.function.FunctionUtils;

public class ApiKaisouShip3 extends UnitHandler {
	private final List<WordShip> ships;
	private final UnitHandler deck;

	public ApiKaisouShip3(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		JsonObject json = (JsonObject) api_data;
		this.ships = json.getJsonArray("api_ship_data").getValuesAs(JsonObject.class).stream().map(WordShip::new).collect(Collectors.toList());
		this.deck = new ApiDeck(unitManager, time, fields, json.get("api_deck_data"));
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
