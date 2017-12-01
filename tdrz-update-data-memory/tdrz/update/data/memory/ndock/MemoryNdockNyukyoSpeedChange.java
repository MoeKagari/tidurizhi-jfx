package tdrz.update.data.memory.ndock;

import org.apache.commons.lang3.ArrayUtils;

import tdrz.update.data.memory.MemoryNdock;

public class MemoryNdockNyukyoSpeedChange extends MemoryNdock {
	private static final long serialVersionUID = 1L;
	private final long time;
	private final MemoryObjShip nyukyoedShip;
	private final int[] oldMaterial, newMaterial;

	public MemoryNdockNyukyoSpeedChange(long time, MemoryObjShip nyukyoedShip, int[] oldMaterial, int[] newMaterial) {
		this.time = time;
		this.nyukyoedShip = nyukyoedShip;
		this.oldMaterial = ArrayUtils.clone(oldMaterial);
		this.newMaterial = ArrayUtils.clone(newMaterial);
	}

	public MemoryObjShip getNyukyoedShip() {
		return this.nyukyoedShip;
	}

	public int[] getOldMaterial() {
		return this.oldMaterial;
	}

	public int[] getNewMaterial() {
		return this.newMaterial;
	}

	@Override
	public long getTime() {
		return this.time;
	}
}
