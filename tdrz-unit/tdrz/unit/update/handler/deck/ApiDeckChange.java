package tdrz.unit.update.handler.deck;

import java.util.Map;

import javax.json.JsonValue;

import tdrz.unit.update.UnitHandler;
import tdrz.unit.update.UnitManager;
import tdrz.unit.update.part.DeckUnit.DeckChange;

public class ApiDeckChange extends UnitHandler {
	private final DeckChange deckChange;

	public ApiDeckChange(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		int api_id = Integer.parseInt(fields.get("api_id"));
		int api_ship_idx = Integer.parseInt(fields.get("api_ship_idx"));//变更位置,0开始
		int api_ship_id = Integer.parseInt(fields.get("api_ship_id"));
		this.deckChange = new DeckChange(api_id, api_ship_idx, api_ship_id);
	}

	@Override
	public DeckChange getDeckChange() {
		return this.deckChange;
	}
}
