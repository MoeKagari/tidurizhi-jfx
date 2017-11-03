package tdrz.unit.update.part;

import java.util.Optional;

import tdrz.unit.update.Unit;
import tdrz.unit.update.UnitManager;
import tdrz.unit.update.dto.word.AirbaseDto;
import tdrz.unit.update.part.AirBaseUnit.AirBaseUnitHandler;

public class AirBaseUnit extends Unit<AirBaseUnitHandler> {
	private AirbaseDto airBase = null;

	@Override
	public void accept(UnitManager unitManager, AirBaseUnitHandler unitHandler) {
		this.airBase = Optional.ofNullable(unitHandler.getAirBase()).orElse(this.airBase);
	}

	public static interface AirBaseUnitHandler {
		public default AirbaseDto getAirBase() {
			return null;
		}
	}
}
