package tdrz.update.data;

import java.io.Serializable;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

import tdrz.update.data.word.WordDeck;
import tdrz.update.data.word.WordShip;
import tdrz.update.data.word.WordSlotItem;
import tool.function.FunctionUtils;

public abstract class AbstractMemory extends AbstractData implements Serializable {
	private static final long serialVersionUID = 1L;

	public long getTime() {
		return -1;
	}

	@Override
	public String toString() {
		return "";
	}

	public static class MemoryObj implements Serializable {
		private static final long serialVersionUID = 1L;
	}

	public static class MemoryObjDeck extends MemoryObj {
		private static final long serialVersionUID = 1L;
		private final int deckId;
		private final String deckName;
		private final MemoryObjShip[] shipArray;

		public MemoryObjDeck(int deckId, WordDeck deck, IntFunction<WordShip> shipMapper, IntFunction<WordSlotItem> slotItemMapper) {
			this.deckId = deckId;
			this.deckName = deck.getDeckName();
			this.shipArray = IntStream.of(deck.getShipArray())
					.mapToObj(shipMapper)
					.filter(FunctionUtils::isNotNull)
					.map(wordShip -> new MemoryObjShip(wordShip, slotItemMapper))
					.toArray(MemoryObjShip[]::new);
		}

		public int getDeckId() {
			return this.deckId;
		}

		public String getDeckName() {
			return this.deckName;
		}

		public MemoryObjShip[] getShipArray() {
			return this.shipArray;
		}
	}

	public static class MemoryObjShip extends MemoryObj {
		private static final long serialVersionUID = 1L;
		private final int id, shipId, level;
		private final MemoryObjSlotItem[] slotItemArray;

		public MemoryObjShip(WordShip ship, IntFunction<WordSlotItem> slotItemMapper) {
			this.id = ship.getId();
			this.shipId = ship.getShipId();
			this.level = ship.getLevel();
			this.slotItemArray = IntStream.of(ship.getSlots())
					.mapToObj(slotItemMapper)
					.filter(FunctionUtils::isNotNull)
					.map(word -> new MemoryObjSlotItem(word))
					.toArray(MemoryObjSlotItem[]::new);
		}

		public int getShipId() {
			return this.shipId;
		}

		public int getId() {
			return this.id;
		}

		public int getLevel() {
			return this.level;
		}

		public MemoryObjSlotItem[] getSlotItemArray() {
			return this.slotItemArray;
		}
	}

	public static class MemoryObjSlotItem extends MemoryObj {
		private static final long serialVersionUID = 1L;
		private final int id, slotitemId, level, alv;

		public MemoryObjSlotItem(WordSlotItem item) {
			this.id = item.getId();
			this.slotitemId = item.getSlotitemId();
			this.level = item.getLevel();
			this.alv = item.getAlv();
		}

		public int getId() {
			return this.id;
		}

		public int getSlotitemId() {
			return this.slotitemId;
		}

		public int getLevel() {
			return this.level;
		}

		public int getAlv() {
			return this.alv;
		}
	}
}
