package tdrz.update.handler.deck;

import java.util.Map;

import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.handler.UnitHandler;

public class ApiDeckPresetDelete extends UnitHandler {
	public ApiDeckPresetDelete(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		//nothing to do
	}

	@Override
	public boolean haveAnyUpdate() {
		return false;
	}
}
