package tdrz.unit.update.handler.kdock;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.unit.update.UnitHandler;
import tdrz.unit.update.UnitManager;
import tdrz.unit.update.dto.word.KdockDto;
import tdrz.unit.update.part.KdockUnit.KdockUpdate;

public class ApiKdock extends UnitHandler {
	private final List<KdockUpdate> kdockUpdates;

	public ApiKdock(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		this.kdockUpdates = ((JsonArray) api_data).getValuesAs(JsonObject.class).stream()
				.map(json -> {
					int api_id = json.getInt("api_id");
					KdockDto kdock = new KdockDto(json);
					return new KdockUpdate(api_id, kdock);
				}).collect(Collectors.toList());
	}

	@Override
	public List<KdockUpdate> getKdockUpdate() {
		return this.kdockUpdates;
	}
}
