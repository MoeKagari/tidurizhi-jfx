package tdrz.update.handler.other;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.data.AbstractMemory.MemoryObjSlotItem;
import tdrz.update.data.memory.MemorySlotItem;
import tdrz.update.data.memory.slotitem.MemorySlotItemLostDestroy;
import tdrz.update.handler.UnitHandler;
import tdrz.update.unit.UnitMaterial.MaterialChangeOption;
import tdrz.update.unit.UnitMemory.MemoryChange;
import tool.JsonUtils;
import tool.function.FunctionUtils;

public class ApiOtherDestroySlotItem extends UnitHandler {
	private final List<Integer> removeSlotItemList;
	private final int[] newMaterial;
	private final MemorySlotItemLostDestroy memorySlotItemLostDestroy;

	public ApiOtherDestroySlotItem(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		JsonObject json = (JsonObject) api_data;

		this.removeSlotItemList = FunctionUtils.stream(fields.get("api_slotitem_ids").trim().split(",")).map(Integer::parseInt).collect(Collectors.toList());
		int[] oldMaterial = unitManager.getCurrentMaterial().getAmount();
		this.newMaterial = MaterialChangeOption.getNewMaterial(oldMaterial, JsonUtils.getIntArray(json, "api_get_material"), MaterialChangeOption.INCREASE_MAIN);
		this.memorySlotItemLostDestroy = new MemorySlotItemLostDestroy(
				time,
				this.removeSlotItemList.stream().map(unitManager::getSlotItem).map(MemoryObjSlotItem::new).toArray(MemoryObjSlotItem[]::new),
				oldMaterial, this.newMaterial
		/**/);
	}

	@Override
	public int[] getNewMaterial() {
		return this.newMaterial;
	}

	@Override
	public List<Integer> getRemovedSlotItemList() {
		return this.removeSlotItemList;
	}

	@Override
	public MemoryChange<MemorySlotItem> getMemorySlotItemChange() {
		return new MemoryChange<MemorySlotItem>() {
			@Override
			public MemorySlotItem getMemoryChange() {
				return ApiOtherDestroySlotItem.this.memorySlotItemLostDestroy;
			}
		};
	}
}
