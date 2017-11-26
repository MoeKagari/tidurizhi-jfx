package tdrz.core.translator;

import java.util.Map;
import java.util.function.Function;

import tdrz.update.UnitManager;
import tdrz.update.data.word.WordMasterData;
import tdrz.update.data.word.WordMasterData.WordMasterMission;
import tdrz.update.data.word.WordMasterData.WordMasterShip;
import tdrz.update.data.word.WordMasterData.WordMasterSlotitem;
import tdrz.update.data.word.WordMasterData.WordMasterUserItem;
import tool.function.FunctionUtils;

public class MasterDataTranslator {
	public static String getMissionName(int id) {
		return FunctionUtils.notNull(getMasterMissionDto(id), WordMasterMission::getName, "");
	}

	public static String getShipName(int id) {
		return FunctionUtils.notNull(getMasterShipDto(id), WordMasterShip::getName, "");
	}

	public static String getSlotitemName(int id) {
		return FunctionUtils.notNull(getMasterSlotitem(id), WordMasterSlotitem::getName, "");
	}

	public static String getUseitemName(int id) {
		return FunctionUtils.notNull(getMasterUserItem(id), WordMasterUserItem::getName, "");
	}

	public static WordMasterShip getMasterShipDto(int id) {
		return getMasterData(id, WordMasterData::getMasterShipDataMap);
	}

	public static WordMasterSlotitem getMasterSlotitem(int id) {
		return getMasterData(id, WordMasterData::getMasterSlotitemDataMap);
	}

	public static WordMasterMission getMasterMissionDto(int id) {
		return getMasterData(id, WordMasterData::getMasterMissionDataMap);
	}

	public static WordMasterUserItem getMasterUserItem(int id) {
		return getMasterData(id, WordMasterData::getMasterUserItemDtoMap);
	}

	private static <T> T getMasterData(int id, Function<WordMasterData, Map<Integer, T>> fun) {
		return fun.apply(UnitManager.getUnitManager().getMasterData()).get(id);
	}
}
