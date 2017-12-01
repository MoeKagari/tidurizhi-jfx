package tdrz.update.handler.practice;

import java.util.Map;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.data.word.WordPracticeEnemy;
import tdrz.update.handler.UnitHandler;

public class ApiPracticeList extends UnitHandler {
	private final WordPracticeEnemy[] practiceEnemyArray;

	public ApiPracticeList(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		this.practiceEnemyArray = ((JsonObject) api_data).getJsonArray("api_list").getValuesAs(JsonObject.class).stream()
				.map(WordPracticeEnemy::new)
				.toArray(WordPracticeEnemy[]::new);
	}

	@Override
	public WordPracticeEnemy[] getPracticeEnemyArray() {
		return this.practiceEnemyArray;
	}
}
