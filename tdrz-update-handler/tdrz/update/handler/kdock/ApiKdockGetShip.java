package tdrz.update.handler.kdock;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.data.word.WordShip;
import tdrz.update.data.word.WordSlotItem;
import tdrz.update.handler.UnitHandler;
import tool.function.FunctionUtils;

public class ApiKdockGetShip extends UnitHandler {
	private final WordShip newShip;
	private final List<WordSlotItem> slotItems;
	private final UnitHandler kdock;

	public ApiKdockGetShip(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		JsonObject json = (JsonObject) api_data;
		//新船
		this.newShip = new WordShip(json.getJsonObject("api_ship"));
		//新船的装备,有可能为JsonValue.NULL
		this.slotItems = Optional.ofNullable(json.get("api_slotitem")).filter(value -> value instanceof JsonArray).map(value -> {
			return ((JsonArray) value).getValuesAs(JsonObject.class).stream().map(WordSlotItem::new).collect(Collectors.toList());
		}).orElse(Collections.emptyList());
		this.kdock = new ApiKdock(unitManager, time, fields, json.get("api_kdock"));
	}

	@Override
	public List<WordShip> getAddShipList() {
		return FunctionUtils.asList(this.newShip);
	}

	@Override
	public List<WordSlotItem> getAddSlotItemList() {
		return this.slotItems;
	}

	@Override
	public List<UnitHandler> otherUnitHandler() {
		return FunctionUtils.asList(this.kdock);
	}
}
