package tdrz.update.handler.deck;

import java.util.Map;

import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.data.AbstractMemory.MemoryObjDeck;
import tdrz.update.data.memory.MemoryOther;
import tdrz.update.data.memory.other.MemoryDeckRename;
import tdrz.update.data.word.WordDeck;
import tdrz.update.handler.UnitHandler;
import tdrz.update.unit.UnitDeck.DeckUpdateDeckName;
import tdrz.update.unit.UnitMemory.MemoryChange;

public class ApiDeckUpdateDeckName extends UnitHandler {
	private final DeckUpdateDeckName deckUpdateDeckName;
	private final MemoryDeckRename memoryDeckRename;

	public ApiDeckUpdateDeckName(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		int api_id = Integer.parseInt(fields.get("api_id"));
		String api_name = fields.get("api_name");
		WordDeck deck = unitManager.getDeck(api_id);

		this.deckUpdateDeckName = new DeckUpdateDeckName(api_id, api_name);
		this.memoryDeckRename = new MemoryDeckRename(
				time,
				new MemoryObjDeck(api_id, deck, unitManager::getShip, unitManager::getSlotItem),
				deck.getDeckName(), api_name
		/**/);
	}

	@Override
	public MemoryChange<MemoryOther> getMemoryOtherChange() {
		return new MemoryChange<MemoryOther>() {
			@Override
			public MemoryOther getMemoryChange() {
				return ApiDeckUpdateDeckName.this.memoryDeckRename;
			}
		};
	}

	@Override
	public DeckUpdateDeckName getDeckUpdateDeckName() {
		return this.deckUpdateDeckName;
	}
}
