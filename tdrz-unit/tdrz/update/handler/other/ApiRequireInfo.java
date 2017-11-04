package tdrz.update.handler.other;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.UnitHandler;
import tdrz.update.UnitManager;
import tdrz.update.handler.kdock.ApiKdock;

public class ApiRequireInfo extends UnitHandler {
	private final UnitHandler slotitem, useitem, kdock;

	public ApiRequireInfo(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		JsonObject json = (JsonObject) api_data;
		this.slotitem = new ApiSlotItem(unitManager, time, fields, json.get("api_slot_item"));
		this.useitem = new ApiUseItem(unitManager, time, fields, json.get("api_useitem"));
		this.kdock = new ApiKdock(unitManager, time, fields, json.get("api_kdock"));
		//TODO  api_unsetslot
	}

	@Override
	public List<UnitHandler> otherUnitHandler() {
		return Arrays.asList(this.slotitem, this.useitem, this.kdock);
	}
}
