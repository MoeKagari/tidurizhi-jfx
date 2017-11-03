package tdrz.unit.update.part.other;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tdrz.unit.update.Unit;
import tdrz.unit.update.UnitManager;
import tdrz.unit.update.dto.AbstractMemory;
import tdrz.unit.update.part.other.MemoryUnit.MemoryUnitHandler;

public class MemoryUnit extends Unit<MemoryUnitHandler> {
	private final List<AbstractMemory> memorys = new ArrayList<>();

	@Override
	public void accept(UnitManager unitManager, MemoryUnitHandler unitHandler) {
		this.memorys.addAll(unitHandler.getMemory());
	}

	public static interface MemoryUnitHandler {
		public default List<AbstractMemory> getMemory() {
			return Collections.emptyList();
		}
	}
}
