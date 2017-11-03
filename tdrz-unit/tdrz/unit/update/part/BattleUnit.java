package tdrz.unit.update.part;

import tdrz.unit.update.Unit;
import tdrz.unit.update.UnitManager;
import tdrz.unit.update.part.BattleUnit.BattleUnitHandler;

public class BattleUnit extends Unit<BattleUnitHandler> {
	@Override
	public void accept(UnitManager unitManager, BattleUnitHandler unitHandler) {

	}

	public static interface BattleUnitHandler {

	}
}
