package tdrz.update.handler.mission;

import java.util.Map;

import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.handler.UnitHandler;

public class ApiMission extends UnitHandler {
	public ApiMission(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		//nothing to do
	}

	@Override
	public boolean haveAnyUpdate() {
		return false;
	}
}
