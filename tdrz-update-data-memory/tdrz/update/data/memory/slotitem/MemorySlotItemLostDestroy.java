package tdrz.update.data.memory.slotitem;

import org.apache.commons.lang3.ArrayUtils;

public class MemorySlotItemLostDestroy extends MemorySlotItemLost {
	private static final long serialVersionUID = 1L;
	private final long time;
	private final MemoryObjSlotItem[] destroyedSlotItemArray;
	private final int[] oldMaterial, newMaterial;

	public MemorySlotItemLostDestroy(long time, MemoryObjSlotItem[] destroyedSlotItemArray, int[] oldMaterial, int[] newMaterial) {
		this.time = time;
		this.destroyedSlotItemArray = destroyedSlotItemArray;
		this.oldMaterial = ArrayUtils.clone(oldMaterial);
		this.newMaterial = ArrayUtils.clone(newMaterial);
	}

	@Override
	public long getTime() {
		return this.time;
	}

	public MemoryObjSlotItem[] getDestroyedSlotItemArray() {
		return this.destroyedSlotItemArray;
	}

	public int[] getOldMaterial() {
		return this.oldMaterial;
	}

	public int[] getNewMaterial() {
		return this.newMaterial;
	}
}
