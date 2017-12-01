package tdrz.update.data.word;

import javax.json.JsonObject;

import tdrz.update.data.AbstractWord;

public class WordPracticeEnemy extends AbstractWord {
	private final JsonObject json;

	public WordPracticeEnemy(JsonObject json) {
		this.json = json;
	}

	/*
	 	api_enemy_id			：敵提督ID
		api_enemy_name			：敵提督名
		api_enemy_name_id		：
		api_enemy_level			：敵艦隊司令部Lv.
		api_enemy_rank			：敵階級 string
		api_enemy_flag			：旗フラグ?　1=銅, 2=銀, 3=金
		api_enemy_flag_ship		：敵艦隊旗艦ID
		api_enemy_comment		：敵コメント
		api_enemy_comment_id	：
		api_state				：0=未挑戦, 1=E敗北?, 2=D敗北?, 3=C敗北, 4=B勝利, 5=A勝利, 6=S勝利
		api_medals				：甲種勲章保有数 
	 */

	public int getEnemyId() {
		return this.json.getInt("api_enemy_id");
	}
}
