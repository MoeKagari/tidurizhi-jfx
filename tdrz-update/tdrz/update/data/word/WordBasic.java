package tdrz.update.data.word;

import javax.json.JsonObject;

import tdrz.update.data.AbstractWord;

/**
 * 提督的一些游戏数据
 * 
 * @author MoeKagari
 */
public class WordBasic extends AbstractWord {
	private final JsonObject json;

	public WordBasic(JsonObject json) {
		this.json = json;
	}

	/** 甲章个数 */
	public int getMedalNumber() {
		return this.json.getInt("api_medals");
	}

	public String getUserName() {
		return this.json.getString("api_nickname");
	}

	public int getUserLevel() {
		return this.json.getInt("api_level");
	}

	public int getMaxChara() {
		return this.json.getInt("api_max_chara");
	}

	public int getMaxSlotItem() {
		return this.json.getInt("api_max_slotitem");
	}

	public int getDeckLength() {
		return this.json.getInt("api_count_deck");
	}

	public int getKdockLength() {
		return this.json.getInt("api_count_kdock");
	}

	public int getNdockLength() {
		return this.json.getInt("api_count_ndock");
	}
}
