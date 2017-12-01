package tdrz.update.handler.practice;

import java.util.Map;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.data.word.WordPracticeEnemyInfo;
import tdrz.update.handler.UnitHandler;

public class ApiPracticeEnemyInfo extends UnitHandler {
	private final WordPracticeEnemyInfo practiceEnemyInfo;

	public ApiPracticeEnemyInfo(UnitManager unitManager,long time, Map<String, String> fields, JsonValue api_data) {
		this.practiceEnemyInfo = new WordPracticeEnemyInfo((JsonObject) api_data);
	}

	@Override
	public WordPracticeEnemyInfo getPracticeEnemyInfo() {
		return this.practiceEnemyInfo;
	}
}
