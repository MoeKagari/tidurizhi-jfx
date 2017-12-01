package tdrz.update.unit;

import java.util.List;

import tdrz.update.UnitManager.Unit;
import tdrz.update.data.memory.kdock.MemoryKdockCreateShip;
import tdrz.update.data.word.WordKdock;
import tdrz.update.unit.UnitKdock.UnitHandlerKdock;
import tool.function.FunctionUtils;

public class UnitKdock extends Unit<UnitHandlerKdock> {
	public final KdockHolder[] kdockHolders = { new KdockHolder(1), new KdockHolder(2), new KdockHolder(3), new KdockHolder(4) };

	@Override
	public void accept(UnitHandlerKdock unitHandler) {
		unitHandler.getKdockUpdateList().forEach(kdockUpdate -> {
			FunctionUtils.forEach(this.kdockHolders, kdockHolder -> {
				if (kdockHolder.id == kdockUpdate.api_id) {
					kdockHolder.kdock = kdockUpdate.kdock;
				}
			});
		});

		FunctionUtils.notNull(unitHandler.getKdockTempMemoryKdockCreateShip(), temp -> {
			FunctionUtils.forEach(this.kdockHolders, kdockHolder -> {
				if (kdockHolder.id == temp.api_kdock_id) {
					kdockHolder.memoryKdockCreateShip = temp.memoryKdockCreateShip;
				}
			});
		});
	}

	public static interface UnitHandlerKdock {
		public default List<KdockUpdate> getKdockUpdateList() {
			return FunctionUtils.emptyList();
		}

		public default KdockTemp_MemoryKdockCreateShip getKdockTempMemoryKdockCreateShip() {
			return null;
		}
	}

	public static class KdockHolder {
		private final int id;
		private WordKdock kdock = null;
		private MemoryKdockCreateShip memoryKdockCreateShip = null;

		public KdockHolder(int id) {
			this.id = id;
		}

		public WordKdock getKdock() {
			return this.kdock;
		}

		public MemoryKdockCreateShip getMemoryKdockCreateShip() {
			return this.memoryKdockCreateShip;
		}
	}

	public static class KdockUpdate {
		public final int api_id;
		public final WordKdock kdock;

		public KdockUpdate(int api_id, WordKdock kdock) {
			this.api_id = api_id;
			this.kdock = kdock;
		}
	}

	public static class KdockTemp_MemoryKdockCreateShip {
		public final int api_kdock_id;
		public final MemoryKdockCreateShip memoryKdockCreateShip;

		public KdockTemp_MemoryKdockCreateShip(int api_kdock_id, MemoryKdockCreateShip memoryKdockCreateShip) {
			this.api_kdock_id = api_kdock_id;
			this.memoryKdockCreateShip = memoryKdockCreateShip;
		}
	}
}
