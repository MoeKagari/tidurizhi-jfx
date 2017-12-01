package tdrz.update.data.word;

import javax.json.JsonObject;

import tdrz.update.data.AbstractWord;
import tool.JsonUtils;

public class WordPresetDeck extends AbstractWord {
	private final int no;
	private final String name;
	private final int[] ships;

	public WordPresetDeck(int no, JsonObject json) {
		this.no = no;
		this.name = json.getString("api_name");
		this.ships = JsonUtils.getIntArray(json, "api_ship");
	}

	public int getNo() {
		return this.no;
	}

	public String getName() {
		return this.name;
	}

	public int[] getShips() {
		return this.ships;
	}
}
