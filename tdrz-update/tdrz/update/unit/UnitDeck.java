package tdrz.update.unit;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.commons.lang3.ArrayUtils;

import tdrz.core.translator.DeckDtoTranslator;
import tdrz.update.UnitManager.Unit;
import tdrz.update.data.word.WordDeck;
import tdrz.update.unit.UnitDeck.UnitHandlerDeck;
import tool.function.FunctionUtils;

public class UnitDeck extends Unit<UnitHandlerDeck> {
	public final DeckHolder[] deckHolders = { new DeckHolder(1), new DeckHolder(2), new DeckHolder(3), new DeckHolder(4) };

	@Override
	public void accept(UnitHandlerDeck unitHandler) {
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
					FunctionUtils.notNull(deckHolder.deck, deck -> {
						this.doDeckChange(deck, deckChange.api_id, deckChange.api_ship_idx, deckChange.api_ship_id);
					});
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
					FunctionUtils.notNull(deckHolder.deck, deck -> {
						deck.setDeckName(deckUpdateDeckName.api_name);
					});
				}
			});
		});
	}

	private void doDeckChange(final WordDeck deck, final int api_id, final int api_ship_idx, final int api_ship_id) {
		if (api_ship_idx == -1) {
			//除旗舰其余全解除
			deck.setShipArray(new int[] { deck.getShipArray()[0], -1, -1, -1, -1, -1 });
		} else {
			if (api_ship_id == -1) {
				//解除某一艘船
				deck.setShipArray(ArrayUtils.addAll(
						IntStream.range(0, deck.getShipArray().length).filter(index -> index != api_ship_idx).map(index -> deck.getShipArray()[index]).toArray(), -1
				/**/));
			} else {
				//替换为另外的ship
				for (int index = 0; index < this.deckHolders.length; index++) {
					int shipIndex = DeckDtoTranslator.indexInDeck(this.deckHolders[index].deck, api_ship_id);
					if (shipIndex != -1) {
						//替换的ship在某deck中
						this.deckHolders[index].deck.getShipArray()[shipIndex] = deck.getShipArray()[api_ship_idx];
						break;
					}
				}
				deck.getShipArray()[api_ship_idx] = api_ship_id;
			}
		}
	}

	public static interface UnitHandlerDeck {
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
		private final int id;
		private WordDeck deck = null;

		public DeckHolder(int id) {
			this.id = id;
		}

		public WordDeck getDeck() {
			return this.deck;
		}
	}

	public static class DeckUpdate {
		public final int api_id;
		public final WordDeck deck;

		public DeckUpdate(int api_id, WordDeck deck) {
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
		public final WordDeck deck;

		public DeckPresetSelect(int api_id, WordDeck deck) {
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
