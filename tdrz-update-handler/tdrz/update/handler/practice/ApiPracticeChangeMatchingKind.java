package tdrz.update.handler.practice;

import java.util.Map;

import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.handler.UnitHandler;

public class ApiPracticeChangeMatchingKind extends UnitHandler {
	public ApiPracticeChangeMatchingKind(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		//nothing to do
	}

	@Override
	public boolean haveAnyUpdate() {
		return false;
	}
}
