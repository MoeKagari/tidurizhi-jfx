package tdrz.update.unit;

import java.util.Optional;

import tdrz.update.UnitManager.Unit;
import tdrz.update.data.word.WordAirbase;
import tdrz.update.unit.UnitAirBase.UnitHandlerAirBase;

public class UnitAirBase extends Unit<UnitHandlerAirBase> {
	private WordAirbase airBase = null;

	@Override
	public void accept(UnitHandlerAirBase unitHandler) {
		this.airBase = Optional.ofNullable(unitHandler.getAirBase()).orElse(this.airBase);
	}

	public static interface UnitHandlerAirBase {
		public default WordAirbase getAirBase() {
			return null;
		}
	}
}
