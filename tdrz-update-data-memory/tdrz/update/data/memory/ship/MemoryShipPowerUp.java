package tdrz.update.data.memory.ship;

import java.io.Serializable;

import tdrz.update.data.memory.MemoryShip;
import tdrz.update.data.word.WordShip;

public class MemoryShipPowerUp extends MemoryShip {
	private static final long serialVersionUID = 1L;
	private final long time;
	private final ShipPowerUpShip oldship, newShip;
	private final boolean success;
	private final MemoryObjShip[] consumedShipArray;

	public MemoryShipPowerUp(long time, ShipPowerUpShip oldship, ShipPowerUpShip newShip, boolean success, MemoryObjShip[] consumedShipArray) {
		this.time = time;
		this.oldship = oldship;
		this.newShip = newShip;
		this.success = success;
		this.consumedShipArray = consumedShipArray;
	}

	public ShipPowerUpShip getOldship() {
		return this.oldship;
	}

	public ShipPowerUpShip getNewShip() {
		return this.newShip;
	}

	public boolean isSuccess() {
		return this.success;
	}

	public MemoryObjShip[] getConsumedShipArray() {
		return this.consumedShipArray;
	}

	@Override
	public long getTime() {
		return this.time;
	}

	public static class ShipPowerUpShip implements Serializable {
		private static final long serialVersionUID = 1L;
		private final int karyoku, raisou, taiku, soukou;
		private final int taisen, luck;

		public ShipPowerUpShip(WordShip ship) {
			this.karyoku = ship.getKaryoku()[0];
			this.raisou = ship.getRaisou()[0];
			this.taiku = ship.getTaiku()[0];
			this.soukou = ship.getSoukou()[0];
			this.taisen = ship.getTaisen()[0];
			this.luck = ship.getLuck()[0];
		}

		public int getKaryoku() {
			return this.karyoku;
		}

		public int getRaisou() {
			return this.raisou;
		}

		public int getTaiku() {
			return this.taiku;
		}

		public int getSoukou() {
			return this.soukou;
		}

		public int getTaisen() {
			return this.taisen;
		}

		public int getLuck() {
			return this.luck;
		}
	}
}
