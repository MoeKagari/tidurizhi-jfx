package tdrz.update.handler.other;

import java.util.Map;

import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.handler.UnitHandler;

public class ApiMaterial extends UnitHandler {
	public ApiMaterial(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		//		int[] materialChange = ((JsonArray) api_data)
		//				.getValuesAs(JsonObject.class)
		//				.stream()
		//				.sorted(Comparator.comparingInt(json -> json.getInt("api_id")))// 1到8
		//				.mapToInt(json -> json.getInt("api_value"))
		//				.toArray();
		//		this.materialChangeHolder = Optional.ofNullable(unitManager.getCurrentMaterial())
		//				.map(material -> {
		//					return new MaterialChange(material.getAmount(), materialChange, UnitMaterial.MaterialChangeOption.UPDATE);
		//				}).orElse(null);
	}
}
