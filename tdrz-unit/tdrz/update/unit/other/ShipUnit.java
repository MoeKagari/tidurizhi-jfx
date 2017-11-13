package tdrz.update.unit.other;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tdrz.update.data.word.WordShip;
import tdrz.update.unit.UnitManager;
import tdrz.update.unit.UnitManager.Unit;
import tdrz.update.unit.other.ShipUnit.ShipUnitHandler;
import tool.function.FunctionUtils;

public class ShipUnit extends Unit<ShipUnitHandler> {
	public final Map<Integer, WordShip> shipMap = new HashMap<>();

	@Override
	public void accept(UnitManager unitManager, ShipUnitHandler unitHandler) {
		FunctionUtils.ifRunnable(unitHandler.needClearShipList(), this.shipMap::clear);
		unitHandler.getAddShipList().forEach(ship -> this.shipMap.put(ship.getId(), ship));
		unitHandler.getRemoveShipList().forEach(this.shipMap::remove);

		//补给
		unitHandler.getCharge().forEach(charge -> {
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

	public static interface ShipUnitHandler {
		public default boolean needClearShipList() {
			return false;
		}

		public default List<WordShip> getAddShipList() {
			return Collections.emptyList();
		}

		public default List<Integer> getRemoveShipList() {
			return Collections.emptyList();
		}

		public default List<Charge> getCharge() {
			return Collections.emptyList();
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
		private final int api_id;
		private final int[] api_slot;

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
