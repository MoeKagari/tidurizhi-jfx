package tdrz.unit.update.part;

import tdrz.unit.update.Unit;
import tdrz.unit.update.UnitManager;
import tdrz.unit.update.part.MissionUnit.MissionUnitHandler;

public class MissionUnit extends Unit<MissionUnitHandler> {
	@Override
	public void accept(UnitManager unitManager, MissionUnitHandler unitHandler) {

	}

	public static interface MissionUnitHandler {

	}
}
