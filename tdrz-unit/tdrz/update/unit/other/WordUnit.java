package tdrz.update.unit.other;

import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Optional;
import java.util.function.Supplier;

import javax.json.Json;
import javax.json.JsonObject;

import tdrz.update.data.word.WordBasic;
import tdrz.update.data.word.WordMapinfo;
import tdrz.update.data.word.WordMasterData;
import tdrz.update.data.word.WordRecord;
import tdrz.update.unit.UnitManager;
import tdrz.update.unit.UnitManager.Unit;
import tdrz.update.unit.other.WordUnit.WordUnitHandler;

public class WordUnit extends Unit<WordUnitHandler> {
	/**
	 * 是否结成联合舰队<br>
	 * 0=未结成<br>
	 * 1=机动,2=水上,3=输送<br>
	 * -x = 强制解队 , 队伍编成不满足当前联合舰队要求时 , 比如解体<br>
	 * 未确认
	 */
	private Integer combined = 0;
	private String comment = null;
	private WordBasic basicInformation = null;
	private WordMapinfo mapInfo = null;
	private WordMasterData masterData = null;
	private WordRecord record = null;

	public WordUnit() {
		try {
			JsonObject json = Json.createReader(new InputStreamReader(WordUnit.class.getResourceAsStream("/MasterData.json"), Charset.forName("utf-8"))).readObject();
			this.masterData = new WordMasterData(json);
		} catch (Exception e) {
			this.getLogger().warn("MasterData读取失败", e);
		}
	}

	public WordMasterData getMasterData() {
		return this.masterData;
	}

	@Override
	public void accept(UnitManager unitManager, WordUnitHandler unitHandler) {
		this.comment = this.updateField(unitHandler::getComment, this.comment);
		this.combined = this.updateField(unitHandler::getCombined, this.combined);
		this.basicInformation = this.updateField(unitHandler::getBasicInformation, this.basicInformation);
		this.mapInfo = this.updateField(unitHandler::getMapInfo, this.mapInfo);
		this.masterData = this.updateField(unitHandler::getMasterData, this.masterData);
		this.record = this.updateField(unitHandler::getRecord, this.record);
	}

	private <S> S updateField(Supplier<S> newValue, S oldValue) {
		return Optional.ofNullable(newValue.get()).orElse(oldValue);
	}

	public static interface WordUnitHandler {
		public default String getComment() {
			return null;
		}

		public default Integer getCombined() {
			return null;
		}

		public default WordBasic getBasicInformation() {
			return null;
		}

		public default WordMapinfo getMapInfo() {
			return null;
		}

		public default WordMasterData getMasterData() {
			return null;
		}

		public default WordRecord getRecord() {
			return null;
		}
	}
}
