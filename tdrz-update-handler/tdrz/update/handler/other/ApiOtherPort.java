package tdrz.update.handler.other;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.data.word.WordShip;
import tdrz.update.handler.UnitHandler;
import tdrz.update.handler.deck.ApiDeck;
import tdrz.update.handler.ndock.ApiNdock;

public class ApiOtherPort extends UnitHandler {
	private final UnitHandler apiBasic, apiMaterial, apiNdock, apiDeck;
	private final Integer combined;
	private final List<WordShip> shipList;
	private final long timeWhenPort;

	public ApiOtherPort(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		JsonObject json = (JsonObject) api_data;

		this.apiBasic = new ApiOtherBasic(unitManager, time, fields, json.get("api_basic"));
		this.apiMaterial = new ApiOtherMaterial(unitManager, time, fields, json.get("api_material"));
		this.apiNdock = new ApiNdock(unitManager, time, fields, json.get("api_ndock"));
		this.apiDeck = new ApiDeck(unitManager, time, fields, json.get("api_deck_port"));

		this.shipList = json.getJsonArray("api_ship").getValuesAs(JsonObject.class).stream().map(WordShip::new).collect(Collectors.toCollection(LinkedList::new));
		if (json.containsKey("api_combined_flag")) {
			this.combined = json.getInt("api_combined_flag");
		} else {
			this.combined = null;
		}
		this.timeWhenPort = time;
	}

	@Override
	public Long resetWhenPort() {
		return this.timeWhenPort;
	}

	@Override
	public boolean needClearShipList() {
		return true;
	}

	@Override
	public List<WordShip> getAddShipList() {
		return this.shipList;
	}

	@Override
	public List<UnitHandler> otherUnitHandler() {
		return Arrays.asList(this.apiBasic, this.apiMaterial, this.apiNdock, this.apiDeck);
	}

	@Override
	public Integer getCombined() {
		return this.combined;
	}
}
