package tdrz.update.unit.main;

import tdrz.update.unit.UnitManager;
import tdrz.update.unit.UnitManager.Unit;
import tdrz.update.unit.main.MissionUnit.MissionUnitHandler;

public class MissionUnit extends Unit<MissionUnitHandler> {
	@Override
	public void accept(UnitManager unitManager, MissionUnitHandler unitHandler) {

	}

	public static interface MissionUnitHandler {

	}
}
