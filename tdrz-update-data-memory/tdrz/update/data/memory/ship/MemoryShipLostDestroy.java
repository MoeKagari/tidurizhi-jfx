package tdrz.update.data.memory.ship;

import org.apache.commons.lang3.ArrayUtils;

public class MemoryShipLostDestroy extends MemoryShipLost {
	private static final long serialVersionUID = 1L;
	private final long time;
	private final MemoryObjShip[] destroyedShipArray;
	private final int[] oldMaterial, newMaterial;

	public MemoryShipLostDestroy(long time, MemoryObjShip[] removedShipArray, int[] oldMaterial, int[] newMaterial) {
		this.time = time;
		this.destroyedShipArray = removedShipArray;
		this.oldMaterial = ArrayUtils.clone(oldMaterial);
		this.newMaterial = ArrayUtils.clone(newMaterial);
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

	public MemoryObjShip[] getDestroyedShipArray() {
		return this.destroyedShipArray;
	}
}
