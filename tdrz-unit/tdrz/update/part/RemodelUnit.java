package tdrz.update.part;

import tdrz.update.Unit;
import tdrz.update.UnitManager;
import tdrz.update.part.RemodelUnit.RemodelUnitHandler;

public class RemodelUnit extends Unit<RemodelUnitHandler> {
	@Override
	public void accept(UnitManager unitManager, RemodelUnitHandler unitHandler) {

	}

	public static interface RemodelUnitHandler {

	}
}
