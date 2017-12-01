package tdrz.update.unit;

import java.util.List;

import tdrz.update.UnitManager.Unit;
import tdrz.update.data.word.WordNdock;
import tdrz.update.unit.UnitNdock.UnitHandlerNdock;
import tool.function.FunctionUtils;

public class UnitNdock extends Unit<UnitHandlerNdock> {
	public final NdockHodler[] ndockHodlers = { new NdockHodler(1), new NdockHodler(2), new NdockHodler(3), new NdockHodler(4) };

	@Override
	public void accept(UnitHandlerNdock unitHandler) {
		unitHandler.getNdockList().forEach(ndockUpdate -> {
			FunctionUtils.forEach(this.ndockHodlers, ndockHodler -> {
				if (ndockHodler.id == ndockUpdate.api_id) {
					ndockHodler.ndock = ndockUpdate.ndock;
				}
			});
		});

		FunctionUtils.notNull(unitHandler.getNdockNyukyoSpeedChange(), ndockNyukyoSpeedChange -> {
			FunctionUtils.forEach(this.ndockHodlers, ndockHodler -> {
				if (ndockHodler.id == ndockNyukyoSpeedChange) {
					ndockHodler.ndock = null;
				}
			});
		});
	}

	public static interface UnitHandlerNdock {
		public default List<NdockUpdate> getNdockList() {
			return FunctionUtils.emptyList();
		}

		/** 使用 高速修复 的ndock的id */
		public default Integer getNdockNyukyoSpeedChange() {
			return null;
		}
	}

	public static class NdockHodler {
		private final int id;
		private WordNdock ndock;

		public NdockHodler(int id) {
			this.id = id;
		}

		public WordNdock getNdock() {
			return this.ndock;
		}
	}

	public static class NdockUpdate {
		public final int api_id;
		public final WordNdock ndock;

		public NdockUpdate(int api_id, WordNdock ndock) {
			this.api_id = api_id;
			this.ndock = ndock;
		}
	}
}
