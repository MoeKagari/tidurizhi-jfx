package tdrz.update.part;

import tdrz.update.Unit;
import tdrz.update.UnitManager;
import tdrz.update.part.BattleUnit.BattleUnitHandler;

public class BattleUnit extends Unit<BattleUnitHandler> {
	@Override
	public void accept(UnitManager unitManager, BattleUnitHandler unitHandler) {

	}

	public static interface BattleUnitHandler {

	}
}
