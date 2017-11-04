package tdrz.update.data.memory.other;

import tdrz.update.data.AbstractMemory;
import tdrz.update.data.word.WordSlotItem;
import tool.function.FunctionUtils;

public class MemoryRemodel extends AbstractMemory {
	private static final long serialVersionUID = 1L;
	private final long time;
	private final int slotId;
	private final boolean certain, success;
	private final SlotItem slotItem, newSlotItem;

	public MemoryRemodel(long time, int slotId, boolean certain, boolean success, WordSlotItem item, WordSlotItem newItem) {
		this.time = time;
		this.slotId = slotId;
		this.certain = certain;
		this.success = success;
		this.slotItem = new SlotItem(item);
		this.newSlotItem = FunctionUtils.notNull(newItem, SlotItem::new, null);
	}

	public int getSlotId() {
		return this.slotId;
	}

	public boolean isCertain() {
		return this.certain;
	}

	public boolean isSuccess() {
		return this.success;
	}

	public boolean isUpdate() {
		return this.newSlotItem != null && this.newSlotItem.getSlotitemId() != this.slotItem.getSlotitemId();
	}

	@Override
	public long getTime() {
		return this.time;
	}

	public SlotItem getItem() {
		return this.slotItem;
	}

	public SlotItem getNewItem() {
		return this.newSlotItem;
	}

	@Override
	public String toString() {
		return "";
	}
}
