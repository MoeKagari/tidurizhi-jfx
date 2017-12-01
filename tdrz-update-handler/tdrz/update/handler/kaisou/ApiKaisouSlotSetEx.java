package tdrz.update.handler.kaisou;

import java.util.Map;

import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.handler.UnitHandler;

public class ApiKaisouSlotSetEx extends UnitHandler {
	public ApiKaisouSlotSetEx(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		//后接ship3
		//nothing to do
	}

	@Override
	public boolean haveAnyUpdate() {
		return false;
	}
}
