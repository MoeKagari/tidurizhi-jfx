package tdrz.update.handler.other;

import java.util.Map;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.data.word.WordBasic;
import tdrz.update.handler.UnitHandler;
import tdrz.update.unit.UnitManager;

public class ApiBasic extends UnitHandler {
	private final WordBasic basic;

	public ApiBasic(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		this.basic = new WordBasic((JsonObject) api_data);
	}

	@Override
	public WordBasic getBasicInformation() {
		return this.basic;
	}
}
