package tdrz.update.data.memory.ship;

import org.apache.commons.lang3.ArrayUtils;

import tdrz.update.data.memory.MemoryShip;

public class MemoryShipRemodeling extends MemoryShip {
	private static final long serialVersionUID = 1L;
	private final long time;
	private final MemoryObjShip remodeledShip;
	private final int[] oldMaterial, newMaterial;
	private final int drawingNumber, catapultNumber;//图纸,甲板

	public MemoryShipRemodeling(long time, MemoryObjShip remodeledShip, int[] oldMaterial, int[] newMaterial, int drawingNumber, int catapultNumber) {
		this.time = time;
		this.remodeledShip = remodeledShip;
		this.oldMaterial = ArrayUtils.clone(oldMaterial);
		this.newMaterial = ArrayUtils.clone(newMaterial);
		this.drawingNumber = drawingNumber;
		this.catapultNumber = catapultNumber;
	}

	/** 图纸 */
	public int getDrawingNumber() {
		return this.drawingNumber;
	}

	/** 甲板 */
	public int getCatapultNumber() {
		return this.catapultNumber;
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

	public MemoryObjShip getRemodeledShip() {
		return this.remodeledShip;
	}
}
