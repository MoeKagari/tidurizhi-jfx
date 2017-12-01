package tdrz.update.handler.kdock;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.data.memory.MemoryKdock;
import tdrz.update.data.memory.kdock.MemoryKdockCreateShip;
import tdrz.update.data.word.WordKdock;
import tdrz.update.handler.UnitHandler;
import tdrz.update.unit.UnitKdock.KdockUpdate;
import tdrz.update.unit.UnitMemory.MemoryChange;

public class ApiKdock extends UnitHandler {
	private final List<KdockUpdate> kdockUpdateList;
	private final MemoryKdockCreateShip memoryKdockCreateShip;

	public ApiKdock(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		this.kdockUpdateList = ((JsonArray) api_data).getValuesAs(JsonObject.class).stream()
				.map(json -> {
					int api_id = json.getInt("api_id");
					WordKdock kdock = new WordKdock(json);
					return new KdockUpdate(api_id, kdock);
				}).collect(Collectors.toList());

		MemoryKdockCreateShip memoryKdockCreateShip = null;
		for (KdockUpdate kdockUpdate : this.kdockUpdateList) {
			MemoryKdockCreateShip temp = unitManager.getKdockHolders()[kdockUpdate.api_id - 1].getMemoryKdockCreateShip();
			if (temp != null) {
				memoryKdockCreateShip = temp;
				memoryKdockCreateShip.setCreatedShipId(kdockUpdate.kdock.getShipId());
			}
		}
		this.memoryKdockCreateShip = memoryKdockCreateShip;
	}

	@Override
	public List<KdockUpdate> getKdockUpdateList() {
		return this.kdockUpdateList;
	}

	@Override
	public MemoryChange<MemoryKdock> getMemoryKdockChange() {
		return new MemoryChange<MemoryKdock>() {
			@Override
			public MemoryKdock getMemoryChange() {
				return ApiKdock.this.memoryKdockCreateShip;
			}
		};
	}
}
