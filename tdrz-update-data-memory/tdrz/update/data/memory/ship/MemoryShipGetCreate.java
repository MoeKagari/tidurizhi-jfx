package tdrz.update.data.memory.ship;

public class MemoryShipGetCreate extends MemoryShipGet {
	private static final long serialVersionUID = 1L;
	private final long time;
	private final MemoryObjShip createdShip;

	public MemoryShipGetCreate(long time, MemoryObjShip createdShip) {
		this.time = time;
		this.createdShip = createdShip;
	}

	@Override
	public long getTime() {
		return this.time;
	}

	public MemoryObjShip getCreatedShip() {
		return this.createdShip;
	}
}
