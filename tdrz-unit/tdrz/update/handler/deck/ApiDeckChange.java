package tdrz.update.handler.deck;

import java.util.Map;

import javax.json.JsonValue;

import tdrz.update.UnitHandler;
import tdrz.update.UnitManager;
import tdrz.update.part.DeckUnit.DeckChange;
import tdrz.update.part.other.AkashiUnit.ResetAkashiFlagshipWhenChange;

public class ApiDeckChange extends UnitHandler {
	private final DeckChange deckChange;
	private final ResetAkashiFlagshipWhenChange resetAkashiFlagshipWhenChange;

	public ApiDeckChange(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		int api_id = Integer.parseInt(fields.get("api_id"));
		int api_ship_idx = Integer.parseInt(fields.get("api_ship_idx"));//变更位置,0开始
		int api_ship_id = Integer.parseInt(fields.get("api_ship_id"));
		this.deckChange = new DeckChange(api_id, api_ship_idx, api_ship_id);
		this.resetAkashiFlagshipWhenChange = new ResetAkashiFlagshipWhenChange(api_id, api_ship_idx, time);
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
