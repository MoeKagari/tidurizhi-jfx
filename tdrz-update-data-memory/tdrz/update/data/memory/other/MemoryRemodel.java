package tdrz.update.data.memory.other;

import java.util.List;
import java.util.stream.Collectors;

import tdrz.update.data.memory.MemoryOther;
import tdrz.update.data.word.WordSlotItem;

public class MemoryRemodel extends MemoryOther {
	private static final long serialVersionUID = 1L;
	private final long time;
	private final boolean certain, success;
	private final MemoryObjSlotItem oldSlotItem, newSlotItem;
	private final List<MemoryObjSlotItem> consumedSlotItemList;
	private final int[] oldMaterial, newMaterial;

	public MemoryRemodel(
			long time,
			boolean certain, boolean success, WordSlotItem oldSlotItem, WordSlotItem newSlotItem,
			List<WordSlotItem> consumedSlotItemList,
			int[] oldMaterial, int[] newMaterial
	/**/) {
		this.time = time;
		this.certain = certain;
		this.success = success;
		this.oldSlotItem = new MemoryObjSlotItem(oldSlotItem);
		this.newSlotItem = new MemoryObjSlotItem(newSlotItem);
		this.consumedSlotItemList = consumedSlotItemList.stream().map(MemoryObjSlotItem::new).collect(Collectors.toList());
		this.oldMaterial = oldMaterial;
		this.newMaterial = newMaterial;
	}

	@Override
	public long getTime() {
		return this.time;
	}

	public boolean isCertain() {
		return this.certain;
	}

	public boolean isSuccess() {
		return this.success;
	}

	public MemoryObjSlotItem getOldSlotItem() {
		return this.oldSlotItem;
	}

	public MemoryObjSlotItem getNewSlotItem() {
		return this.newSlotItem;
	}

	public List<MemoryObjSlotItem> getConsumedSlotItemList() {
		return this.consumedSlotItemList;
	}

	public int[] getOldMaterial() {
		return this.oldMaterial;
	}

	public int[] getNewMaterial() {
		return this.newMaterial;
	}
}
