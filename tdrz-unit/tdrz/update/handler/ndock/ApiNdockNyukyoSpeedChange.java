package tdrz.update.handler.ndock;

import java.util.Map;

import javax.json.JsonValue;

import tdrz.update.handler.UnitHandler;
import tdrz.update.unit.UnitManager;
import tdrz.update.unit.main.NdockUnit.NdockNyukyoSpeedChange;

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
