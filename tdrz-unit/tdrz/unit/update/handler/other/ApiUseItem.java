package tdrz.unit.update.handler.other;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;import tdrz.unit.update.UnitManager;
import java.util.stream.Collectors;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.unit.update.UnitHandler;
import tdrz.unit.update.dto.word.UseItemDto;

public class ApiUseItem extends UnitHandler {
	private final List<UseItemDto> useItems;

	public ApiUseItem(UnitManager unitManager,long time, Map<String, String> fields, JsonValue api_data) {
		JsonObject json = (JsonObject) api_data;
		this.useItems = json.getJsonArray("api_ship").getValuesAs(JsonObject.class).stream().map(UseItemDto::new).collect(Collectors.toCollection(LinkedList::new));
	}

	@Override
	public boolean needClearUseItemList() {
		return true;
	}

	@Override
	public List<UseItemDto> getAddUseItemList() {
		return this.useItems;
	}
}
