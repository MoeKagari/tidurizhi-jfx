package tdrz.update.unit;

import tdrz.update.UnitManager.Unit;
import tdrz.update.unit.UnitAkashi.UnitHandlerAkashi;
import tool.function.FunctionUtils;

public class UnitAkashi extends Unit<UnitHandlerAkashi> {
	private final static int RESET_LIMIT = 20 * 60 * 1000;
	private long akashiTime = -1;

	public long getAkashiTime() {
		return this.akashiTime;
	}

	@Override
	public void accept(UnitHandlerAkashi unitHandler) {
		FunctionUtils.notNull(unitHandler.getResetAkashiFlagshipWhenChange(), resetAkashiFlagshipWhenChange -> {
			if (resetAkashiFlagshipWhenChange.resetAkashiTimer) {
				this.akashiTime = resetAkashiFlagshipWhenChange.timeWhenChange;
			}
		});

		FunctionUtils.notNull(unitHandler.resetWhenPort(), timeWhenPort -> {
			if (this.akashiTime != -1) {
				if (timeWhenPort - this.akashiTime >= RESET_LIMIT) {
					this.akashiTime = timeWhenPort;
				}
			}
		});
	}

	public static interface UnitHandlerAkashi {
		public default Long resetWhenPort() {
			return null;
		}

		public default ResetAkashiFlagshipWhenChange getResetAkashiFlagshipWhenChange() {
			return null;
		}
	}

	public static class ResetAkashiFlagshipWhenChange {
		public final boolean resetAkashiTimer;
		public final long timeWhenChange;

		public ResetAkashiFlagshipWhenChange(boolean resetAkashiTimer, long timeWhenChange) {
			this.resetAkashiTimer = resetAkashiTimer;
			this.timeWhenChange = timeWhenChange;
		}
	}
}
