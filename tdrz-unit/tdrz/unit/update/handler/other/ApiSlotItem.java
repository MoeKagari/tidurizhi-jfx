package tdrz.unit.update.handler.other;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;import tdrz.unit.update.UnitManager;
import java.util.stream.Collectors;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.unit.update.UnitHandler;
import tdrz.unit.update.dto.word.SlotItemDto;

public class ApiSlotItem extends UnitHandler {
	private final List<SlotItemDto> slotItems;

	public ApiSlotItem(UnitManager unitManager,long time, Map<String, String> fields, JsonValue api_data) {
		JsonObject json = (JsonObject) api_data;
		this.slotItems = json.getJsonArray("api_ship").getValuesAs(JsonObject.class).stream().map(SlotItemDto::new).collect(Collectors.toCollection(LinkedList::new));
	}

	@Override
	public boolean needClearSlotItemList() {
		return true;
	}

	@Override
	public List<SlotItemDto> getAddSlotItemList() {
		return this.slotItems;
	}
}
