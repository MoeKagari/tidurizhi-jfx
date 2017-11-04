package tdrz.update.part.other;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tdrz.update.Unit;
import tdrz.update.UnitManager;
import tdrz.update.data.AbstractMemory;
import tdrz.update.part.other.MemoryUnit.MemoryUnitHandler;

public class MemoryUnit extends Unit<MemoryUnitHandler> {
	private final List<AbstractMemory> memorys = new ArrayList<>();

	@Override
	public void accept(UnitManager unitManager, MemoryUnitHandler unitHandler) {
		this.memorys.addAll(unitHandler.getMemory());
	}

	public static interface MemoryUnitHandler {
		public default List<? extends AbstractMemory> getMemory() {
			return Collections.emptyList();
		}
	}
}
