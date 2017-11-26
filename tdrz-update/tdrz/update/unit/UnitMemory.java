package tdrz.update.unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import tdrz.update.UnitManager.Unit;
import tdrz.update.data.AbstractMemory;
import tdrz.update.data.memory.MemoryBattle;
import tdrz.update.data.memory.MemoryMission;
import tdrz.update.data.memory.MemoryOther;
import tdrz.update.data.memory.MemoryShip;
import tdrz.update.data.memory.MemorySlotItem;
import tdrz.update.data.memory.MemoryUseItem;
import tdrz.update.unit.UnitMemory.UnitHandlerMemory;

public class UnitMemory extends Unit<UnitHandlerMemory> {
	public final List<MemoryBattle> memoryBattleList = new ArrayList<>();
	public final List<MemoryMission> memoryMissionList = new ArrayList<>();
	public final List<MemoryOther> memoryOtherList = new ArrayList<>();
	public final List<MemoryShip> memoryShipList = new ArrayList<>();
	public final List<MemorySlotItem> memorySlotItemList = new ArrayList<>();
	public final List<MemoryUseItem> memoryUseItemList = new ArrayList<>();

	@Override
	public void accept(UnitHandlerMemory unitHandler) {
		UnitMemory.addMemory(this.memoryBattleList, unitHandler, UnitHandlerMemory::getMemoryBattleChange);
		UnitMemory.addMemory(this.memoryMissionList, unitHandler, UnitHandlerMemory::getMemoryMissionChange);
		UnitMemory.addMemory(this.memoryOtherList, unitHandler, UnitHandlerMemory::getMemoryOtherChange);
		UnitMemory.addMemory(this.memoryShipList, unitHandler, UnitHandlerMemory::getMemoryShipChange);
		UnitMemory.addMemory(this.memorySlotItemList, unitHandler, UnitHandlerMemory::getMemorySlotItemChange);
		UnitMemory.addMemory(this.memoryUseItemList, unitHandler, UnitHandlerMemory::getMemoryUseItemChange);
	}

	private static <T extends AbstractMemory> void addMemory(List<T> memoryList, UnitHandlerMemory unitHandler, Function<UnitHandlerMemory, MemoryChange<T>> mapper) {
		Optional.ofNullable(mapper.apply(unitHandler)).map(MemoryChange::getMemoryChange).ifPresent(memoryList::add);
	}

	public static interface UnitHandlerMemory {
		public default MemoryChange<MemoryBattle> getMemoryBattleChange() {
			return null;
		}

		public default MemoryChange<MemoryMission> getMemoryMissionChange() {
			return null;
		}

		public default MemoryChange<MemoryOther> getMemoryOtherChange() {
			return null;
		}

		public default MemoryChange<MemoryShip> getMemoryShipChange() {
			return null;
		}

		public default MemoryChange<MemorySlotItem> getMemorySlotItemChange() {
			return null;
		}

		public default MemoryChange<MemoryUseItem> getMemoryUseItemChange() {
			return null;
		}
	}

	public static abstract class MemoryChange<T extends AbstractMemory> {
		/** 单个 */
		public abstract T getMemoryChange();
	}
}
