package tdrz.unit.update.handler.kaisou;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.unit.update.UnitHandler;
import tdrz.unit.update.UnitManager;
import tdrz.unit.update.dto.word.ShipDto;
import tdrz.unit.update.handler.deck.ApiDeck;
import tool.function.FunctionUtils;

public class ApiKaisouShip3 extends UnitHandler {
	private final List<ShipDto> ships;
	private final UnitHandler deck;

	public ApiKaisouShip3(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		JsonObject json = (JsonObject) api_data;
		this.ships = json.getJsonArray("api_ship_data").getValuesAs(JsonObject.class).stream().map(ShipDto::new).collect(Collectors.toList());
		this.deck = new ApiDeck(unitManager, time, fields, json.get("api_deck_data"));
	}

	@Override
	public List<ShipDto> getAddShipList() {
		return this.ships;
	}

	@Override
	public List<UnitHandler> otherUnitHandler() {
		return FunctionUtils.asList(this.deck);
	}
}
