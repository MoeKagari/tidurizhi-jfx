package tdrz.update.handler.ndock;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.data.word.WordNdock;
import tdrz.update.handler.UnitHandler;
import tdrz.update.unit.UnitNdock.NdockUpdate;

public class ApiNdock extends UnitHandler {
	private final List<NdockUpdate> ndockUpdateList;

	public ApiNdock(UnitManager unitManager,long time, Map<String, String> fields, JsonValue api_data) {
		this.ndockUpdateList = ((JsonArray) api_data).getValuesAs(JsonObject.class).stream()
				.map(json -> {
					int api_id = json.getInt("api_id");
					WordNdock ndock = new WordNdock(json);
					return new NdockUpdate(api_id, ndock);
				}).collect(Collectors.toList());
	}

	@Override
	public List<NdockUpdate> getNdockList() {
		return this.ndockUpdateList;
	}
}
