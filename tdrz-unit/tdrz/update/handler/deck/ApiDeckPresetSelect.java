package tdrz.update.handler.deck;

import java.util.Map;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.data.word.WordDeck;
import tdrz.update.handler.UnitHandler;
import tdrz.update.unit.UnitManager;
import tdrz.update.unit.main.DeckUnit.DeckPresetSelect;

public class ApiDeckPresetSelect extends UnitHandler {
	private final DeckPresetSelect deckPresetSelect;

	public ApiDeckPresetSelect(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		JsonObject json = (JsonObject) api_data;
		this.deckPresetSelect = new DeckPresetSelect(json.getInt("api_id"), new WordDeck(time, json));
	}

	@Override
	public DeckPresetSelect getDeckPresetSelect() {
		return this.deckPresetSelect;
	}
}
