package tdrz.update.data.memory.other;

import org.apache.commons.lang3.ArrayUtils;

import tdrz.update.data.memory.MemoryOther;

public class MemoryCharge extends MemoryOther {
	private static final long serialVersionUID = 1L;
	private final long time;
	private final int[] oldMaterial, newMaterial;

	public MemoryCharge(long time, int[] oldMaterial, int[] newMaterial) {
		this.time = time;
		this.oldMaterial = ArrayUtils.clone(oldMaterial);
		this.newMaterial = ArrayUtils.clone(newMaterial);
	}

	@Override
	public long getTime() {
		return this.time;
	}

	public int[] getOldMaterial() {
		return oldMaterial;
	}

	public int[] getNewMaterial() {
		return newMaterial;
	}
}
