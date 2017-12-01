package tdrz.update.data.word;

import javax.json.JsonObject;

import tdrz.update.data.AbstractWord;

/**
 * 战绩界面
 * 
 * @author MoeKagari
 */
public class WordRecord extends AbstractWord {
	private String comment;

	public WordRecord(JsonObject json) {
		this.comment = json.getString("api_cmt");
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
