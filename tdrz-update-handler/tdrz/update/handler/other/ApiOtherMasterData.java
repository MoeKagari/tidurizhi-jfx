package tdrz.update.handler.other;

import java.util.Map;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.data.word.WordMasterData;
import tdrz.update.handler.UnitHandler;

public class ApiOtherMasterData extends UnitHandler {
	private final WordMasterData masterData;

	public ApiOtherMasterData(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		this.masterData = new WordMasterData((JsonObject) api_data);
	}

	@Override
	public WordMasterData getMasterData() {
		return this.masterData;
	}
}
