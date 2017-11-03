package tdrz.unit.update.part;

import java.util.Collections;
import java.util.List;

import tdrz.unit.update.Unit;
import tdrz.unit.update.UnitManager;
import tdrz.unit.update.dto.word.DeckDto;
import tdrz.unit.update.part.DeckUnit.DeckUnitHandler;
import tool.function.FunctionUtils;

public class DeckUnit extends Unit<DeckUnitHandler> {
	private final DeckHolder[] deckHolders = { new DeckHolder(1), new DeckHolder(2), new DeckHolder(3), new DeckHolder(4) };

	@Override
	public void accept(UnitManager unitManager, DeckUnitHandler unitHandler) {
		unitHandler.getDeckUpdate().forEach(deckUpdate -> {
			FunctionUtils.forEach(this.deckHolders, deckHolder -> {
				if (deckUpdate.api_id == deckHolder.id) {
					deckHolder.deck = deckUpdate.deck;
				}
			});
		});

		FunctionUtils.notNull(unitHandler.getDeckChange(), deckChange -> {
			FunctionUtils.forEach(this.deckHolders, deckHolder -> {
				if (deckChange.api_id == deckHolder.id) {
					//TODO
				}
			});
		});

		FunctionUtils.notNull(unitHandler.getDeckPresetSelect(), deckPresetSelect -> {
			FunctionUtils.forEach(this.deckHolders, deckHolder -> {
				if (deckPresetSelect.api_id == deckHolder.id) {
					deckHolder.deck = deckPresetSelect.deck;
				}
			});
		});

		FunctionUtils.notNull(unitHandler.getDeckUpdateDeckName(), deckUpdateDeckName -> {
			FunctionUtils.forEach(this.deckHolders, deckHolder -> {
				if (deckUpdateDeckName.api_id == deckHolder.id) {
					deckHolder.deck.setDeckName(deckUpdateDeckName.api_name);
				}
			});
		});
	}

	public static interface DeckUnitHandler {
		public default List<DeckUpdate> getDeckUpdate() {
			return Collections.emptyList();
		}

		public default DeckChange getDeckChange() {
			return null;
		}

		public default DeckPresetSelect getDeckPresetSelect() {
			return null;
		}

		public default DeckUpdateDeckName getDeckUpdateDeckName() {
			return null;
		}
	}

	public static class DeckHolder {
		private final int id;//1,2,3,4
		private DeckDto deck;

		public DeckHolder(int id) {
			this.id = id;
		}

		public DeckDto getDeck() {
			return this.deck;
		}
	}

	public static class DeckUpdate {
		public final int api_id;
		public final DeckDto deck;

		public DeckUpdate(int api_id, DeckDto deck) {
			this.api_id = api_id;
			this.deck = deck;
		}
	}

	public static class DeckChange {
		public final int api_id, api_ship_idx, api_ship_id;

		public DeckChange(int api_id, int api_ship_idx, int api_ship_id) {
			this.api_id = api_id;
			this.api_ship_idx = api_ship_idx;
			this.api_ship_id = api_ship_id;
		}
	}

	public static class DeckPresetSelect {
		public final int api_id;
		public final DeckDto deck;

		public DeckPresetSelect(int api_id, DeckDto deck) {
			this.api_id = api_id;
			this.deck = deck;
		}
	}

	public static class DeckUpdateDeckName {
		public final int api_id;
		public final String api_name;

		public DeckUpdateDeckName(int api_id, String api_name) {
			this.api_id = api_id;
			this.api_name = api_name;
		}
	}
}
