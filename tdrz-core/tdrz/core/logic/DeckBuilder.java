package tdrz.core.logic;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

import tdrz.update.UnitManager;
import tdrz.update.data.word.WordDeck;
import tdrz.update.data.word.WordShip;
import tdrz.update.data.word.WordSlotItem;

public class DeckBuilder {
	private final UnitManager unitManager;

	public DeckBuilder(UnitManager unitManager) {
		this.unitManager = unitManager;
	}

	@Override
	public String toString() {
		return this.build();
	}

	private String build() {
		JsonObjectBuilder builder = Json.createObjectBuilder();

		builder.add("version", 4);
		for (int i = 0; i < 4; i++) {
			WordDeck deck = this.unitManager.getDeck(i);
			if (deck != null) {
				builder.add("f" + (i + 1), this.getFleetBuilder(deck));
			}
		}

		return builder.build().toString();
	}

	private JsonObjectBuilder getFleetBuilder(WordDeck deck) {
		JsonObjectBuilder fleetBuilder = Json.createObjectBuilder();

		for (int i = 0; i < 6; i++) {
			WordShip ship = this.unitManager.getShip(deck.getShipArray()[i]);
			if (ship != null) {
				fleetBuilder.add("s" + (i + 1), this.getShipBuilder(ship));
			}
		}

		return fleetBuilder;
	}

	private JsonObjectBuilder getShipBuilder(WordShip ship) {
		JsonObjectBuilder shipBuilder = Json.createObjectBuilder();

		shipBuilder.add("id", String.valueOf(ship.getShipId()));
		shipBuilder.add("lv", ship.getLevel());
		shipBuilder.add("luck", ship.getLuck()[0]);
		{
			JsonObjectBuilder itemsBuilder = Json.createObjectBuilder();
			for (int i = 0; i < 4; i++) {
				WordSlotItem item = this.unitManager.getSlotItem(ship.getSlots()[i]);
				if (item != null) {
					itemsBuilder.add("i" + (i + 1), this.getItemBuilder(item));
				}
			}
			{
				WordSlotItem item = this.unitManager.getSlotItem(ship.getSlotex());
				if (item != null) {
					itemsBuilder.add("ix", this.getItemBuilder(item));
				}
			}
			shipBuilder.add("items", itemsBuilder);
		}

		return shipBuilder;
	}

	private JsonObjectBuilder getItemBuilder(WordSlotItem item) {
		JsonObjectBuilder itemBuilder = Json.createObjectBuilder();

		itemBuilder.add("id", item.getSlotitemId());
		itemBuilder.add("rf", item.getLevel());
		if (item.getAlv() > 0) {
			itemBuilder.add("mas", item.getAlv());
		}

		return itemBuilder;
	}
}
