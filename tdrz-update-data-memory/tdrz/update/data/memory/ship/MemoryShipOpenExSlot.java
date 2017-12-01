package tdrz.update.data.memory.ship;

import tdrz.update.data.memory.MemoryShip;

public class MemoryShipOpenExSlot extends MemoryShip {
	private static final long serialVersionUID = 1L;
	private final long time;
	private final MemoryObjShip ship;

	public MemoryShipOpenExSlot(long time, MemoryObjShip ship) {
		this.time = time;
		this.ship = ship;
	}

	@Override
	public long getTime() {
		return this.time;
	}

	public MemoryObjShip getShip() {
		return this.ship;
	}
}
