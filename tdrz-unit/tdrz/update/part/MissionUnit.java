package tdrz.update.part;

import tdrz.update.Unit;
import tdrz.update.UnitManager;
import tdrz.update.part.MissionUnit.MissionUnitHandler;

public class MissionUnit extends Unit<MissionUnitHandler> {
	@Override
	public void accept(UnitManager unitManager, MissionUnitHandler unitHandler) {

	}

	public static interface MissionUnitHandler {

	}
}
