package tdrz.update.unit;

import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Optional;

import javax.json.Json;

import tdrz.update.UnitManager.Unit;
import tdrz.update.data.word.WordBasic;
import tdrz.update.data.word.WordMapinfo;
import tdrz.update.data.word.WordMasterData;
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

	@Override
	public void accept(UnitHandlerWord unitHandler) {
		this.record = Optional.ofNullable(unitHandler.getRecord()).orElse(this.record);
		this.mapInfo = Optional.ofNullable(unitHandler.getMapinfo()).orElse(this.mapInfo);
		this.combined = Optional.ofNullable(unitHandler.getCombined()).orElse(this.combined);
		this.masterData = Optional.ofNullable(unitHandler.getMasterData()).orElse(this.masterData);
		this.basicInformation = Optional.ofNullable(unitHandler.getBasicInformation()).orElse(this.basicInformation);

		FunctionUtils.notNull(unitHandler.getUpdateComment(), comment -> {
			if (this.record != null) {
				this.record.setComment(comment);
			}
		});
	}

	public static interface UnitHandlerWord {
		public default String getUpdateComment() {
			return null;
		}

		public default Integer getCombined() {
			return null;
		}

		public default WordBasic getBasicInformation() {
			return null;
		}

		public default WordMapinfo getMapinfo() {
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
