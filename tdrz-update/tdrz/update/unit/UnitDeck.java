package tdrz.update.unit;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import tdrz.update.UnitManager.Unit;
import tdrz.update.data.word.WordDeck;
import tdrz.update.unit.UnitDeck.UnitHandlerDeck;
import tool.function.FunctionUtils;

public class UnitDeck extends Unit<UnitHandlerDeck> {
	public final DeckHolder[] deckHolders = { new DeckHolder(1), new DeckHolder(2), new DeckHolder(3), new DeckHolder(4) };

	@Override
	public void accept(UnitHandlerDeck unitHandler) {
		unitHandler.getDeckUpdateList().forEach(deckUpdate -> {
			FunctionUtils.forEach(this.deckHolders, deckHolder -> {
				if (deckUpdate.api_id == deckHolder.id) {
					deckHolder.deck = deckUpdate.deck;
				}
			});
		});

		FunctionUtils.notNull(unitHandler.getDeckChange(), deckChange -> {
			for (int[] deckChangeDetail : new int[][] { deckChange.oldDeckChangeDetail, deckChange.newDeckChangeDetail }) {
				int deckId = deckChangeDetail[0], index = deckChangeDetail[1], shipId = deckChangeDetail[2];
				if (deckId != -1) {
					WordDeck deck = this.deckHolders[deckId - 1].deck;
					if (index == -1) {
						//除旗舰以外全解除
						int[] shipArray = deck.getShipArray();
						int[] newShipArray = new int[shipArray.length];//7船对策
						newShipArray[0] = shipArray[0];
						deck.setShipArray(newShipArray);
					} else {
						//替换
						deck.getShipArray()[index] = shipId;
					}
				}
			}
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

	public static interface UnitHandlerDeck {
		public default List<DeckUpdate> getDeckUpdateList() {
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
		public final int[] oldDeckChangeDetail, newDeckChangeDetail;

		public DeckChange(int[] oldDeckChangeDetail, int[] newDeckChangeDetail) {
			this.oldDeckChangeDetail = ArrayUtils.clone(oldDeckChangeDetail);
			this.newDeckChangeDetail = ArrayUtils.clone(newDeckChangeDetail);
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
