package tdrz.update.data.word;

import javax.json.JsonArray;
import javax.json.JsonObject;

import tdrz.update.data.AbstractWord;
import tool.JsonUtils;

/**
 * 舰队编成
 * 
 * @author MoeKagari
 */
public class WordDeck extends AbstractWord {
	private final long refreshTime;//此deck的刷新时间
	private String deckName;
	private int[] shipArray;
	private final DeckMission deckMission;

	public WordDeck(long time, JsonObject json) {
		this.refreshTime = time;
		this.deckName = json.getString("api_name");
		this.shipArray = JsonUtils.getIntArray(json, "api_ship");
		this.deckMission = new DeckMission(json.getJsonArray("api_mission"));
	}

	/*-------------------------------------------------------------------------------------------*/

	public void setShipArray(int[] shipArray) {
		this.shipArray = shipArray;
	}

	public void setDeckName(String name) {
		this.deckName = name;
	}

	/*-------------------------------------------------------------------------------------------*/

	public int[] getShipArray() {
		return this.shipArray;
	}

	public long getRefreshTime() {
		return this.refreshTime;
	}

	public String getDeckName() {
		return this.deckName;
	}

	public DeckMission getDeckMission() {
		return this.deckMission;
	}

	/*-------------------------------------------------------------------------------------------*/

	/** 舰队远征信息 */
	public class DeckMission {
		private final int state;
		private final long time;
		private final String name = "";
		//private final TimerCounter timerCounter;

		public DeckMission(JsonArray json) {
			this.state = json.getJsonNumber(0).intValue();
			//this.name = MasterDataTranslator.getMissionName(json.getJsonNumber(1).intValue());
			this.time = json.getJsonNumber(2).longValue();
			//this.timerCounter = new TimerCounter(this.time, 60, true, 2 * 60);
		}

		/** 远征名 */
		public String getName() {
			return this.name;
		}

		/** 0=未出撃, 1=遠征中, 2=遠征帰投, 3=強制帰投中 */
		public int getState() {
			return this.state;
		}

		/** 归还时间 */
		public long getTime() {
			return this.time;
		}

		//		public TimerCounter getTimerCounter() {
		//			return this.timerCounter;
		//		}
	}
}
