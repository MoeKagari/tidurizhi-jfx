package tdrz.update.handler.other;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.data.word.WordSlotItem;
import tdrz.update.handler.UnitHandler;

public class ApiOtherSlotItem extends UnitHandler {
	private final List<WordSlotItem> slotItemList;

	public ApiOtherSlotItem(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		this.slotItemList = ((JsonArray) api_data).getValuesAs(JsonObject.class).stream().map(WordSlotItem::new).collect(Collectors.toList());
	}

	@Override
	public boolean needClearSlotItemList() {
		return true;
	}

	@Override
	public List<WordSlotItem> getAddSlotItemList() {
		return this.slotItemList;
	}
}
