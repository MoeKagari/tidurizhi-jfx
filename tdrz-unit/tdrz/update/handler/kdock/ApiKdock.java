package tdrz.update.handler.kdock;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.data.word.WordKdock;
import tdrz.update.handler.UnitHandler;
import tdrz.update.unit.UnitManager;
import tdrz.update.unit.main.KdockUnit.KdockUpdate;

public class ApiKdock extends UnitHandler {
	private final List<KdockUpdate> kdockUpdates;

	public ApiKdock(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		this.kdockUpdates = ((JsonArray) api_data).getValuesAs(JsonObject.class).stream()
				.map(json -> {
					int api_id = json.getInt("api_id");
					WordKdock kdock = new WordKdock(json);
					return new KdockUpdate(api_id, kdock);
				}).collect(Collectors.toList());
	}

	@Override
	public List<KdockUpdate> getKdockUpdate() {
		return this.kdockUpdates;
	}
}
