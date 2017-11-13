package tdrz.update.handler.other;

import java.util.Map;

import javax.json.JsonValue;

import tdrz.update.handler.UnitHandler;
import tdrz.update.unit.UnitManager;

public class ApiEventMapRankSelect extends UnitHandler {
	public ApiEventMapRankSelect(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		//		{api_map_no:1,api_maparea_id:37,api_rank:2}
		//		{"api_result":1,"api_result_msg":"成功","api_data":{"api_max_maphp":"100"}}
		//TODO
	}
}
