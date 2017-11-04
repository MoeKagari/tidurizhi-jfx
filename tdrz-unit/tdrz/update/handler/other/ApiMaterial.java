package tdrz.update.handler.other;

import java.util.Comparator;
import java.util.Map;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.UnitHandler;
import tdrz.update.UnitManager;
import tdrz.update.part.other.MaterialUnit.MaterialChangeHodler;
import tdrz.update.part.other.MaterialUnit.MaterialChangeHodler.MaterialChangeOption;

public class ApiMaterial extends UnitHandler {
	private final MaterialChangeHodler mch;

	public ApiMaterial(UnitManager unitManager,long time, Map<String, String> fields, JsonValue api_data) {
		this.mch = new MaterialChangeHodler(time, "刷新",
				((JsonArray) api_data).getValuesAs(JsonObject.class).stream()
						.sorted(Comparator.comparingInt(json -> json.getInt("api_id")))// 1到8
						.mapToInt(json -> json.getInt("api_value"))
						.toArray(),
				MaterialChangeOption.UPDATE);
	}

	@Override
	public MaterialChangeHodler getMaterialChangeHodler() {
		return this.mch;
	}
}
