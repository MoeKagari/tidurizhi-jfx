package tdrz.update.data.memory.other;

import java.util.List;

import tdrz.update.data.memory.MemoryOther;
import tdrz.update.data.word.WordSlotItem;

public class MemoryRemodel extends MemoryOther {
	private static final long serialVersionUID = 1L;
	private final long time;
	private final boolean certainRemodel, successRemodel;
	private final MemoryObjSlotItem oldSlotItem, newSlotItem;
	private final MemoryObjSlotItem[] consumedSlotItemArray;
	private final int[] oldMaterial, newMaterial;

	public MemoryRemodel(
			long time,
			boolean certain, boolean success, WordSlotItem oldSlotItem, WordSlotItem newSlotItem,
			List<WordSlotItem> consumedSlotItemList,
			int[] oldMaterial, int[] newMaterial
	/**/) {
		this.time = time;
		this.certainRemodel = certain;
		this.successRemodel = success;
		this.oldSlotItem = new MemoryObjSlotItem(oldSlotItem);
		this.newSlotItem = new MemoryObjSlotItem(newSlotItem);
		this.consumedSlotItemArray = consumedSlotItemList.stream().map(MemoryObjSlotItem::new).toArray(MemoryObjSlotItem[]::new);
		this.oldMaterial = oldMaterial;
		this.newMaterial = newMaterial;
	}

	@Override
	public long getTime() {
		return this.time;
	}

	public boolean isCertainRemodel() {
		return this.certainRemodel;
	}

	public boolean isSuccessRemodel() {
		return this.successRemodel;
	}

	public MemoryObjSlotItem getOldSlotItem() {
		return this.oldSlotItem;
	}

	public MemoryObjSlotItem getNewSlotItem() {
		return this.newSlotItem;
	}

	public MemoryObjSlotItem[] getConsumedSlotItemArray() {
		return this.consumedSlotItemArray;
	}

	public int[] getOldMaterial() {
		return this.oldMaterial;
	}

	public int[] getNewMaterial() {
		return this.newMaterial;
	}
}
