package tdrz.update.handler.other;

import java.util.Map;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.data.word.WordBasic;
import tdrz.update.handler.UnitHandler;

public class ApiOtherBasic extends UnitHandler {
	private final WordBasic basic;

	public ApiOtherBasic(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		this.basic = new WordBasic((JsonObject) api_data);
	}

	@Override
	public WordBasic getBasicInformation() {
		return this.basic;
	}
}
