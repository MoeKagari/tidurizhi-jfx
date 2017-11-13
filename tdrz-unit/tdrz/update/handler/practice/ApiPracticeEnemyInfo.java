package tdrz.update.handler.practice;

import java.util.Map;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.data.word.WordPracticeEnemy;
import tdrz.update.handler.UnitHandler;
import tdrz.update.unit.UnitManager;

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
