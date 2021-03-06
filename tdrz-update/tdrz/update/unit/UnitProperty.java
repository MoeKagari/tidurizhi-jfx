package tdrz.update.unit;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tdrz.update.UnitManager.Unit;
import tdrz.update.data.word.WordShip;
import tdrz.update.data.word.WordSlotItem;
import tdrz.update.data.word.WordUseItem;
import tdrz.update.unit.UnitProperty.UnitHandlerProperty;
import tool.function.FunctionUtils;

public class UnitProperty extends Unit<UnitHandlerProperty> {
	public final Map<Integer, WordUseItem> useItemMap = new HashMap<>();
	public final Map<Integer, WordSlotItem> slotItemMap = new HashMap<>();
	public final Map<Integer, WordShip> shipMap = new HashMap<>();

	@Override
	public void accept(UnitHandlerProperty unitHandler) {
		FunctionUtils.ifRunnable(unitHandler.needClearUseItemList(), this.useItemMap::clear);
		unitHandler.getAddUseItemList().forEach(useItem -> this.useItemMap.put(useItem.getId(), useItem));
		unitHandler.getRemovedUseItemList().forEach(this.useItemMap::remove);

		FunctionUtils.ifRunnable(unitHandler.needClearSlotItemList(), this.slotItemMap::clear);
		unitHandler.getAddSlotItemList().forEach(slotItem -> this.slotItemMap.put(slotItem.getId(), slotItem));
		unitHandler.getRemovedSlotItemList().forEach(this.slotItemMap::remove);
		FunctionUtils.notNull(unitHandler.getSlotItemLock(), lock -> {
			FunctionUtils.notNull(this.slotItemMap.get(lock.api_slotitem_id), slotItem -> {
				slotItem.slotItemLock(lock.api_locked);
			});
		});

		FunctionUtils.ifRunnable(unitHandler.needClearShipList(), this.shipMap::clear);
		unitHandler.getAddShipList().forEach(ship -> this.shipMap.put(ship.getId(), ship));
		unitHandler.getRemovedShipList().forEach(this.shipMap::remove);
		//补给
		unitHandler.getChargeList().forEach(charge -> {
			FunctionUtils.notNull(this.shipMap.get(charge.api_id), ship -> {
				ship.charge(charge.api_fuel, charge.api_bull, charge.api_onslot);
			});
		});
		//开增设装备槽
		FunctionUtils.notNull(unitHandler.getOpenExSlot(), oes -> {
			FunctionUtils.notNull(this.shipMap.get(oes.api_id), ship -> {
				ship.openSlotex();
			});
		});
		//交换装备槽里的装备(自身)
		FunctionUtils.notNull(unitHandler.getSlotExchange(), se -> {
			FunctionUtils.notNull(this.shipMap.get(se.api_id), ship -> {
				ship.slotExchange(se.api_slot);
			});
		});
		//加锁
		FunctionUtils.notNull(unitHandler.getDeckShipLock(), sl -> {
			FunctionUtils.notNull(this.shipMap.get(sl.api_ship_id), ship -> {
				ship.setShipLocked(sl.api_locked);
			});
		});
	}

	public static interface UnitHandlerProperty extends
			UnitHandlerShip, UnitHandlerShipUpdate,
			UnitHandlerSlotItem, UnitHandlerSlotItemUpdate,
			UnitHandlerUseItem {}

	public static interface UnitHandlerShip {
		public default boolean needClearShipList() {
			return false;
		}

		public default List<WordShip> getAddShipList() {
			return FunctionUtils.emptyList();
		}

		public default List<Integer> getRemovedShipList() {
			return FunctionUtils.emptyList();
		}
	}

	public static interface UnitHandlerShipUpdate {
		public default List<Charge> getChargeList() {
			return FunctionUtils.emptyList();
		}

		public default OpenExSlot getOpenExSlot() {
			return null;
		}

		public default SlotExchange getSlotExchange() {
			return null;
		}

		public default DeckShipLock getDeckShipLock() {
			return null;
		}

		public static class Charge {
			public final int api_id, api_fuel, api_bull;
			public final int[] api_onslot;

			public Charge(int api_id, int api_fuel, int api_bull, int[] api_onslot) {
				this.api_id = api_id;
				this.api_fuel = api_fuel;
				this.api_bull = api_bull;
				this.api_onslot = api_onslot;
			}
		}

		public static class OpenExSlot {
			public final int api_id;

			public OpenExSlot(int api_id) {
				this.api_id = api_id;
			}
		}

		public static class SlotExchange {
			public final int api_id;
			public final int[] api_slot;

			public SlotExchange(int api_id, int[] api_slot) {
				this.api_id = api_id;
				this.api_slot = api_slot;
			}
		}

		public static class DeckShipLock {
			public final int api_ship_id;
			public final boolean api_locked;

			public DeckShipLock(int api_ship_id, boolean api_locked) {
				this.api_ship_id = api_ship_id;
				this.api_locked = api_locked;
			}
		}
	}

	public static interface UnitHandlerSlotItem {
		public default boolean needClearSlotItemList() {
			return false;
		}

		public default List<WordSlotItem> getAddSlotItemList() {
			return Collections.emptyList();
		}

		public default List<Integer> getRemovedSlotItemList() {
			return Collections.emptyList();
		}
	}

	public static interface UnitHandlerSlotItemUpdate {
		public default SlotItemLock getSlotItemLock() {
			return null;
		}

		public static class SlotItemLock {
			public final int api_slotitem_id;
			public final boolean api_locked;

			public SlotItemLock(int api_slotitem_id, boolean api_locked) {
				this.api_slotitem_id = api_slotitem_id;
				this.api_locked = api_locked;
			}
		}
	}

	public static interface UnitHandlerUseItem {
		public default boolean needClearUseItemList() {
			return false;
		}

		public default List<WordUseItem> getAddUseItemList() {
			return Collections.emptyList();
		}

		public default List<Integer> getRemovedUseItemList() {
			return Collections.emptyList();
		}
	}
}
