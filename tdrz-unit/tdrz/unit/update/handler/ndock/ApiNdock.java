package tdrz.unit.update.handler.ndock;

import java.util.List;
import java.util.Map;import tdrz.unit.update.UnitManager;
import java.util.stream.Collectors;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.unit.update.UnitHandler;
import tdrz.unit.update.dto.word.NdockDto;
import tdrz.unit.update.part.NdockUnit.NdockUpdate;

public class ApiNdock extends UnitHandler {
	private final List<NdockUpdate> ndockUpdates;

	public ApiNdock(UnitManager unitManager,long time, Map<String, String> fields, JsonValue api_data) {
		this.ndockUpdates = ((JsonArray) api_data).getValuesAs(JsonObject.class).stream()
				.map(json -> {
					int api_id = json.getInt("api_id");
					NdockDto ndock = new NdockDto(json);
					return new NdockUpdate(api_id, ndock);
				}).collect(Collectors.toList());
	}

	@Override
	public List<NdockUpdate> getNdock() {
		return this.ndockUpdates;
	}
}
