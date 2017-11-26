package tdrz.gui.main.part;

import java.util.stream.Collectors;

import javafx.scene.control.TableView;
import tdrz.core.util.ExpUtils.Eval;
import tdrz.core.util.ExpUtils.FlagshipMVP;
import tdrz.core.util.ExpUtils.ShipExp;
import tdrz.gui.main.ApplicationMainPart;
import tdrz.gui.main.ApplicationMainTDRZJFX;
import tdrz.server.RawApiDataType;
import tdrz.update.data.word.WordPracticeEnemy;
import tdrz.update.data.word.WordPracticeEnemy.PracticeEnemyShip;
import tool.FXUtils;
import tool.function.FunctionUtils;

public class PartPractice extends TableView<PartPractice.CalcuPracticeExpData> implements ApplicationMainPart {
	public PartPractice(ApplicationMainTDRZJFX main) {
		FXUtils.addNewColumn(this, "", FXUtils.getStringTableCellFactory(), rd -> rd.fm.name);
		FunctionUtils.stream(Eval.values()).forEach(eval -> {
			FXUtils.addNewColumn(this, eval.name, FXUtils.getIntegerTableCellFactory(), rd -> rd.calcu(eval));
		});

		main.getUnitManager().addListener(type -> {
			if (type == RawApiDataType.PRACTICE_ENEMYINFO) {
				PracticeEnemyShip[] ships = FunctionUtils.notNull(main.getUnitManager().getPracticeEnemy(), WordPracticeEnemy::getShips, null);
				if (ships != null) {
					int lv0 = FunctionUtils.ifFunction(ships[0], FunctionUtils::isNotNull, ship -> ship.getLv(), 1);
					int lv1 = FunctionUtils.ifFunction(ships[1], FunctionUtils::isNotNull, ship -> ship.getLv(), 1);
					this.getItems().setAll(
							FunctionUtils.stream(FlagshipMVP.values())
									.map(fm -> new CalcuPracticeExpData(lv0, lv1, fm))
									.collect(Collectors.toList())
					/**/);
				}
			}
		});
	}

	class CalcuPracticeExpData {
		private final int exp0;
		private final int exp1;
		private final FlagshipMVP fm;

		private CalcuPracticeExpData(int lv0, int lv1, FlagshipMVP fm) {
			this.exp0 = ShipExp.EXPMAP.get(lv0);
			this.exp1 = ShipExp.EXPMAP.get(lv1);
			this.fm = fm;
		}

		private int calcu(Eval eval) {
			double exp = Math.floor(this.exp0 / 100.0 + this.exp1 / 300.0);

			if (exp > 500) exp = Math.floor(500 + Math.sqrt(exp - 500));
			exp = Math.floor(exp * eval.valueP);
			if (this.fm.isFlagship) exp *= 1.5;
			if (this.fm.isMVP) exp *= 2;

			return (int) Math.floor(exp);
		}
	}
}
