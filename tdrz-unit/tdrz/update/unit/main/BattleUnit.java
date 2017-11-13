package tdrz.update.unit.main;

import tdrz.update.unit.UnitManager;
import tdrz.update.unit.UnitManager.Unit;
import tdrz.update.unit.main.BattleUnit.BattleUnitHandler;

public class BattleUnit extends Unit<BattleUnitHandler> {
	@Override
	public void accept(UnitManager unitManager, BattleUnitHandler unitHandler) {

	}

	public static interface BattleUnitHandler {

	}
}
