package tdrz.update.data;

import java.io.Serializable;

import tdrz.core.translator.MasterDataTranslator;
import tdrz.core.translator.ShipDtoTranslator;
import tdrz.core.translator.SlotItemDtoTranslator;
import tdrz.update.data.word.WordShip;
import tdrz.update.data.word.WordSlotItem;

public abstract class AbstractMemory extends AbstractData implements Serializable {
	private static final long serialVersionUID = 1L;

	public long getTime() {
		return 0;
	}

	@Override
	public abstract String toString();

	public class Ship implements Serializable {
		private static final long serialVersionUID = 1L;
		private final int id, shipId, level;
		private final String name;

		public Ship(WordShip ship) {
			this.id = ship.getId();
			this.shipId = ship.getShipId();
			this.name = ShipDtoTranslator.getName(ship);
			this.level = ship.getLevel();
		}

		public int getShipId() {
			return this.shipId;
		}

		public int getId() {
			return this.id;
		}

		public String getName() {
			return this.name;
		}

		public int getLevel() {
			return this.level;
		}

		public String getTypeString() {
			return ShipDtoTranslator.getTypeString(MasterDataTranslator.getMasterShipDto(this.shipId));
		}
	}

	public class SlotItem implements Serializable {
		private static final long serialVersionUID = 1L;
		private final int id, slotitemId, level, alv;
		private final String name;

		public SlotItem(WordSlotItem item) {
			this.id = item.getId();
			this.slotitemId = item.getSlotitemId();
			this.level = item.getLevel();
			this.alv = item.getAlv();
			this.name = SlotItemDtoTranslator.getName(item);
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

		public String getName() {
			return this.name;
		}

		@Override
		public String toString() {
			return String.format("%s%s%s", this.name, this.alv > 0 ? (" 熟" + this.alv) : "", this.level > 0 ? (" ★" + this.level) : "");
		}
	}
}
