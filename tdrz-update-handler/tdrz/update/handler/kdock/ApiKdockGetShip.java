package tdrz.update.handler.kdock;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.data.AbstractMemory.MemoryObjShip;
import tdrz.update.data.memory.MemoryKdock;
import tdrz.update.data.memory.kdock.MemoryKdockGetShip;
import tdrz.update.data.word.WordShip;
import tdrz.update.data.word.WordSlotItem;
import tdrz.update.handler.UnitHandler;
import tdrz.update.unit.UnitMemory.MemoryChange;
import tool.function.FunctionUtils;

public class ApiKdockGetShip extends UnitHandler {
	private final WordShip newShip;
	private final List<WordSlotItem> addSlotItemList;
	private final UnitHandler kdock;
	private final MemoryKdockGetShip memoryKdockGetShip;

	public ApiKdockGetShip(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		JsonObject json = (JsonObject) api_data;
		//新船
		this.newShip = new WordShip(json.getJsonObject("api_ship"));
		//新船的装备,无装备时为JsonValue.NULL
		this.addSlotItemList = Optional.ofNullable(json.get("api_slotitem"))
				.filter(value -> value instanceof JsonArray)
				.map(value -> ((JsonArray) value).getValuesAs(JsonObject.class).stream().map(WordSlotItem::new).collect(Collectors.toList()))
				.orElse(FunctionUtils.emptyList());
		this.kdock = new ApiKdock(unitManager, time, fields, json.get("api_kdock"));
		this.memoryKdockGetShip = new MemoryKdockGetShip(time, new MemoryObjShip(this.newShip, this.addSlotItemList));
	}

	@Override
	public MemoryChange<MemoryKdock> getMemoryKdockChange() {
		return new MemoryChange<MemoryKdock>() {
			@Override
			public MemoryKdock getMemoryChange() {
				return ApiKdockGetShip.this.memoryKdockGetShip;
			}
		};
	}

	@Override
	public List<WordShip> getAddShipList() {
		return FunctionUtils.asList(this.newShip);
	}

	@Override
	public List<WordSlotItem> getAddSlotItemList() {
		return this.addSlotItemList;
	}

	@Override
	public List<UnitHandler> otherUnitHandler() {
		return FunctionUtils.asList(this.kdock);
	}
}
