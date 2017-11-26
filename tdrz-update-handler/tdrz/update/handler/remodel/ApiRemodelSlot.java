package tdrz.update.handler.remodel;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonValue;

import org.apache.commons.lang3.ArrayUtils;

import tdrz.core.util.JsonUtils;
import tdrz.update.UnitManager;
import tdrz.update.data.memory.MemoryOther;
import tdrz.update.data.memory.other.MemoryRemodel;
import tdrz.update.data.word.WordSlotItem;
import tdrz.update.handler.UnitHandler;
import tdrz.update.unit.UnitMaterial;
import tdrz.update.unit.UnitMaterial.MaterialChangeOption;
import tdrz.update.unit.UnitMemory.MemoryChange;
import tool.function.FunctionUtils;

public class ApiRemodelSlot extends UnitHandler {
	private final List<WordSlotItem> addSlotItemList;
	private final List<Integer> removeSlotItemList;
	private final int[] newMaterial;
	private final MemoryRemodel memoryRemodel;

	public ApiRemodelSlot(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		JsonObject json = (JsonObject) api_data;

		boolean certain = Integer.parseInt(fields.get("api_certain_flag")) == 1;
		boolean success = json.getInt("api_remodel_flag") == 1;
		int[] oldMaterial = ArrayUtils.clone(unitManager.getCurrentMaterial().getAmount());
		int[] newMaterial = MaterialChangeOption.getNewMaterial(oldMaterial, JsonUtils.getIntArray(json, "api_after_material"), UnitMaterial.MaterialChangeOption.UPDATE);
		WordSlotItem oldSlotItem = unitManager.getSlotItem(Integer.parseInt(fields.get("api_slot_id")));
		//失败时,null
		//成功非更新装备时,level +1
		//更新装备时,为新装备的数据,id与原装备相同
		WordSlotItem api_after_slot;
		if (json.containsKey("api_after_slot")) {
			api_after_slot = new WordSlotItem(json.getJsonObject("api_after_slot"));
		} else {
			api_after_slot = null;
		}
		//改修用到的材料装备
		List<Integer> api_use_slot_id;
		if (json.containsKey("api_use_slot_id")) {
			api_use_slot_id = json.getJsonArray("api_use_slot_id").getValuesAs(JsonNumber.class).stream().map(JsonNumber::intValue).collect(Collectors.toList());
		} else {
			api_use_slot_id = Collections.emptyList();
		}

		//

		this.addSlotItemList = Optional.ofNullable(api_after_slot).map(FunctionUtils::asList).orElse(FunctionUtils.emptyList());
		this.removeSlotItemList = api_use_slot_id;
		this.newMaterial = newMaterial;
		this.memoryRemodel = new MemoryRemodel(
				time,
				certain, success, oldSlotItem, api_after_slot,
				api_use_slot_id.stream().map(unitManager::getSlotItem).collect(Collectors.toList()),
				oldMaterial, newMaterial
		/**/);
	}

	@Override
	public MemoryChange<MemoryOther> getMemoryOtherChange() {
		return new MemoryChange<MemoryOther>() {
			@Override
			public MemoryOther getMemoryChange() {
				return ApiRemodelSlot.this.memoryRemodel;
			}
		};
	}

	@Override
	public int[] getNewMaterial() {
		return this.newMaterial;
	}

	@Override
	public List<WordSlotItem> getAddSlotItemList() {
		return this.addSlotItemList;
	}

	@Override
	public List<Integer> getRemoveSlotItemList() {
		return this.removeSlotItemList;
	}
}
