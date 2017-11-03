package tdrz.unit.update;

import tdrz.unit.update.part.AirBaseUnit;
import tdrz.unit.update.part.BattleUnit;
import tdrz.unit.update.part.DeckUnit;
import tdrz.unit.update.part.KaisouUnit;
import tdrz.unit.update.part.KdockUnit;
import tdrz.unit.update.part.MissionUnit;
import tdrz.unit.update.part.NdockUnit;
import tdrz.unit.update.part.PracticeUnit;
import tdrz.unit.update.part.QuestUnit;
import tdrz.unit.update.part.RemodelUnit;
import tdrz.unit.update.part.other.MaterialUnit;
import tdrz.unit.update.part.other.MemoryUnit;
import tdrz.unit.update.part.other.PrintUnit;
import tdrz.unit.update.part.other.PrintUnit.Printer;
import tdrz.unit.update.part.other.ShipUnit;
import tdrz.unit.update.part.other.SlotItemUnit;
import tdrz.unit.update.part.other.UseItemUnit;
import tdrz.unit.update.part.other.WordUnit;

public class UnitManager {
	private final static UnitManager INSTANCE = new UnitManager();

	private UnitManager() {}

	public static UnitManager get() {
		return INSTANCE;
	}

	/* -------------------------------------------------------------------------------------------------------- */

	public void addPrinter(Printer printer) {
		this.printUnit.addPrinter(printer);
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
	public final PrintUnit printUnit = new PrintUnit();

	public void acceptUnitHandler(UnitHandler handler) {
		this.shipUnit.accept(this, handler);
		this.slotItemUnit.accept(this, handler);
		this.useItemUnit.accept(this, handler);
		this.wordUnit.accept(this, handler);

		this.questUnit.accept(this, handler);
		this.battleUnit.accept(this, handler);
		this.practiceUnit.accept(this, handler);
		this.remodelUnit.accept(this, handler);
		this.airBaseUnit.accept(this, handler);
		this.deckUnit.accept(this, handler);
		this.kdockUnit.accept(this, handler);
		this.ndockUnit.accept(this, handler);
		this.kaisouUnit.accept(this, handler);
		this.missionUnit.accept(this, handler);

		this.materialUnit.accept(this, handler);
		this.memoryUnit.accept(this, handler);
		this.printUnit.accept(this, handler);

		handler.otherUnitHandler().forEach(this::acceptUnitHandler);
	}
}
