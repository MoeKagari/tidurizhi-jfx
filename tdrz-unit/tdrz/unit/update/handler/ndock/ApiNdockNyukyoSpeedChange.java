package tdrz.unit.update.handler.ndock;

import java.util.Map;import tdrz.unit.update.UnitManager;

import javax.json.JsonValue;

import tdrz.unit.update.UnitHandler;
import tdrz.unit.update.part.NdockUnit.NdockNyukyoSpeedChange;

public class ApiNdockNyukyoSpeedChange extends UnitHandler {
	private final NdockNyukyoSpeedChange nnsc;

	public ApiNdockNyukyoSpeedChange(UnitManager unitManager,long time, Map<String, String> fields, JsonValue api_data) {
		this.nnsc = new NdockNyukyoSpeedChange(Integer.parseInt(fields.get("api_ndock_id")));
	}

	@Override
	public NdockNyukyoSpeedChange getNdockNyukyoSpeedChange() {
		return this.nnsc;
	}
}
