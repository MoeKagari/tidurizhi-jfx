package tdrz.unit.update.handler.other;

import java.util.Map;import tdrz.unit.update.UnitManager;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.unit.update.UnitHandler;
import tdrz.unit.update.dto.word.RecordDto;

public class ApiRecord extends UnitHandler {
	private final RecordDto record;

	public ApiRecord(UnitManager unitManager,long time, Map<String, String> fields, JsonValue api_data) {
		this.record = new RecordDto((JsonObject) api_data);
	}

	@Override
	public RecordDto getRecord() {
		return this.record;
	}
}
