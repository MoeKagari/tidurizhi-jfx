package tdrz.update.handler.kaisou;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.data.AbstractMemory.MemoryObjShip;
import tdrz.update.data.memory.MemoryShip;
import tdrz.update.data.memory.ship.MemoryShipPowerUp;
import tdrz.update.data.memory.ship.MemoryShipPowerUp.ShipPowerUpShip;
import tdrz.update.data.word.WordShip;
import tdrz.update.handler.UnitHandler;
import tdrz.update.handler.deck.ApiDeck;
import tdrz.update.unit.UnitMemory.MemoryChange;
import tool.function.FunctionUtils;

public class ApiKaisouPowerUp extends UnitHandler {
	private final UnitHandler deck;
	private final WordShip newShip;
	private final List<Integer> removeShipList;
	private final MemoryShipPowerUp memoryShipPowerUp;

	public ApiKaisouPowerUp(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		JsonObject json = (JsonObject) api_data;

		boolean success = json.getInt("api_powerup_flag") == 1;
		WordShip oldship = unitManager.getShip(Integer.parseInt(fields.get("api_id")));
		this.newShip = new WordShip(json.getJsonObject("api_ship"));

		this.removeShipList = FunctionUtils.stream(fields.get("api_id_items").trim().split(",")).map(Integer::parseInt).collect(Collectors.toList());
		this.deck = new ApiDeck(unitManager, time, fields, json.get("api_deck"));
		this.memoryShipPowerUp = new MemoryShipPowerUp(
				time,
				new ShipPowerUpShip(oldship), new ShipPowerUpShip(this.newShip),
				success,
				this.removeShipList.stream().map(unitManager::getShip).map(ship -> new MemoryObjShip(ship, unitManager::getSlotItem)).toArray(MemoryObjShip[]::new)
		/**/);
	}

	@Override
	public MemoryChange<MemoryShip> getMemoryShipChange() {
		return new MemoryChange<MemoryShip>() {
			@Override
			public MemoryShip getMemoryChange() {
				return ApiKaisouPowerUp.this.memoryShipPowerUp;
			}
		};
	}

	@Override
	public List<WordShip> getAddShipList() {
		return FunctionUtils.asList(this.newShip);
	}

	@Override
	public List<Integer> getRemovedShipList() {
		return this.removeShipList;
	}

	@Override
	public List<UnitHandler> otherUnitHandler() {
		return FunctionUtils.asList(this.deck);
	}
}
