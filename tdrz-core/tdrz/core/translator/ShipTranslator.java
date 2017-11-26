package tdrz.core.translator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import tdrz.core.logic.HPMessage;
import tdrz.core.util.AppConstants;
import tdrz.update.UnitManager;
import tdrz.update.data.word.WordMasterData.WordMasterShip;
import tdrz.update.data.word.WordMasterData.WordMasterSlotitem;
import tdrz.update.data.word.WordShip;
import tdrz.update.data.word.WordSlotItem;
import tdrz.update.unit.UnitDeck.DeckHolder;
import tdrz.update.unit.UnitNdock.NdockHodler;
import tool.function.FunctionUtils;

/** 全部方法 ship==null 可 */
public class ShipTranslator {
	public static double getHPPercent(WordShip ship) {
		if (ship == null) return 1;
		return ship.getNowHp() * 1.0 / ship.getMaxHp();
	}

	public static String getTypeString(WordShip ship) {
		if (ship == null) return "";
		return getTypeString(ship.getMasterData());
	}

	public static String getTypeString(WordMasterShip msdd) {
		if (msdd == null) return "";
		int type = msdd.getType();
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
		if (ship == null) return "";
		return FunctionUtils.notNull(ship.getMasterData(), WordMasterShip::getName, "");
	}

	public static String getDetail(WordShip ship) {
		if (ship == null) return "";
		ArrayList<String> detail = new ArrayList<>();
		{
			detail.add(getName(ship));
			detail.add(String.format("经验: %d/%d", ship.getNextExp(), ship.getCurrentExp()));
			detail.add(String.format("速力: %s", getSokuString(ship, true)));
		}
		return StringUtils.join(detail, "\n");
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

	public static boolean highspeed(WordShip ship) {
		if (ship == null) return true;
		return ship.getSoku() != 5;
	}

	public static int getSuodi(WordShip ship) {
		if (ship == null) return 0;
		int suodi = 0;
		for (int i = 0; i < 4; i++) {
			suodi += SlotItemTranslator.getSuodi(ship.getSlots()[i]);
		}
		return suodi;
	}

	public static int getZhikong(WordShip ship) {
		if (ship == null) return 0;
		int zhikong = 0;
		for (int i = 0; i < 4; i++) {
			zhikong += SlotItemTranslator.getZhikong(ship.getSlots()[i], ship.getOnSlot()[i]);
		}
		return zhikong;
	}

	public static boolean isAkashi(WordShip ship) {
		if (ship == null) return false;
		return ship.getShipId() == 182 || ship.getShipId() == 187;
	}

	public static boolean isInNyukyo(WordShip ship) {
		if (ship == null) return false;
		return Arrays.stream(UnitManager.getUnitManager().getNdockHodlers()).map(NdockHodler::getNdock).anyMatch(
				ndock -> FunctionUtils.isNotNull(ndock) &&
						ndock.getShipId() == ship.getId()
		/**/);
	}

	public static boolean isInMission(WordShip ship) {
		if (ship == null) return false;
		return Arrays.stream(UnitManager.getUnitManager().getDeckHolders()).map(DeckHolder::getDeck).anyMatch(
				deck -> FunctionUtils.isNotNull(deck) &&
						DeckDtoTranslator.isInMission(deck) &&
						DeckDtoTranslator.isShipInDeck(deck, ship.getId())
		/**/);
	}

	public static String getStateString(WordShip ship, boolean showMax) {
		if (ship == null) return "";
		String text = HPMessage.getString(getHPPercent(ship));
		return FunctionUtils.isFalse(showMax) && StringUtils.equals(text, HPMessage.getString(1)) ? "" : text;
	}

	public static boolean needHokyo(WordShip ship) {
		if (ship == null) return false;
		WordMasterShip msdd = ship.getMasterData();
		if (msdd == null) return false;
		return msdd.getFuelMax() != ship.getFuel() || //
				msdd.getBullMax() != ship.getBull() || //
				FunctionUtils.isFalse(Arrays.equals(msdd.getOnslotMax(), ship.getOnSlot()));
	}

	/** 完好 */
	public static boolean perfectState(WordShip ship) {
		if (ship == null) return false;
		return ship.getNowHp() == ship.getMaxHp();
	}

	/** 擦伤小破 */
	public static boolean healthyState(WordShip ship) {
		if (ship == null) return false;
		return terribleState(ship) ? false : getHPPercent(ship) < 1;
	}

	/** 中破大破 */
	public static boolean terribleState(WordShip ship) {
		if (ship == null) return false;
		return getHPPercent(ship) <= 0.5;
	}

	public static boolean dapo(WordShip ship) {
		if (ship == null) return false;
		return getHPPercent(ship) <= 0.25;
	}

	public static int whichDeck(WordShip ship) {
		if (ship != null) {
			for (int i = 0; i < 4; i++) {
				if (DeckDtoTranslator.isShipInDeck(UnitManager.getUnitManager().getDeck(i), ship.getId())) {
					return i;
				}
			}
		}
		return -1;
	}

	public static String whichDeckString(WordShip ship) {
		return FunctionUtils.ifFunction(ShipTranslator.whichDeck(ship), wd -> wd != -1, wd -> AppConstants.DEFAULT_FLEET_NAME[wd], "");
	}

	public static boolean canOpeningTaisen(WordShip ship) {
		if (ship == null) return false;

		WordMasterShip msd = ship.getMasterData();
		if (msd == null) return false;
		//驱逐舰,轻巡洋舰,重雷装巡洋舰,练习巡洋舰
		if (IntStream.of(2, 3, 4, 21).noneMatch(type -> type == msd.getType())) return false;

		int taisen = ship.getTaisen()[0];
		for (int equip : ArrayUtils.addAll(ship.getSlots(), ship.getSlotex())) {
			WordMasterSlotitem md = FunctionUtils.notNull(UnitManager.getUnitManager().getSlotItem(equip), WordSlotItem::getMasterData, null);
			taisen -= FunctionUtils.notNull(md, WordMasterSlotitem::getTaisen, 0);
		}
		return taisen >= 64;
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

	public static interface AbstractShip {
		/** 唯一 */
		public int getId();

		/** 不唯一 */
		public int getShipId();
	}

	public static interface AbstractShipParameter {

	}
}
