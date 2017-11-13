package tdrz.update.unit.main;

import java.util.Optional;

import tdrz.update.data.word.WordPracticeEnemy;
import tdrz.update.unit.UnitManager;
import tdrz.update.unit.UnitManager.Unit;
import tdrz.update.unit.main.PracticeUnit.PracticeUnitHandler;

public class PracticeUnit extends Unit<PracticeUnitHandler> {
	private WordPracticeEnemy practiceEnemy = null;

	@Override
	public void accept(UnitManager unitManager, PracticeUnitHandler unitHandler) {
		this.practiceEnemy = Optional.ofNullable(unitHandler.getPracticeEnemy()).orElse(this.practiceEnemy);
	}

	public static interface PracticeUnitHandler {
		public default WordPracticeEnemy getPracticeEnemy() {
			return null;
		}
	}
}
