package tdrz.update.data.memory.ship;

import tdrz.update.data.memory.MemoryShip;

public class MemoryShipNyukyo extends MemoryShip {
	private static final long serialVersionUID = 1L;
	private final long time;
	private final MemoryObjShip nyukyoedShip;

	public MemoryShipNyukyo(long time, MemoryObjShip nyukyoedShip) {
		this.time = time;
		this.nyukyoedShip = nyukyoedShip;
	}

	@Override
	public long getTime() {
		return this.time;
	}

	public MemoryObjShip getNyukyoedShip() {
		return this.nyukyoedShip;
	}
}
