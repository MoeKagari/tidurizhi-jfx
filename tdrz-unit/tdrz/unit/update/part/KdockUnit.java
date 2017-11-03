package tdrz.unit.update.part;

import java.util.Collections;
import java.util.List;

import tdrz.unit.update.Unit;
import tdrz.unit.update.UnitManager;
import tdrz.unit.update.dto.word.KdockDto;
import tdrz.unit.update.part.KdockUnit.KdockUnitHandler;
import tool.function.FunctionUtils;

public class KdockUnit extends Unit<KdockUnitHandler> {
	private final KdockHolder[] kdockHolders = { new KdockHolder(1), new KdockHolder(2), new KdockHolder(3), new KdockHolder(4) };

	@Override
	public void accept(UnitManager unitManager, KdockUnitHandler unitHandler) {
		unitHandler.getKdockUpdate().forEach(kdockUpdate -> {
			FunctionUtils.forEach(this.kdockHolders, kdockHolder -> {
				if (kdockHolder.id == kdockUpdate.api_id) {
					kdockHolder.kdock = kdockUpdate.kdock;
				}
			});
		});
	}

	public static interface KdockUnitHandler {
		public default List<KdockUpdate> getKdockUpdate() {
			return Collections.emptyList();
		}
	}

	public static class KdockHolder {
		private final int id;
		private KdockDto kdock;

		public KdockHolder(int id) {
			this.id = id;
		}

		public KdockDto getKdock() {
			return this.kdock;
		}
	}

	public static class KdockUpdate {
		public final int api_id;
		public final KdockDto kdock;

		public KdockUpdate(int api_id, KdockDto kdock) {
			this.api_id = api_id;
			this.kdock = kdock;
		}
	}
}
