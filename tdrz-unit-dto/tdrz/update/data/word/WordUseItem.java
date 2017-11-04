package tdrz.update.data.word;

import javax.json.JsonObject;

import tdrz.core.translator.MasterDataTranslator;
import tdrz.update.data.AbstractWord;
import tdrz.update.data.word.WordMasterData.WordMasterUserItem;

/**
 * 道具栏内的道具,除了几个特殊(比如损管,女神,etc)
 * 
 * @author MoeKagari
 */
public class WordUseItem extends AbstractWord {
	private int id;
	private int count;
	private WordMasterUserItem masterData;

	public WordUseItem(JsonObject json) {
		this.id = json.getInt("api_id");
		this.count = json.getInt("api_count");
		this.masterData = MasterDataTranslator.getMasterUserItem(this.id);
	}

	public WordMasterUserItem getMasterData() {
		if (this.masterData == null) {
			this.masterData = MasterDataTranslator.getMasterUserItem(this.id);
		}
		return this.masterData;
	}

	public int getId() {
		return this.id;
	}

	public int getCount() {
		return this.count;
	}
}
