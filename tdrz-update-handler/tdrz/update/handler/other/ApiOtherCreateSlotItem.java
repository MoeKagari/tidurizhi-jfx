package tdrz.update.handler.other;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.data.AbstractMemory.MemoryObjSlotItem;
import tdrz.update.data.memory.MemorySlotItem;
import tdrz.update.data.memory.slotitem.MemorySlotItemGetCreate;
import tdrz.update.data.word.WordSlotItem;
import tdrz.update.handler.UnitHandler;
import tdrz.update.unit.UnitMemory.MemoryChange;
import tool.JsonUtils;
import tool.function.FunctionUtils;

public class ApiOtherCreateSlotItem extends UnitHandler {
	private final int[] newMaterial;
	private final List<WordSlotItem> newSlotItemList;
	private final MemorySlotItemGetCreate memorySlotItemGetCreate;

	public ApiOtherCreateSlotItem(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		JsonObject json = (JsonObject) api_data;
		WordSlotItem newSlotItem = Optional.ofNullable(json.getJsonObject("api_slot_item")).map(WordSlotItem::new).orElse(null);
		this.newSlotItemList = Optional.ofNullable(newSlotItem).map(FunctionUtils::asList).orElse(FunctionUtils.emptyList());
		int[] oldMaterial = unitManager.getCurrentMaterial().getAmount();
		this.newMaterial = JsonUtils.getIntArray(json, "api_material");
		this.memorySlotItemGetCreate = new MemorySlotItemGetCreate(
				time,
				Optional.ofNullable(newSlotItem).map(MemoryObjSlotItem::new).orElse(null),
				oldMaterial, this.newMaterial
		/**/);
	}

	@Override
	public List<WordSlotItem> getAddSlotItemList() {
		return this.newSlotItemList;
	}

	@Override
	public MemoryChange<MemorySlotItem> getMemorySlotItemChange() {
		return new MemoryChange<MemorySlotItem>() {
			@Override
			public MemorySlotItem getMemoryChange() {
				return ApiOtherCreateSlotItem.this.memorySlotItemGetCreate;
			}
		};
	}

	@Override
	public int[] getNewMaterial() {
		return this.newMaterial;
	}
}
