package tdrz.update;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.json.JsonObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Platform;
import tdrz.server.RawApiData;
import tdrz.server.RawApiDataType;
import tdrz.update.data.memory.MemoryBattle;
import tdrz.update.data.memory.MemoryMission;
import tdrz.update.data.memory.MemoryOther;
import tdrz.update.data.memory.MemoryShip;
import tdrz.update.data.memory.MemorySlotItem;
import tdrz.update.data.word.WordBasic;
import tdrz.update.data.word.WordDeck;
import tdrz.update.data.word.WordKdock;
import tdrz.update.data.word.WordMapinfo;
import tdrz.update.data.word.WordMasterData;
import tdrz.update.data.word.WordMaterial;
import tdrz.update.data.word.WordNdock;
import tdrz.update.data.word.WordPracticeEnemyInfo;
import tdrz.update.data.word.WordQuest;
import tdrz.update.data.word.WordRecord;
import tdrz.update.data.word.WordShip;
import tdrz.update.data.word.WordSlotItem;
import tdrz.update.data.word.WordUseItem;
import tdrz.update.handler.UnitHandler;
import tdrz.update.unit.UnitAirBase;
import tdrz.update.unit.UnitAkashi;
import tdrz.update.unit.UnitBattle;
import tdrz.update.unit.UnitDeck;
import tdrz.update.unit.UnitDeck.DeckHolder;
import tdrz.update.unit.UnitKdock;
import tdrz.update.unit.UnitKdock.KdockHolder;
import tdrz.update.unit.UnitMaterial;
import tdrz.update.unit.UnitMemory;
import tdrz.update.unit.UnitNdock;
import tdrz.update.unit.UnitNdock.NdockHodler;
import tdrz.update.unit.UnitPractice;
import tdrz.update.unit.UnitPrint;
import tdrz.update.unit.UnitPrint.Printer;
import tdrz.update.unit.UnitProperty;
import tdrz.update.unit.UnitQuest;
import tdrz.update.unit.UnitWord;

public class UnitManager {
	private final static Logger LOG = LogManager.getLogger(UnitManager.class);
	private final static SimpleDateFormat JSONFILETIMEFORMAT = new SimpleDateFormat("yyMMdd_HHmmss.SSS");

	//@formatter:off
	private UnitManager() {}
	private final static UnitManager INSTANCE = new UnitManager();
	public static UnitManager getUnitManager() {return INSTANCE;}
	//@formatter:on

	//@formatter:off
	private final List<AAAAAAAAA> listeners = new ArrayList<>();
	public void addListener(AAAAAAAAA listener) {
		this.listeners.add(listener);
	}
	public static interface AAAAAAAAA {//仅仅是因为不知取什么名
		public void update(RawApiDataType type);
	}
	//@formatter:on

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
					this.listeners.parallelStream().forEach(listener -> {
						Platform.runLater(() -> listener.update(type));
					});
				}
			} catch (Exception ex) {
				LOG.warn(rawApiData);
				LOG.warn(String.format("api-%s更新错误", type), ex);
				LOG.warn("\r\n");
			}
		});
	}

	private void doUnitHandler(UnitHandler handler) {
		this.propertyUnit.accept(handler);

		this.airBaseUnit.accept(handler);
		this.deckUnit.accept(handler);
		this.kdockUnit.accept(handler);
		this.ndockUnit.accept(handler);

		this.practiceUnit.accept(handler);
		this.wordUnit.accept(handler);
		this.questUnit.accept(handler);
		this.battleUnit.accept(handler);

		this.materialUnit.accept(handler);
		this.akashiUnit.accept(handler);
		this.printUnit.accept(handler);
		this.memoryUnit.accept(handler);

		handler.otherUnitHandler().forEach(this::doUnitHandler);
	}

	//@formatter:off
	private final UnitDeck deckUnit = new UnitDeck();
	public WordDeck getDeck(int index) {return this.deckUnit.deckHolders[index].getDeck();}
	public DeckHolder[] getDeckHolders() {return this.deckUnit.deckHolders;}



	private final UnitKdock kdockUnit = new UnitKdock();
	public WordKdock getKdock(int index) {return this.kdockUnit.kdockHolders[index].getKdock();}
	public KdockHolder[] getKdockHolders() {return this.kdockUnit.kdockHolders;}



	private final UnitNdock ndockUnit = new UnitNdock();
	public WordNdock getNdock(int index) {return this.ndockUnit.ndockHodlers[index].getNdock();}
	public NdockHodler[] getNdockHodlers() {return this.ndockUnit.ndockHodlers;}



	private final UnitAirBase airBaseUnit = new UnitAirBase();



	private final UnitProperty propertyUnit = new UnitProperty();
	public WordShip getShip(int id) {return this.propertyUnit.shipMap.get(id);}
	public Collection<WordShip> getShips() {return this.propertyUnit.shipMap.values();}
	public WordShip getSecretaryShip() {return Optional.ofNullable(this.getDeck(0)).map(deck -> this.getShip(deck.getShipArray()[0])).orElse(null);}
	public WordSlotItem getSlotItem(int id) {return this.propertyUnit.slotItemMap.get(id);}
	public Collection<WordSlotItem> getSlotItems() {return this.propertyUnit.slotItemMap.values();}	
	public WordUseItem getUseItem(int id) {return this.propertyUnit.useItemMap.get(id);}
	public Collection<WordUseItem> getUseItems() {return this.propertyUnit.useItemMap.values();}



	private final UnitWord wordUnit = new UnitWord();
	public WordMasterData getMasterData() {return this.wordUnit.getMasterData();}
	public WordBasic getBasicInformation() {return this.wordUnit.getBasicInformation();}
	public WordMapinfo getMapInfo() {return this.wordUnit.getMapInfo();}
	public Integer getCombined() {	return this.wordUnit.getCombined();}
	public WordRecord getRecord() {return this.wordUnit.getRecord();}

	
	
	private final UnitPractice practiceUnit = new UnitPractice();
	public WordPracticeEnemyInfo getPracticeEnemyInfo() {return this.practiceUnit.getPracticeEnemyInfo();}
	
	
	
	private final UnitQuest questUnit = new UnitQuest();
	public List<WordQuest> getQuestList() {return this.questUnit.quests;}
	public WordQuest getQuest(int no) {return this.questUnit.quests.stream().filter(quest -> quest.getNo()==no).findFirst().orElse(null);}



	private final UnitMaterial materialUnit = new UnitMaterial();
	public WordMaterial getCurrentMaterial() {return this.materialUnit.getCurrentMaterial();}



	private final UnitMemory memoryUnit = new UnitMemory();
	public List<MemoryBattle> getMemoryBattleList() {return this.memoryUnit.memoryBattleList;}
	public List<MemoryMission> getMemoryMissionList() {return this.memoryUnit.memoryMissionList;}
	public List<MemoryOther> getMemoryOtherList() {return this.memoryUnit.memoryOtherList;}
	public List<MemoryShip> getMemoryShipList() {return this.memoryUnit. memoryShipList;}
	public List<MemorySlotItem> getMemorySlotItemList() {return this.memoryUnit.memorySlotItemList;}


	private final UnitAkashi akashiUnit = new UnitAkashi();
	public long getAkashiRepairStartTime() {return this.akashiUnit.getAkashiTime();}
	


	private final UnitPrint printUnit = new UnitPrint();
	public void addPrinter(Printer printer) {this.printUnit.addPrinter(printer);}

	

	private final UnitBattle battleUnit = new UnitBattle();



	//@formatter:on
	public static abstract class Unit<T> {
		private final Logger logger = LogManager.getLogger(this.getClass());

		public final Logger getLogger() {
			return this.logger;
		}

		public abstract void accept(T unitHandler);
	}
}
