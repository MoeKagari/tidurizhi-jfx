package tdrz.update.handler.practice;

import java.util.Map;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.UnitHandler;
import tdrz.update.UnitManager;
import tdrz.update.data.word.WordPracticeEnemy;

public class ApiPracticeEnemyInfo extends UnitHandler {
	private final WordPracticeEnemy practiceEnemy;

	public ApiPracticeEnemyInfo(UnitManager unitManager,long time, Map<String, String> fields, JsonValue api_data) {
		this.practiceEnemy = new WordPracticeEnemy((JsonObject) api_data);
	}

	@Override
	public WordPracticeEnemy getPracticeEnemy() {
		return this.practiceEnemy;
	}
}
