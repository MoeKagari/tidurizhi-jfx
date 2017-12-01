package tdrz.update.data.memory.kdock;

import tdrz.update.data.memory.MemoryKdock;

public class MemoryKdockGetShip extends MemoryKdock {
	private static final long serialVersionUID = 1L;
	private final long time;
	private final MemoryObjShip createdShip;

	public MemoryKdockGetShip(long time, MemoryObjShip createdShip) {
		this.time = time;
		this.createdShip = createdShip;
	}

	public MemoryObjShip getCreatedShip() {
		return this.createdShip;
	}

	@Override
	public long getTime() {
		return this.time;
	}
}
