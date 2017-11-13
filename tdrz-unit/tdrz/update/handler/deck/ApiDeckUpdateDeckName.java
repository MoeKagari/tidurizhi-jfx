package tdrz.update.handler.deck;

import java.util.Map;

import javax.json.JsonValue;

import tdrz.update.handler.UnitHandler;
import tdrz.update.unit.UnitManager;
import tdrz.update.unit.main.DeckUnit.DeckUpdateDeckName;

public class ApiDeckUpdateDeckName extends UnitHandler {
	private final DeckUpdateDeckName deckUpdateDeckName;

	public ApiDeckUpdateDeckName(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		int api_id = Integer.parseInt(fields.get("api_id"));
		String api_name = fields.get("api_name");
		this.deckUpdateDeckName = new DeckUpdateDeckName(api_id, api_name);
	}

	@Override
	public DeckUpdateDeckName getDeckUpdateDeckName() {
		return this.deckUpdateDeckName;
	}
}
