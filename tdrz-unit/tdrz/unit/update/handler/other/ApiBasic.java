package tdrz.unit.update.handler.other;

import java.util.Map;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.unit.update.UnitHandler;
import tdrz.unit.update.UnitManager;
import tdrz.unit.update.dto.word.BasicDto;

public class ApiBasic extends UnitHandler {
	private final BasicDto basic;

	public ApiBasic(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		this.basic = new BasicDto((JsonObject) api_data);
	}

	@Override
	public BasicDto getBasicInformation() {
		return this.basic;
	}
}
