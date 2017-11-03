package tdrz.unit.update.part;

import java.util.Optional;

import tdrz.unit.update.Unit;
import tdrz.unit.update.UnitManager;
import tdrz.unit.update.dto.word.PracticeEnemyDto;
import tdrz.unit.update.part.PracticeUnit.PracticeUnitHandler;

public class PracticeUnit extends Unit<PracticeUnitHandler> {
	private PracticeEnemyDto practiceEnemy = null;

	@Override
	public void accept(UnitManager unitManager, PracticeUnitHandler unitHandler) {
		this.practiceEnemy = Optional.ofNullable(unitHandler.getPracticeEnemy()).orElse(this.practiceEnemy);
	}

	public static interface PracticeUnitHandler {
		public default PracticeEnemyDto getPracticeEnemy() {
			return null;
		}
	}
}
