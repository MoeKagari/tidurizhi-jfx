package tdrz.update.handler.ndock;

import java.util.Map;

import javax.json.JsonValue;

import tdrz.update.handler.UnitHandler;
import tdrz.update.unit.UnitManager;
import tdrz.update.unit.main.NdockUnit.NdockNyukyoStart;

public class ApiNdockNyukyoStart extends UnitHandler {
	private final NdockNyukyoStart ndockNyukyoStart;

	public ApiNdockNyukyoStart(UnitManager unitManager,long time, Map<String, String> fields, JsonValue api_data) {
		int api_ndock_id = Integer.parseInt(fields.get("api_ndock_id"));
		int api_ship_id = Integer.parseInt(fields.get("api_ship_id"));
		boolean api_highspeed = Integer.parseInt(fields.get("api_highspeed")) == 1;
		this.ndockNyukyoStart = new NdockNyukyoStart(api_ndock_id, api_ship_id, api_highspeed);

		//		ShipDto ship = GlobalContext.getShip(Integer.parseInt(data.getField("api_ship_id")));
		//		if (ship != null) {
		//			GlobalContext.getCurrentMaterial().setMaterial("入渠", data.getTime(), ship.getNyukyoCost(), false);
		//		}

		//		if (api_highspeed) {
		//			//使用高速修复,后无ndock
		//			FunctionUtils.notNull(ship, ShipDto::nyukyoEnd);
		//			GlobalContext.getCurrentMaterial().setMaterial("高速修复", data.getTime(), new int[] { 0, 0, 0, 0, 0, 1, 0, 0 }, false);
		//			this.ndock = null;
		//		} else {
		//			//不使用高速修复,后接ndock ,无需处理
		//		}
	}

	@Override
	public NdockNyukyoStart getNdockNyukyoStart() {
		return this.ndockNyukyoStart;
	}
}
