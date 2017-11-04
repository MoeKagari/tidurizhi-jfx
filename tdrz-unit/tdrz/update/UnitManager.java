package tdrz.update;

import tdrz.update.data.word.WordShip;
import tdrz.update.data.word.WordSlotItem;
import tdrz.update.part.AirBaseUnit;
import tdrz.update.part.BattleUnit;
import tdrz.update.part.DeckUnit;
import tdrz.update.part.KaisouUnit;
import tdrz.update.part.KdockUnit;
import tdrz.update.part.MissionUnit;
import tdrz.update.part.NdockUnit;
import tdrz.update.part.PracticeUnit;
import tdrz.update.part.QuestUnit;
import tdrz.update.part.RemodelUnit;
import tdrz.update.part.other.AkashiUnit;
import tdrz.update.part.other.MaterialUnit;
import tdrz.update.part.other.MemoryUnit;
import tdrz.update.part.other.PrintUnit;
import tdrz.update.part.other.ShipUnit;
import tdrz.update.part.other.SlotItemUnit;
import tdrz.update.part.other.UseItemUnit;
import tdrz.update.part.other.WordUnit;

public class UnitManager {
	private final static UnitManager INSTANCE = new UnitManager();

	private UnitManager() {}

	/** 多用户支持 */
	public static UnitManager getUnitManager() {
		return INSTANCE;
	}

	/* -------------------------------------------------------------------------------------------------------- */

	public WordShip getShip(int id) {
		return this.shipUnit.shipMap.get(id);
	}

	public WordSlotItem getSlotItem(int id) {
		return this.slotItemUnit.slotItemMap.get(id);
	}

	public void acceptUnitHandler(UnitHandler handler) {
		if (handler.haveAnyUpdate()) {//对一些无需处理的 api ,直接滤过
			this.doUnitHandler(handler);
		}
	}

	private void doUnitHandler(UnitHandler handler) {
		this.shipUnit.accept(this, handler);
		this.slotItemUnit.accept(this, handler);
		this.useItemUnit.accept(this, handler);
		this.wordUnit.accept(this, handler);

		this.questUnit.accept(this, handler);
		this.practiceUnit.accept(this, handler);
		this.remodelUnit.accept(this, handler);
		this.airBaseUnit.accept(this, handler);
		this.deckUnit.accept(this, handler);
		this.kdockUnit.accept(this, handler);
		this.ndockUnit.accept(this, handler);
		this.kaisouUnit.accept(this, handler);
		this.missionUnit.accept(this, handler);
		this.battleUnit.accept(this, handler);

		this.materialUnit.accept(this, handler);
		this.memoryUnit.accept(this, handler);
		this.akashiUnit.accept(this, handler);
		this.printUnit.accept(this, handler);

		handler.otherUnitHandler().forEach(this::doUnitHandler);
	}

	public final QuestUnit questUnit = new QuestUnit();
	public final BattleUnit battleUnit = new BattleUnit();
	public final PracticeUnit practiceUnit = new PracticeUnit();
	public final RemodelUnit remodelUnit = new RemodelUnit();
	public final AirBaseUnit airBaseUnit = new AirBaseUnit();
	public final DeckUnit deckUnit = new DeckUnit();
	public final KdockUnit kdockUnit = new KdockUnit();
	public final NdockUnit ndockUnit = new NdockUnit();
	public final KaisouUnit kaisouUnit = new KaisouUnit();
	public final MissionUnit missionUnit = new MissionUnit();

	public final ShipUnit shipUnit = new ShipUnit();
	public final SlotItemUnit slotItemUnit = new SlotItemUnit();
	public final UseItemUnit useItemUnit = new UseItemUnit();
	public final WordUnit wordUnit = new WordUnit();
	public final MaterialUnit materialUnit = new MaterialUnit();
	public final MemoryUnit memoryUnit = new MemoryUnit();
	public final AkashiUnit akashiUnit = new AkashiUnit();
	public final PrintUnit printUnit = new PrintUnit();
}
