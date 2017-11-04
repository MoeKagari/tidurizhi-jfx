package tdrz.update.part.other;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tdrz.update.Unit;
import tdrz.update.UnitManager;
import tdrz.update.data.word.WordSlotItem;
import tdrz.update.part.other.SlotItemUnit.SlotItemUnitHandler;
import tool.function.FunctionUtils;

public class SlotItemUnit extends Unit<SlotItemUnitHandler> {
	public final Map<Integer, WordSlotItem> slotItemMap = new HashMap<>();

	@Override
	public void accept(UnitManager unitManager, SlotItemUnitHandler unitHandler) {
		FunctionUtils.ifRunnable(unitHandler.needClearSlotItemList(), this.slotItemMap::clear);
		unitHandler.getAddSlotItemList().forEach(slotItem -> this.slotItemMap.put(slotItem.getId(), slotItem));
		unitHandler.getRemoveSlotItemList().forEach(this.slotItemMap::remove);

		FunctionUtils.notNull(unitHandler.getSlotItemLock(), lock -> {
			FunctionUtils.notNull(this.slotItemMap.get(lock.api_slotitem_id), slotItem -> {
				slotItem.slotItemLock(lock.api_locked);
			});
		});
	}

	public static interface SlotItemUnitHandler {
		public default boolean needClearSlotItemList() {
			return false;
		}

		public default List<WordSlotItem> getAddSlotItemList() {
			return Collections.emptyList();
		}

		public default List<Integer> getRemoveSlotItemList() {
			return Collections.emptyList();
		}

		public default SlotItemLock getSlotItemLock() {
			return null;
		}
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
