package tdrz.update.data.memory.ship;

import tdrz.update.data.memory.MemoryShip;

public class MemoryShipLevelUP extends MemoryShip {
	private static final long serialVersionUID = 1L;
	private final long time;
	private final MemoryObjShip ship;
	private final int oldLevel, newLevel;

	public MemoryShipLevelUP(long time, MemoryObjShip ship, int oldLevel, int newLevel) {
		this.time = time;
		this.ship = ship;
		this.oldLevel = oldLevel;
		this.newLevel = newLevel;
	}

	public MemoryObjShip getShip() {
		return this.ship;
	}

	@Override
	public long getTime() {
		return this.time;
	}

	public int getOldLevel() {
		return this.oldLevel;
	}

	public int getNewLevel() {
		return this.newLevel;
	}
}
