package tdrz.unit.update.handler.remodel;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;import tdrz.unit.update.UnitManager;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.unit.update.UnitHandler;
import tdrz.unit.update.dto.word.SlotItemDto;
import tool.function.FunctionUtils;

@SuppressWarnings("unused")
public class ApiRemodelSlot extends UnitHandler {
	private final int slotId;
	private final boolean certain, success;
	private final List<SlotItemDto> newItem;
	private final List<Integer> useSlotIds;

	public ApiRemodelSlot(UnitManager unitManager,long time, Map<String, String> fields, JsonValue api_data) {
		JsonObject json = (JsonObject) api_data;

		this.slotId = Integer.parseInt(fields.get("api_slot_id"));
		this.certain = Integer.parseInt(fields.get("api_certain_flag")) == 1;
		this.success = json.getInt("api_remodel_flag") == 1;

		//失败时,null
		//成功非更新装备时,level +1
		//更新装备时,为新装备的数据,id与原装备相同
		this.newItem = Optional.ofNullable(json.getJsonObject("api_after_slot")).map(SlotItemDto::new).map(FunctionUtils::asList).orElse(Collections.emptyList());

		//改修用到的材料装备
		if (json.containsKey("api_use_slot_id")) {
			this.useSlotIds = json.getJsonArray("api_use_slot_id").getValuesAs(JsonNumber.class).stream().map(JsonNumber::intValue).collect(Collectors.toCollection(LinkedList::new));
		} else {
			this.useSlotIds = Collections.emptyList();
		}

		//		FunctionUtils.forEachInt(this.useSlotIds, id -> GlobalContext.destroyItem(time, "改修消耗", id, this.useSlotIds.length));
		//
		//		memoryList.add(new RemodleRecordDto(time, this.slotId, this.certain, this.success, item, this.newItem));
		//		currentMaterial.setMaterial("改修", time, JsonUtils.getIntArray(json, "api_after_material"));
	}

	@Override
	public List<SlotItemDto> getAddSlotItemList() {
		return this.newItem;
	}

	@Override
	public List<Integer> getRemoveSlotItemList() {
		return this.useSlotIds;
	}
}
