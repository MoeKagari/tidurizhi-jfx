package tdrz.update.unit;

import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.function.Supplier;

import javax.json.Json;

import tdrz.update.UnitManager.Unit;
import tdrz.update.data.word.WordBasic;
import tdrz.update.data.word.WordMapinfo;
import tdrz.update.data.word.WordMasterData;
import tdrz.update.data.word.WordPracticeEnemy;
import tdrz.update.data.word.WordRecord;
import tdrz.update.unit.UnitWord.UnitHandlerWord;
import tool.function.FunctionUtils;

public class UnitWord extends Unit<UnitHandlerWord> {
	/**
	 * 是否结成联合舰队<br>
	 * 0=未结成<br>
	 * 1=机动,2=水上,3=输送<br>
	 * -x = 强制解队 , 队伍编成不满足当前联合舰队要求时 , 比如解体<br>
	 * 未确认
	 */
	private Integer combined = 0;
	private WordBasic basicInformation = null;
	private WordMapinfo mapInfo = null;
	private WordMasterData masterData;
	private WordRecord record = null;
	private WordPracticeEnemy practiceEnemy = null;

	public UnitWord() {
		try {
			this.masterData = new WordMasterData(
					Json.createReader(
							new InputStreamReader(
									UnitWord.class.getResourceAsStream("/MasterData.json"),
									Charset.forName("utf-8")
							/**/)
					/**/).readObject()
			/**/);
		} catch (Exception e) {
			this.getLogger().warn("MasterData读取失败", e);
		}
	}

	public Integer getCombined() {
		return this.combined;
	}

	public WordRecord getRecord() {
		return this.record;
	}

	public WordMasterData getMasterData() {
		return this.masterData;
	}

	public WordBasic getBasicInformation() {
		return this.basicInformation;
	}

	public WordMapinfo getMapInfo() {
		return this.mapInfo;
	}

	public WordPracticeEnemy getPracticeEnemy() {
		return this.practiceEnemy;
	}

	@Override
	public void accept(UnitHandlerWord unitHandler) {
		this.record = this.updateField(unitHandler::getRecord, this.record);
		this.combined = this.updateField(unitHandler::getCombined, this.combined);
		this.basicInformation = this.updateField(unitHandler::getBasicInformation, this.basicInformation);
		this.mapInfo = this.updateField(unitHandler::getMapInfo, this.mapInfo);
		this.masterData = this.updateField(unitHandler::getMasterData, this.masterData);
		this.practiceEnemy = this.updateField(unitHandler::getPracticeEnemy, this.practiceEnemy);

		FunctionUtils.notNull(unitHandler.getComment(), comment -> {
			if (this.record != null) {
				this.record.setComment(comment);
			}
		});
	}

	private <S> S updateField(Supplier<S> newValue, S oldValue) {
		return FunctionUtils.notNull(newValue.get(), FunctionUtils::returnSelf, oldValue);
	}

	public static interface UnitHandlerWord {
		public default String getComment() {
			return null;
		}

		public default WordPracticeEnemy getPracticeEnemy() {
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
