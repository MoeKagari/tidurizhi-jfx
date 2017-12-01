package tdrz.update.data.memory.kdock;

import tdrz.update.data.memory.MemoryKdock;

public class MemoryKdockSpeedChange extends MemoryKdock {
	private static final long serialVersionUID = 1L;
	private final long time;
	private final int[] oldMaterial, newMaterial;

	public MemoryKdockSpeedChange(long time, int[] oldMaterial, int[] newMaterial) {
		this.time = time;
		this.oldMaterial = oldMaterial;
		this.newMaterial = newMaterial;
	}

	@Override
	public long getTime() {
		return this.time;
	}

	public int[] getOldMaterial() {
		return this.oldMaterial;
	}

	public int[] getNewMaterial() {
		return this.newMaterial;
	}
}
