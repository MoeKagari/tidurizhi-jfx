package tdrz.update.part;

import java.util.Optional;

import tdrz.update.Unit;
import tdrz.update.UnitManager;
import tdrz.update.data.word.WordAirbase;
import tdrz.update.part.AirBaseUnit.AirBaseUnitHandler;

public class AirBaseUnit extends Unit<AirBaseUnitHandler> {
	private WordAirbase airBase = null;

	@Override
	public void accept(UnitManager unitManager, AirBaseUnitHandler unitHandler) {
		this.airBase = Optional.ofNullable(unitHandler.getAirBase()).orElse(this.airBase);
	}

	public static interface AirBaseUnitHandler {
		public default WordAirbase getAirBase() {
			return null;
		}
	}
}
