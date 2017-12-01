package tdrz.update.data.memory.ship;

import tdrz.update.data.memory.MemoryShip;

public class MemoryShipMarriage extends MemoryShip {
	private static final long serialVersionUID = 1L;
	private final long time;
	private final MemoryObjShip marriagedShip;

	public MemoryShipMarriage(long time, MemoryObjShip marriagedShip) {
		this.time = time;
		this.marriagedShip = marriagedShip;
	}

	@Override
	public long getTime() {
		return this.time;
	}

	public MemoryObjShip getMarriagedShip() {
		return this.marriagedShip;
	}
}
