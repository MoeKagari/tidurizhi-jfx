package tdrz.update.handler.other;

import java.util.Map;

import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.data.memory.MemoryOther;
import tdrz.update.data.memory.other.MemoryEventMapRankSelect;
import tdrz.update.handler.UnitHandler;
import tdrz.update.unit.UnitMemory.MemoryChange;

public class ApiOtherEventMapRankSelect extends UnitHandler {
	private final MemoryEventMapRankSelect memoryEventMapRankSelect;

	public ApiOtherEventMapRankSelect(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		int api_maparea_id = Integer.parseInt(fields.get("api_maparea_id"));
		int api_map_no = Integer.parseInt(fields.get("api_map_no"));
		int api_rank = Integer.parseInt(fields.get("api_rank"));
		this.memoryEventMapRankSelect = new MemoryEventMapRankSelect(time, api_maparea_id, api_map_no, api_rank);
	}

	@Override
	public MemoryChange<MemoryOther> getMemoryOtherChange() {
		return new MemoryChange<MemoryOther>() {
			@Override
			public MemoryOther getMemoryChange() {
				return ApiOtherEventMapRankSelect.this.memoryEventMapRankSelect;
			}
		};
	}
}
