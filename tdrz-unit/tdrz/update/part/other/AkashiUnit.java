package tdrz.update.part.other;

import tdrz.core.translator.DeckDtoTranslator;
import tdrz.update.Unit;
import tdrz.update.UnitManager;
import tdrz.update.part.other.AkashiUnit.AkashiUnitHandler;
import tool.function.FunctionUtils;

public class AkashiUnit extends Unit<AkashiUnitHandler> {
	private final static int RESET_LIMIT = 20 * 60 * 1000;
	private long time = -1;

	@Override
	public void accept(UnitManager unitManager, AkashiUnitHandler unitHandler) {
		FunctionUtils.notNull(unitHandler.getResetAkashiFlagshipWhenChange(), resetAkashiFlagshipWhenChange -> {
			int api_id = resetAkashiFlagshipWhenChange.api_id;
			int api_ship_idx = resetAkashiFlagshipWhenChange.api_ship_idx;
			long timeWhenChange = resetAkashiFlagshipWhenChange.timeWhenChange;
			if (api_ship_idx != -1 && DeckDtoTranslator.isAkashiFlagship(unitManager.deckUnit.deckHolders[api_id - 1].getDeck())) {
				//变更之后明石旗舰
				//并且不是[随伴舰一括解除](index == -1)
				this.time = timeWhenChange;
			}
		});

		FunctionUtils.notNull(unitHandler.resetWhenPort(), timeWhenPort -> {
			if (this.time != -1) {
				if (timeWhenPort - this.time >= RESET_LIMIT) {
					this.time = timeWhenPort;
				}
			}
		});
	}

	public static interface AkashiUnitHandler {
		public default Long resetWhenPort() {
			return null;
		}

		public default ResetAkashiFlagshipWhenChange getResetAkashiFlagshipWhenChange() {
			return null;
		}
	}

	public static class ResetAkashiFlagshipWhenChange {
		public final int api_id, api_ship_idx;
		public final long timeWhenChange;

		public ResetAkashiFlagshipWhenChange(int api_id, int api_ship_idx, long timeWhenChange) {
			this.api_id = api_id;
			this.api_ship_idx = api_ship_idx;
			this.timeWhenChange = timeWhenChange;
		}
	}
}
