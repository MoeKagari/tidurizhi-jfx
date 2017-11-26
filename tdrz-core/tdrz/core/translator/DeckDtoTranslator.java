package tdrz.core.translator;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

import tdrz.update.UnitManager;
import tdrz.update.data.word.WordDeck;
import tdrz.update.data.word.WordShip;
import tool.function.FunctionUtils;

public class DeckDtoTranslator {
	private static Stream<WordShip> getShipStream(WordDeck deck) {
		return Arrays.stream(deck.getShipArray()).mapToObj(UnitManager.getUnitManager()::getShip).filter(FunctionUtils::isNotNull);
	}

	public static int getZhikong(WordDeck deck) {
		if (deck == null) return 0;
		return getShipStream(deck).mapToInt(ShipTranslator::getZhikong).sum();
	}

	public static int getSuodi(WordDeck deck) {
		if (deck == null) return 0;
		return getShipStream(deck).mapToInt(ShipTranslator::getSuodi).sum();
	}

	public static int getTotalLevel(WordDeck deck) {
		if (deck == null) return 0;
		return getShipStream(deck).mapToInt(WordShip::getLevel).sum();
	}

	public static boolean highspeed(WordDeck deck) {
		if (deck == null) return true;
		return getShipStream(deck).allMatch(ShipTranslator::highspeed);
	}

	public static boolean isShipInDeck(WordDeck deck, int id) {
		return indexInDeck(deck, id) != -1;
	}

	public static int indexInDeck(WordDeck deck, int id) {
		if (deck != null) {
			int[] ships = deck.getShipArray();
			for (int index = 0; index < ships.length; index++) {
				int ship = ships[index];
				if (ship != -1 && ship == id) {
					return index;
				}
			}
		}
		return -1;
	}

	public static boolean isAkashiFlagship(WordDeck deck) {
		if (deck == null) return false;
		return ShipTranslator.isAkashi(UnitManager.getUnitManager().getShip(deck.getShipArray()[0]));
	}

	/** 泊地修理到点时,是否应该提醒 */
	public static boolean shouldNotifyAkashiTimer(WordDeck deck) {
		if (deck == null) return false;
		//远征中
		if (isInMission(deck)) return false;

		WordShip flagship = UnitManager.getUnitManager().getShip(deck.getShipArray()[0]);
		if (flagship == null) return false;
		if (ShipTranslator.isAkashi(flagship) == false) return false;
		//入渠中,中破大破,不能修理
		Predicate<WordShip> cannot = ship -> ShipTranslator.isInNyukyo(ship) || ShipTranslator.terribleState(ship);
		//明石不能修理自己时,同时不能修理其它舰娘
		if (cannot.test(flagship)) return false;

		//修理数(2+修理设施)
		int count = 2 + Arrays.stream(flagship.getSlots()).filter(SlotItemTranslator::isRepairItem).map(i -> 1).sum();
		//没有入渠,擦伤小破,可以修理		
		Predicate<WordShip> can = ship -> !ShipTranslator.isInNyukyo(ship) && ShipTranslator.healthyState(ship);
		return Arrays.stream(deck.getShipArray()).limit(count).mapToObj(UnitManager.getUnitManager()::getShip).filter(FunctionUtils::isNotNull).anyMatch(can);
	}

	public static boolean isInMission(WordDeck deck) {
		if (deck == null) return false;
		return deck.getDeckMission().getState() != 0;
	}

	public static boolean hasDapo(WordDeck deck) {
		if (deck == null) return false;
		return getShipStream(deck).anyMatch(ShipTranslator::dapo);
	}
}
