package tdrz.update.handler.deck;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.data.word.WordDeck;
import tdrz.update.handler.UnitHandler;
import tdrz.update.unit.UnitDeck.DeckUpdate;

public class ApiDeck extends UnitHandler {
	private final List<DeckUpdate> deckUpdates;

	public ApiDeck(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		this.deckUpdates = ((JsonArray) api_data).getValuesAs(JsonObject.class)
				.stream().map(json -> {
					int api_id = json.getInt("api_id");
					WordDeck deck = new WordDeck(time, json);
					return new DeckUpdate(api_id, deck);
				}).collect(Collectors.toList());
	}

	@Override
	public List<DeckUpdate> getDeckUpdate() {
		return this.deckUpdates;
	}
}
