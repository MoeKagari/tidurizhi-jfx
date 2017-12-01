package tdrz.update.handler.deck;

import java.util.Map;

import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.handler.UnitHandler;

public class ApiDeckItemUseCond extends UnitHandler {
	public ApiDeckItemUseCond(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		//TODO  暂时不处理
		//后接 ship2 , useitem
	}

	@Override
	public boolean haveAnyUpdate() {
		return false;
	}
}
