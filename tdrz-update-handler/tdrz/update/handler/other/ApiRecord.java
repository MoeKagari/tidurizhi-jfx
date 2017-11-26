package tdrz.update.handler.other;

import java.util.Map;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.data.word.WordRecord;
import tdrz.update.handler.UnitHandler;

public class ApiRecord extends UnitHandler {
	private final WordRecord record;

	public ApiRecord(UnitManager unitManager,long time, Map<String, String> fields, JsonValue api_data) {
		this.record = new WordRecord((JsonObject) api_data);
	}

	@Override
	public WordRecord getRecord() {
		return this.record;
	}
}
