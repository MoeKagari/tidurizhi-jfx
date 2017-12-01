package tdrz.update.unit;

import java.util.Optional;

import tdrz.update.UnitManager.Unit;
import tdrz.update.data.word.WordPracticeEnemy;
import tdrz.update.data.word.WordPracticeEnemyInfo;
import tdrz.update.unit.UnitPractice.UnitPracticeHandler;

public class UnitPractice extends Unit<UnitPracticeHandler> {
	private WordPracticeEnemy[] practiceEnemyArray = null;
	private WordPracticeEnemyInfo practiceEnemyInfo = null;

	@Override
	public void accept(UnitPracticeHandler unitHandler) {
		this.practiceEnemyArray = Optional.ofNullable(unitHandler.getPracticeEnemyArray()).orElse(this.practiceEnemyArray);
		this.practiceEnemyInfo = Optional.ofNullable(unitHandler.getPracticeEnemyInfo()).orElse(this.practiceEnemyInfo);
	}

	public WordPracticeEnemyInfo getPracticeEnemyInfo() {
		return this.practiceEnemyInfo;
	}

	public static interface UnitPracticeHandler {
		public default WordPracticeEnemy[] getPracticeEnemyArray() {
			return null;
		}

		public default WordPracticeEnemyInfo getPracticeEnemyInfo() {
			return null;
		}
	}
}
