package tdrz.update.handler.other;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.data.word.WordUseItem;
import tdrz.update.handler.UnitHandler;

public class ApiOtherUseItem extends UnitHandler {
	private final List<WordUseItem> useItemList;

	public ApiOtherUseItem(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		this.useItemList = ((JsonArray) api_data).getValuesAs(JsonObject.class).stream().map(WordUseItem::new).collect(Collectors.toList());
	}

	@Override
	public boolean needClearUseItemList() {
		return true;
	}

	@Override
	public List<WordUseItem> getAddUseItemList() {
		return this.useItemList;
	}
}
