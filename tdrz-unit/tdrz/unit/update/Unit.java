package tdrz.unit.update;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Unit<T> {
	private final Logger logger = LogManager.getLogger(this.getClass());

	public final Logger getLogger() {
		return this.logger;
	}

	public abstract void accept(UnitManager unitManager, T unitHandler);
}
