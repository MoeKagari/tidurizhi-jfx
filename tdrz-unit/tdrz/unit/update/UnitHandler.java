package tdrz.unit.update;

import java.util.Collections;
import java.util.List;

import tdrz.unit.update.part.AirBaseUnit.AirBaseUnitHandler;
import tdrz.unit.update.part.BattleUnit.BattleUnitHandler;
import tdrz.unit.update.part.DeckUnit.DeckUnitHandler;
import tdrz.unit.update.part.KaisouUnit.KaisouUnitHandler;
import tdrz.unit.update.part.KdockUnit.KdockUnitHandler;
import tdrz.unit.update.part.MissionUnit.MissionUnitHandler;
import tdrz.unit.update.part.NdockUnit.NdockUnitHandler;
import tdrz.unit.update.part.PracticeUnit.PracticeUnitHandler;
import tdrz.unit.update.part.QuestUnit.QuestUnitHandler;
import tdrz.unit.update.part.RemodelUnit.RemodelUnitHandler;
import tdrz.unit.update.part.other.MaterialUnit.MaterialUnitHandler;
import tdrz.unit.update.part.other.MemoryUnit.MemoryUnitHandler;
import tdrz.unit.update.part.other.PrintUnit.PrintUnitHandler;
import tdrz.unit.update.part.other.ShipUnit.ShipUnitHandler;
import tdrz.unit.update.part.other.SlotItemUnit.SlotItemUnitHandler;
import tdrz.unit.update.part.other.UseItemUnit.UseItemUnitHandler;
import tdrz.unit.update.part.other.WordUnit.WordUnitHandler;

public abstract class UnitHandler implements WordUnitHandler, MemoryUnitHandler, QuestUnitHandler, BattleUnitHandler,
		MaterialUnitHandler, PracticeUnitHandler, RemodelUnitHandler, MissionUnitHandler, PrintUnitHandler, AirBaseUnitHandler,
		DeckUnitHandler, KdockUnitHandler, NdockUnitHandler, KaisouUnitHandler,
		ShipUnitHandler, SlotItemUnitHandler, UseItemUnitHandler {

	/**
	 * 此 {@link UnitHandler} 内是否有其它 {@link UnitHandler}<br>
	 * 默认{@link Collections#emptyList()}
	 */
	public List<UnitHandler> otherUnitHandler() {
		return Collections.emptyList();
	}

	/**
	 * 此 {@link UnitHandler} 是否产生了任何一样更新<br>
	 * 默认 true
	 */
	public boolean haveAnyUpdate() {
		return true;
	}

	public static final UnitHandler EMPTY_HANDLER = new UnitHandler() {
		@Override
		public boolean haveAnyUpdate() {
			return false;
		};
	};
}
