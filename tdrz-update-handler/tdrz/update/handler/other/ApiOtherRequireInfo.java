package tdrz.update.handler.other;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.handler.UnitHandler;
import tdrz.update.handler.kdock.ApiKdock;

public class ApiOtherRequireInfo extends UnitHandler {
	private final UnitHandler apiSlotItem, apiUseItem, apiKdock;

	public ApiOtherRequireInfo(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		JsonObject json = (JsonObject) api_data;
		this.apiSlotItem = new ApiOtherSlotItem(unitManager, time, fields, json.get("api_slot_item"));
		this.apiUseItem = new ApiOtherUseItem(unitManager, time, fields, json.get("api_useitem"));
		this.apiKdock = new ApiKdock(unitManager, time, fields, json.get("api_kdock"));
	}

	@Override
	public List<UnitHandler> otherUnitHandler() {
		return Arrays.asList(this.apiSlotItem, this.apiUseItem, this.apiKdock);
	}
}
