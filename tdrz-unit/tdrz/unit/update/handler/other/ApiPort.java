package tdrz.unit.update.handler.other;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.unit.update.UnitHandler;
import tdrz.unit.update.UnitManager;
import tdrz.unit.update.dto.word.ShipDto;
import tdrz.unit.update.handler.deck.ApiDeck;
import tdrz.unit.update.handler.ndock.ApiNdock;

public class ApiPort extends UnitHandler {
	private final UnitHandler basic, material, ndock, deck;
	private final Integer combined;
	private final List<ShipDto> ships;

	public ApiPort(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		JsonObject json = (JsonObject) api_data;

		this.basic = new ApiBasic(unitManager, time, fields, json.get("api_basic"));
		this.material = new ApiMaterial(unitManager, time, fields, json.get("api_material"));
		this.ndock = new ApiNdock(unitManager, time, fields, json.get("api_basic"));
		this.deck = new ApiDeck(unitManager, time, fields, json.get("api_material"));

		this.ships = json.getJsonArray("api_ship").getValuesAs(JsonObject.class).stream().map(ShipDto::new).collect(Collectors.toCollection(LinkedList::new));
		if (json.containsKey("api_combined_flag")) {
			this.combined = json.getInt("api_combined_flag");
		} else {
			this.combined = null;
		}
	}

	@Override
	public boolean needClearShipList() {
		return true;
	}

	@Override
	public List<ShipDto> getAddShipList() {
		return this.ships;
	}

	@Override
	public List<UnitHandler> otherUnitHandler() {
		return Arrays.asList(this.basic, this.material, this.ndock, this.deck);
	}

	@Override
	public Integer getCombined() {
		return this.combined;
	}
}
