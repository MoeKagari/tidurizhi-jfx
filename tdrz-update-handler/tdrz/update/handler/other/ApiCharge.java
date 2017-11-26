package tdrz.update.handler.other;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.core.util.JsonUtils;
import tdrz.update.UnitManager;
import tdrz.update.handler.UnitHandler;

public class ApiCharge extends UnitHandler {
	private final List<Charge> charges;

	public ApiCharge(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		JsonObject json = (JsonObject) api_data;
		this.charges = json.getJsonArray("api_ship").getValuesAs(JsonObject.class).stream().map(info -> {
			int api_id = info.getInt("api_id");
			int api_fuel = info.getInt("api_fuel");
			int api_bull = info.getInt("api_bull");
			int[] api_onslot = JsonUtils.getIntArray(info, "api_onslot");
			return new Charge(api_id, api_fuel, api_bull, api_onslot);
		}).collect(Collectors.toList());
		//currentMaterial.setMainMaterial("补给", time, JsonUtils.getIntArray(json.getJsonArray("api_material")));

		//		this.materialChangeHolder = Optional.ofNullable(unitManager.getCurrentMaterial())
		//				.map(material -> {
		//					return new MaterialChange(material.getAmount(), JsonUtils.getIntArray(json.getJsonArray("api_material")), UnitMaterial.MaterialChangeOption.UPDATE_MAIN);
		//				}).orElse(null);
	}

	@Override
	public List<Charge> getCharge() {
		return this.charges;
	}
}
