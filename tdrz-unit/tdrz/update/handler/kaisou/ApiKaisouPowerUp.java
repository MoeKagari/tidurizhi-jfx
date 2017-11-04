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

public class ApiKaisouPowerUp extends UnitHandler {
	private final UnitHandler deck;
	private final WordShip ship;
	private final List<Integer> ids;

	public ApiKaisouPowerUp(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		JsonObject json = (JsonObject) api_data;

		//boolean success = json.getInt("api_powerup_flag") == 1;
		//ShipDto oldship = GlobalContext.getShip(Integer.parseInt(data.getField("api_id")));

		this.ids = FunctionUtils.stream(fields.get("api_id_items").trim().split(",")).map(Integer::parseInt).collect(Collectors.toList());
		this.ship = new WordShip(json.getJsonObject("api_ship"));
		this.deck = new ApiDeck(unitManager, time, fields, json.get("api_deck"));
	}

	@Override
	public List<WordShip> getAddShipList() {
		return FunctionUtils.asList(this.ship);
	}

	@Override
	public List<Integer> getRemoveShipList() {
		return this.ids;
	}

	@Override
	public List<UnitHandler> otherUnitHandler() {
		return FunctionUtils.asList(this.deck);
	}
}
