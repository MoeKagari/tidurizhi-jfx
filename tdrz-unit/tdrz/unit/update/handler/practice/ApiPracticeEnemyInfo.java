package tdrz.unit.update.handler.practice;

import java.util.Map;import tdrz.unit.update.UnitManager;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.unit.update.UnitHandler;
import tdrz.unit.update.dto.word.PracticeEnemyDto;

public class ApiPracticeEnemyInfo extends UnitHandler {
	private final PracticeEnemyDto practiceEnemy;

	public ApiPracticeEnemyInfo(UnitManager unitManager,long time, Map<String, String> fields, JsonValue api_data) {
		this.practiceEnemy = new PracticeEnemyDto((JsonObject) api_data);
	}

	@Override
	public PracticeEnemyDto getPracticeEnemy() {
		return this.practiceEnemy;
	}
}
