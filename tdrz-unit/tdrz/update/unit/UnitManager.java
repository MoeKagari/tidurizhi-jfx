package tdrz.update.unit;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Optional;

import javax.json.JsonObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tdrz.server.RawApiData;
import tdrz.server.RawApiDataType;
import tdrz.update.data.word.WordShip;
import tdrz.update.data.word.WordSlotItem;
import tdrz.update.handler.UnitHandler;
import tdrz.update.unit.main.AirBaseUnit;
import tdrz.update.unit.main.BattleUnit;
import tdrz.update.unit.main.DeckUnit;
import tdrz.update.unit.main.KaisouUnit;
import tdrz.update.unit.main.KdockUnit;
import tdrz.update.unit.main.MissionUnit;
import tdrz.update.unit.main.NdockUnit;
import tdrz.update.unit.main.PracticeUnit;
import tdrz.update.unit.main.QuestUnit;
import tdrz.update.unit.main.RemodelUnit;
import tdrz.update.unit.other.AkashiUnit;
import tdrz.update.unit.other.MaterialUnit;
import tdrz.update.unit.other.MemoryUnit;
import tdrz.update.unit.other.PrintUnit;
import tdrz.update.unit.other.ShipUnit;
import tdrz.update.unit.other.SlotItemUnit;
import tdrz.update.unit.other.UseItemUnit;
import tdrz.update.unit.other.WordUnit;

public class UnitManager {
	private final static Logger LOG = LogManager.getLogger(UnitManager.class);
	private final static SimpleDateFormat JSONFILETIMEFORMAT = new SimpleDateFormat("yyMMdd_HHmmss.SSS");
	private final static UnitManager INSTANCE = new UnitManager();

	private UnitManager() {}

	public static UnitManager getUnitManager() {
		return INSTANCE;
	}

	public Thread newProcessDataThread(long time, String serverName, String uri, Map<String, String> headers, ByteArrayOutputStream requestBody, ByteArrayOutputStream responseBody) {
		return new Thread(() -> {
			RawApiData rawApiData;
			try {
				rawApiData = new RawApiData(time, serverName, uri, headers, requestBody, responseBody);
			} catch (Exception ex) {
				LOG.warn(uri + "\r\n" + requestBody + "\r\n" + responseBody, ex);
				LOG.warn("\r\n");
				return;
			}

			RawApiDataType type = rawApiData.getType();
			if (type == null) {
				System.err.println("Ｘ定义的api : " + rawApiData.getUri());
				return;
			}
			System.out.println("Ｏ定义的api : " + rawApiData.getUri());

			JsonObject json = rawApiData.getJsonObject();
			int api_result = json.getInt("api_result");
			if (api_result != 1) {//有猫
				LOG.warn(String.format("%s,猫了,%d,api-%s", JSONFILETIMEFORMAT.format(rawApiData.getTime()), api_result, type));
				LOG.warn("\r\n");
				return;
			}

			try {
				UnitHandler handler = UnitHandler.getUnitHandler(type, this, rawApiData.getTime(), rawApiData.getFields(), json.get("api_data"));
				if (handler.haveAnyUpdate()) {//对一些无需处理的 api ,直接滤过
					this.doUnitHandler(handler);
				}
			} catch (Throwable ex) {
				LOG.warn(rawApiData);
				LOG.warn(String.format("api-%s更新错误", type), ex);
				LOG.warn("\r\n");
			}
		});
	}

	/* -------------------------------------------------------------------------------------------------------- */

	public WordShip getSecretaryShip() {
		return Optional.ofNullable(this.deckUnit.deckHolders[0].getDeck()).map(deck -> this.getShip(deck.getShips()[0])).orElse(null);
	}

	public WordShip getShip(int id) {
		return this.shipUnit.shipMap.get(id);
	}

	public WordSlotItem getSlotItem(int id) {
		return this.slotItemUnit.slotItemMap.get(id);
	}

	/* -------------------------------------------------------------------------------------------------------- */

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

	public static abstract class Unit<T> {
		private final Logger logger = LogManager.getLogger(this.getClass());

		public final Logger getLogger() {
			return this.logger;
		}

		public abstract void accept(UnitManager unitManager, T unitHandler);
	}
}
