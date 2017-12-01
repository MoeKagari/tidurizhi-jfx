package tdrz.core.translator;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import tdrz.core.util.AppConstants;
import tdrz.core.util.HPMessage;
import tdrz.update.UnitManager;
import tdrz.update.data.word.WordMasterData.WordMasterShip;
import tdrz.update.data.word.WordMasterData.WordMasterSlotitem;
import tdrz.update.data.word.WordShip;
import tdrz.update.data.word.WordSlotItem;
import tdrz.update.unit.UnitDeck.DeckHolder;
import tdrz.update.unit.UnitNdock.NdockHodler;
import tool.function.FunctionUtils;

public class ShipTranslator {
	public static double getHPPercent(WordShip ship) {
		if (ship == null) return 1;
		return ship.getNowHp() * 1.0 / ship.getMaxHp();
	}

	public static String getTypeString(WordShip ship) {
		int type = ship.getMasterData().getType();
		switch (type) {
			case 1:
				return "海防艦";
			case 2:
				return "駆逐艦";
			case 3:
				return "軽巡洋艦";
			case 4:
				return "重雷装巡洋艦";
			case 5:
				return "重巡洋艦";
			case 6:
				return "航空巡洋艦";
			case 7:
				return "軽空母";
			case 8:
				return "巡洋戦艦";
			case 9:
				return "戦艦";
			case 10:
				return "航空戦艦";
			case 11:
				return "正規空母";
			case 12:
				return "超弩級戦艦";
			case 13:
				return "潜水艦";
			case 14:
				return "潜水空母";
			case 15:
				return "補給艦";//敌方
			case 16:
				return "水上機母艦";
			case 17:
				return "揚陸艦";
			case 18:
				return "装甲空母";
			case 19:
				return "工作艦";
			case 20:
				return "潜水母艦";
			case 21:
				return "練習巡洋艦";
			case 22:
				return "補給艦";//自方
			default:
				return String.valueOf(type);
		}
	}

	public static String getName(WordShip ship) {
		return ship.getMasterData().getName();
	}

	public static String getDetail(WordShip ship) {
		return String.join("\n", new String[] {
				getName(ship),
				String.format("经验: %d/%d", ship.getNextExp(), ship.getCurrentExp()),
				String.format("速力: %s", getSokuString(ship, true))
		});
	}

	/** 高速? */
	public static boolean highspeed(WordShip ship) {
		return ship.getSoku() != 5;
	}

	public static String getSokuString(WordShip ship, boolean showHighspeed) {
		if (ship == null) return "";
		int soku = ship.getSoku();
		switch (soku) {
			case 5:
				return "低速";
			case 10:
				return showHighspeed ? "高速" : "";
			case 15:
				return "高速+";
			case 20:
				return "最速";
			default:
				return Integer.toString(soku);
		}
	}

	public static int getSuodi(WordShip ship) {
		return FunctionUtils.stream(ArrayUtils.addAll(ship.getSlots(), ship.getSlotex()))
				.map(SlotItemTranslator::getSuodi)
				.sum();
	}

	public static int getZhikong(WordShip ship) {
		int zhikong = 0;
		for (int i = 0; i < 4; i++) {
			zhikong += SlotItemTranslator.getZhikong(ship.getSlots()[i], ship.getOnSlot()[i]);
		}
		return zhikong;
	}

	/** 明石? */
	public static boolean isAkashi(WordShip ship) {
		return ship.getShipId() == 182 || ship.getShipId() == 187;
	}

	/** 入渠中 */
	public static boolean isInNyukyo(WordShip ship) {
		return Arrays.stream(UnitManager.getUnitManager().getNdockHodlers()).map(NdockHodler::getNdock)
				.anyMatch(
						ndock -> FunctionUtils.isNotNull(ndock) && ndock.getShipId() == ship.getId()
		/**/);
	}

	/** 远征中 */
	public static boolean isInMission(WordShip ship) {
		return Arrays.stream(UnitManager.getUnitManager().getDeckHolders())
				.map(DeckHolder::getDeck).anyMatch(
						deck -> FunctionUtils.isNotNull(deck) &&
								DeckDtoTranslator.isInMission(deck) &&
								DeckDtoTranslator.isShipInDeck(deck, ship.getId())
		/**/);
	}

	public static String getStateString(WordShip ship, boolean showMax) {
		String text = HPMessage.getString(getHPPercent(ship));
		return FunctionUtils.isFalse(showMax) && StringUtils.equals(text, HPMessage.getString(1)) ? "" : text;
	}

	/** 需要补给 */
	public static boolean needHokyo(WordShip ship) {
		WordMasterShip msdd = ship.getMasterData();
		if (msdd == null) return false;
		return msdd.getFuelMax() != ship.getFuel() || //
				msdd.getBullMax() != ship.getBull() || //
				FunctionUtils.isFalse(Arrays.equals(msdd.getOnslotMax(), ship.getOnSlot()));
	}

	/** 完好 */
	public static boolean perfectState(WordShip ship) {
		return ship.getNowHp() >= ship.getMaxHp();
	}

	/** 擦伤小破 */
	public static boolean healthyState(WordShip ship) {
		return FunctionUtils.isFalse(perfectState(ship) || terribleState(ship));
	}

	/** 中破大破 */
	public static boolean terribleState(WordShip ship) {
		return getHPPercent(ship) <= 0.5;
	}

	public static boolean dapo(WordShip ship) {
		return getHPPercent(ship) <= 0.25;
	}

	public static int whichDeck(WordShip ship) {
		for (int index = 0; index < 4; index++) {
			if (DeckDtoTranslator.isShipInDeck(UnitManager.getUnitManager().getDeck(index), ship.getId())) {
				return index;
			}
		}
		return -1;
	}

	public static String whichDeckString(WordShip ship) {
		return FunctionUtils.ifFunction(ShipTranslator.whichDeck(ship), wd -> wd != -1, wd -> AppConstants.DEFAULT_FLEET_NAME[wd], "");
	}

	public static boolean canOpeningTaisen(WordShip ship) {
		WordMasterShip msd = ship.getMasterData();
		//驱逐舰,轻巡洋舰,重雷装巡洋舰,练习巡洋舰
		if (IntStream.of(2, 3, 4, 21).noneMatch(type -> type == msd.getType())) {
			return false;
		}

		return 64 <= ship.getTaisen()[0] -
				FunctionUtils.stream(ArrayUtils.addAll(ship.getSlots(), ship.getSlotex()))
						.mapToObj(UnitManager.getUnitManager()::getSlotItem)
						.filter(FunctionUtils::isNotNull)
						.map(WordSlotItem::getMasterData)
						.mapToInt(WordMasterSlotitem::getTaisen)
						.sum();
	}

	public static boolean canEquipDaihatsu(WordShip ship) {
		return true;//TODO
	}

	public static int getPowerHougeki(WordShip ship) {
		return 0;//TODO
	}

	public static int getPowerRageki(WordShip ship) {
		return 0;//TODO
	}

	public static int getPowerMidnight(WordShip ship) {
		return 0;//TODO
	}

	/** 改造所需资源 */
	public static int[] getRemodelConsumption(WordShip ship) {
		WordMasterShip wms = ship.getMasterData();
		int[] mainMaterial = new int[] { 0, wms.getGaizhaoBull(), wms.getGaizhaoFuel(), 0 };

		//转换改造需要额外的资源
		int[] otherMaterial;
		switch (ship.getShipId()) {//TODO
			default:
				otherMaterial = new int[] { 0, 0, 0, 0 };
		}

		return ArrayUtils.addAll(mainMaterial, otherMaterial);
	}

	/** 改造所需图纸 */
	public static int getRemodelDrawing(WordShip ship) {
		switch (ship.getShipId()) {//TODO
			default:
				return 0;
		}
	}

	/** 改造所需甲板 */
	public static int getRemodelCatapult(WordShip ship) {
		switch (ship.getShipId()) {//TODO
			default:
				return 0;
		}
	}

	public static interface AbstractShip {
		/** 唯一 */
		public int getId();

		/** 不唯一 */
		public int getShipId();
	}

	public static interface AbstractShipParameter {

	}
}
