package tdrz.update.data.word;

import javax.json.JsonObject;

import tdrz.core.translator.MasterDataTranslator;
import tdrz.update.data.AbstractWord;

public class WordPracticeEnemyInfo extends AbstractWord {
	private final PracticeEnemyInfoShip[] shipArray;

	public WordPracticeEnemyInfo(JsonObject json) {
		this.shipArray = json.getJsonObject("api_deck").getJsonArray("api_ships").getValuesAs(JsonObject.class).stream()
				.map(PracticeEnemyInfoShip::new)
				.toArray(PracticeEnemyInfoShip[]::new);
	}

	public PracticeEnemyInfoShip[] getShipArray() {
		return this.shipArray;
	}

	public class PracticeEnemyInfoShip {
		private final JsonObject json;

		public PracticeEnemyInfoShip(JsonObject json) {
			this.json = json;
		}

		public boolean exist() {
			return this.json.getInt("api_id") > 0;
		}

		public int getLevel() {
			return this.json.getInt("api_level");
		}

		public String getName() {
			return MasterDataTranslator.getShipName(this.json.getInt("api_ship_id"));
		}

		public int getStar() {
			return this.json.getInt("api_star");
		}

		public String getStarString() {
			return "☆☆☆☆☆☆☆☆☆☆".substring(0, this.getStar());
		}
	}
}
