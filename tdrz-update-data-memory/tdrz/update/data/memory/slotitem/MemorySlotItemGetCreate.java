package tdrz.update.data.memory.slotitem;

import org.apache.commons.lang3.ArrayUtils;

public class MemorySlotItemGetCreate extends MemorySlotItemGet {
	private static final long serialVersionUID = 1L;
	private final long time;
	private final MemoryObjSlotItem createdSlotItem;
	private final int[] oldMaterial, newMaterial;

	public MemorySlotItemGetCreate(long time, MemoryObjSlotItem slotItem, int[] oldMaterial, int[] newMaterial) {
		this.time = time;
		this.createdSlotItem = slotItem;
		this.oldMaterial = ArrayUtils.clone(oldMaterial);
		this.newMaterial = ArrayUtils.clone(newMaterial);
	}

	@Override
	public long getTime() {
		return this.time;
	}

	public MemoryObjSlotItem getCreatedSlotItem() {
		return this.createdSlotItem;
	}

	public int[] getOldMaterial() {
		return this.oldMaterial;
	}

	public int[] getNewMaterial() {
		return this.newMaterial;
	}
}
