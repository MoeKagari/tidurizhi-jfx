package tdrz.update.handler.deck;

import java.util.Map;

import javax.json.JsonValue;

import tdrz.core.translator.DeckDtoTranslator;
import tdrz.update.UnitManager;
import tdrz.update.data.word.WordDeck;
import tdrz.update.handler.UnitHandler;
import tdrz.update.unit.UnitAkashi.ResetAkashiFlagshipWhenChange;
import tdrz.update.unit.UnitDeck.DeckChange;

public class ApiDeckChange extends UnitHandler {
	private final DeckChange deckChange;
	private final ResetAkashiFlagshipWhenChange resetAkashiFlagshipWhenChange;

	public ApiDeckChange(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		int oldDeckId = Integer.parseInt(fields.get("api_id"));//1,2,3,4
		int oldIndex = Integer.parseInt(fields.get("api_ship_idx"));//变更位置,0开始
		int oldShipId = Integer.parseInt(fields.get("api_ship_id"));

		int newDeckId = -1, newIndex = -1, newShipId = -1;
		if (oldIndex != -1 && oldShipId != -1) {
			//替换为另外的ship
			newShipId = unitManager.getDeckHolders()[oldDeckId].getDeck().getShipArray()[oldIndex];
			for (int index = 0; index < unitManager.getDeckHolders().length; index++) {
				int shipIndex = DeckDtoTranslator.indexInDeck(unitManager.getDeckHolders()[index].getDeck(), oldShipId);
				if (shipIndex != -1) {
					//替换的ship在此deck中
					newDeckId = index;
					newIndex = shipIndex;
					break;
				}
			}
		}
		int[] oldDeckChangeDetail = new int[] { oldDeckId, oldIndex, oldShipId };
		int[] newDeckChangeDetail = new int[] { newDeckId, newIndex, newShipId };

		boolean resetAkashiTimer = false;//是否重置泊地修理计时器
		for (int[] deckChangeDetail : new int[][] { oldDeckChangeDetail, newDeckChangeDetail }) {
			int deckId = deckChangeDetail[0], index = deckChangeDetail[1];
			if (deckId != -1) {
				WordDeck deck = unitManager.getDeck(deckId - 1);
				if (DeckDtoTranslator.isAkashiFlagship(deck) && index != -1) {
					//明石旗舰 并且 不是[除旗舰以外全解除]
					resetAkashiTimer |= true;
				}
			}
		}

		this.deckChange = new DeckChange(oldDeckChangeDetail, newDeckChangeDetail);
		this.resetAkashiFlagshipWhenChange = new ResetAkashiFlagshipWhenChange(resetAkashiTimer, time);
	}

	@Override
	public ResetAkashiFlagshipWhenChange getResetAkashiFlagshipWhenChange() {
		return this.resetAkashiFlagshipWhenChange;
	}

	@Override
	public DeckChange getDeckChange() {
		return this.deckChange;
	}
}
