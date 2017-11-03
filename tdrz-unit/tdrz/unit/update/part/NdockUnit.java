package tdrz.unit.update.part;

import java.util.Collections;
import java.util.List;

import tdrz.unit.update.Unit;
import tdrz.unit.update.UnitManager;
import tdrz.unit.update.dto.word.NdockDto;
import tdrz.unit.update.part.NdockUnit.NdockUnitHandler;
import tool.function.FunctionUtils;

public class NdockUnit extends Unit<NdockUnitHandler> {
	public final NdockHodler[] ndockHodlers = { new NdockHodler(1), new NdockHodler(2), new NdockHodler(3), new NdockHodler(4) };

	@Override
	public void accept(UnitManager unitManager, NdockUnitHandler unitHandler) {
		unitHandler.getNdock().forEach(ndockUpdate -> {
			FunctionUtils.forEach(this.ndockHodlers, ndockHodler -> {

			});
		});

		FunctionUtils.notNull(unitHandler.getNdockNyukyoStart(), ndockNyukyoStart -> {
			FunctionUtils.forEach(this.ndockHodlers, ndockHodler -> {

			});
		});

		FunctionUtils.notNull(unitHandler.getNdockNyukyoSpeedChange(), ndockNyukyoSpeedChange -> {
			FunctionUtils.forEach(this.ndockHodlers, ndockHodler -> {
				if (ndockHodler.id == ndockNyukyoSpeedChange.api_ndock_id) {
					ndockHodler.ndock = null;
				}
			});
		});
	}

	public static interface NdockUnitHandler {
		public default List<NdockUpdate> getNdock() {
			return Collections.emptyList();
		}

		public default NdockNyukyoStart getNdockNyukyoStart() {
			return null;
		}

		public default NdockNyukyoSpeedChange getNdockNyukyoSpeedChange() {
			return null;
		}
	}

	public static class NdockHodler {
		private final int id;
		private NdockDto ndock;

		public NdockHodler(int id) {
			this.id = id;
		}

		public NdockDto getNdock() {
			return this.ndock;
		}
	}

	public static class NdockUpdate {
		public final int api_id;
		public final NdockDto ndock;

		public NdockUpdate(int api_id, NdockDto ndock) {
			this.api_id = api_id;
			this.ndock = ndock;
		}
	}

	public static class NdockNyukyoStart {
		public final int api_ndock_id, api_ship_id;
		public final boolean api_highspeed;

		public NdockNyukyoStart(int api_ndock_id, int api_ship_id, boolean api_highspeed) {
			this.api_ndock_id = api_ndock_id;
			this.api_ship_id = api_ship_id;
			this.api_highspeed = api_highspeed;
		}
	}

	public static class NdockNyukyoSpeedChange {
		public final int api_ndock_id;

		public NdockNyukyoSpeedChange(int api_ndock_id) {
			this.api_ndock_id = api_ndock_id;
		}
	}
}
