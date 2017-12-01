package tdrz.update.data.memory.other;

import org.apache.commons.lang3.ArrayUtils;

import tdrz.update.data.memory.MemoryOther;

public class MemoryMaterial extends MemoryOther {
	private static final long serialVersionUID = 1L;
	private final long time;
	private final String event;
	private final int[] oldMaterial, newMaterial;

	public MemoryMaterial(long time, String event, int[] oldMaterial, int[] newMaterial) {
		this.time = time;
		this.event = event;
		this.oldMaterial = ArrayUtils.clone(oldMaterial);
		this.newMaterial = ArrayUtils.clone(newMaterial);
	}

	@Override
	public long getTime() {
		return this.time;
	}

	public String getEvent() {
		return this.event;
	}

	public int[] getOldMaterial() {
		return this.oldMaterial;
	}

	public int[] getNewMaterial() {
		return this.newMaterial;
	}
}
