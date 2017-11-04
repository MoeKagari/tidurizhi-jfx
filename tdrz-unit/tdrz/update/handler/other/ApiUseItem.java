package tdrz.update.handler.other;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.UnitHandler;
import tdrz.update.UnitManager;
import tdrz.update.data.word.WordUseItem;

public class ApiUseItem extends UnitHandler {
	private final List<WordUseItem> useItems;

	public ApiUseItem(UnitManager unitManager,long time, Map<String, String> fields, JsonValue api_data) {
		JsonObject json = (JsonObject) api_data;
		this.useItems = json.getJsonArray("api_ship").getValuesAs(JsonObject.class).stream().map(WordUseItem::new).collect(Collectors.toCollection(LinkedList::new));
	}

	@Override
	public boolean needClearUseItemList() {
		return true;
	}

	@Override
	public List<WordUseItem> getAddUseItemList() {
		return this.useItems;
	}
}
