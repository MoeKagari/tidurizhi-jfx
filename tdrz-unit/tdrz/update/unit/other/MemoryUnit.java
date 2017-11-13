package tdrz.update.unit.other;

import java.util.ArrayList;
import java.util.List;

import tdrz.update.data.AbstractMemory;
import tdrz.update.unit.UnitManager;
import tdrz.update.unit.UnitManager.Unit;
import tdrz.update.unit.other.MemoryUnit.MemoryUnitHandler;
import tool.function.FunctionUtils;

public class MemoryUnit extends Unit<MemoryUnitHandler> {
	private final List<AbstractMemory> memorys = new ArrayList<>();

	@Override
	public void accept(UnitManager unitManager, MemoryUnitHandler unitHandler) {
		this.memorys.addAll(unitHandler.getMemory());
	}

	public static interface MemoryUnitHandler {
		public default List<? extends AbstractMemory> getMemory() {
			return FunctionUtils.emptyList();
		}
	}
}
