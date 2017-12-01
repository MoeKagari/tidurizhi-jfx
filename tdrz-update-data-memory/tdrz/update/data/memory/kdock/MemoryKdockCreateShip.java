package tdrz.update.data.memory.kdock;

import org.apache.commons.lang3.ArrayUtils;

import tdrz.update.data.memory.MemoryKdock;

public class MemoryKdockCreateShip extends MemoryKdock {
	private static final long serialVersionUID = 1L;
	private final long time;
	private int createdShipId;
	private final int[] oldMaterial, newMaterial;
	private final MemoryObjShip secretaryShip;
	private final boolean largeCreate, highSpeedCreate;

	public MemoryKdockCreateShip(long time, int[] oldMaterial, int[] newMaterial, MemoryObjShip secretaryShip, boolean largeCreate, boolean highSpeedCreate) {
		this.time = time;
		this.oldMaterial = ArrayUtils.clone(oldMaterial);
		this.newMaterial = ArrayUtils.clone(newMaterial);
		this.secretaryShip = secretaryShip;
		this.largeCreate = largeCreate;
		this.highSpeedCreate = highSpeedCreate;
	}

	public void setCreatedShipId(int createdShipId) {
		this.createdShipId = createdShipId;
	}

	public int getCreatedShipId() {
		return this.createdShipId;
	}

	public MemoryObjShip getSecretaryShip() {
		return this.secretaryShip;
	}

	public boolean isHighSpeedCreate() {
		return this.highSpeedCreate;
	}

	public boolean isLargeCreate() {
		return this.largeCreate;
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
