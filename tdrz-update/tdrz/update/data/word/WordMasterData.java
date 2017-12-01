package tdrz.update.data.word;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.json.JsonObject;

import tdrz.update.data.AbstractWord;
import tool.JsonUtils;

public class WordMasterData extends AbstractWord {
	//@formatter:off
	private final JsonObject json;
	public WordMasterData(JsonObject json) {	this.json = json;}
	public JsonObject getJson() {return this.json;}
	//@formatter:on

	private Map<Integer, WordMasterShip> masterShipDataMap = null;
	private Map<Integer, WordMasterSlotitem> masterSlotitemDataMap = null;
	private Map<Integer, WordMasterMission> masterMissionDataMap = null;
	private Map<Integer, WordMasterUserItem> masterUserItemDtoMap = null;

	public Map<Integer, WordMasterShip> getMasterShipDataMap() {
		if (this.masterShipDataMap == null) {
			this.masterShipDataMap = this.json.getJsonArray("api_mst_ship").getValuesAs(JsonObject.class).stream()
					.map(WordMasterShip::new)
					.collect(Collectors.toMap(WordMasterShip::getId, Function.identity()));
		}
		return this.masterShipDataMap;
	}

	public Map<Integer, WordMasterSlotitem> getMasterSlotitemDataMap() {
		if (this.masterSlotitemDataMap == null) {
			this.masterSlotitemDataMap = this.json.getJsonArray("api_mst_slotitem").getValuesAs(JsonObject.class).stream()
					.map(WordMasterSlotitem::new)
					.collect(Collectors.toMap(WordMasterSlotitem::getId, Function.identity()));
		}
		return this.masterSlotitemDataMap;
	}

	public Map<Integer, WordMasterMission> getMasterMissionDataMap() {
		if (this.masterMissionDataMap == null) {
			this.masterMissionDataMap = this.json.getJsonArray("api_mst_mission").getValuesAs(JsonObject.class).stream()
					.map(WordMasterMission::new)
					.collect(Collectors.toMap(WordMasterMission::getId, Function.identity()));
		}
		return this.masterMissionDataMap;
	}

	public Map<Integer, WordMasterUserItem> getMasterUserItemDtoMap() {
		if (this.masterUserItemDtoMap == null) {
			this.masterUserItemDtoMap = this.json.getJsonArray("api_mst_useitem").getValuesAs(JsonObject.class).stream()
					.map(WordMasterUserItem::new)
					.collect(Collectors.toMap(WordMasterUserItem::getId, Function.identity()));
		}
		return this.masterUserItemDtoMap;
	}

	public class WordMasterShip {
		private final JsonObject json;

		public WordMasterShip(JsonObject json) {
			this.json = json;
		}

		public int getId() {
			return this.json.getInt("api_id");
		}

		public String getName() {
			return this.json.getString("api_name");
		}

		public int getGaizhaoLv() {
			return this.json.getInt("api_afterlv");
		}

		public int getGaizhaoAfterId() {
			return Integer.parseInt(this.json.getString("api_aftershipid"));
		}

		/** 改造所需钢材 */
		public int getGaizhaoFuel() {
			return this.json.getInt("api_afterfuel");
		}

		/** 改造所需弹药 */
		public int getGaizhaoBull() {
			return this.json.getInt("api_afterbull");
		}

		public int[] getTaik() {
			return JsonUtils.getIntArray(this.json, "api_taik");
		}

		public int[] getSouk() {
			return JsonUtils.getIntArray(this.json, "api_souk");
		}

		public int[] getHoug() {
			return JsonUtils.getIntArray(this.json, "api_houg");
		}

		public int[] getRaig() {
			return JsonUtils.getIntArray(this.json, "api_raig");
		}

		public int[] getTyku() {
			return JsonUtils.getIntArray(this.json, "api_tyku");
		}

		public int[] getLuck() {
			return JsonUtils.getIntArray(this.json, "api_luck");
		}

		public int[] getMaxeq() {
			return JsonUtils.getIntArray(this.json, "api_maxeq");
		}

		public int getFuelMax() {
			return this.json.getInt("api_fuel_max");
		}

		public int getBullMax() {
			return this.json.getInt("api_bull_max");
		}

		public int[] getOnslotMax() {
			return JsonUtils.getIntArray(this.json, "api_maxeq");
		}

		public int getType() {
			return this.json.getInt("api_stype");
		}
	}

	public class WordMasterSlotitem {
		private final JsonObject json;

		public WordMasterSlotitem(JsonObject json) {
			this.json = json;
		}

		public int getId() {
			return this.json.getInt("api_id");
		}

		public String getName() {
			return this.json.getString("api_name");
		}

		/** 对潜 */
		public int getTaisen() {
			return this.json.getInt("api_tais");
		}

		/** 对空 */
		public int getTyku() {
			return this.json.getInt("api_tyku");
		}

		/** 索敌 */
		public int getSaku() {
			return this.json.getInt("api_saku");
		}

		/** 五个值 */
		public int[] getType() {
			return JsonUtils.getIntArray(this.json, "api_type");
		}
	}

	public class WordMasterMission {
		private final JsonObject json;

		public WordMasterMission(JsonObject json) {
			this.json = json;
		}

		public int getId() {
			return this.json.getInt("api_id");
		}

		public String getName() {
			return this.json.getString("api_name");
		}
	}

	public class WordMasterUserItem {
		private final JsonObject json;

		public WordMasterUserItem(JsonObject json) {
			this.json = json;
		}

		private int getId() {
			return this.json.getInt("api_id");
		}

		public String getName() {
			return this.json.getString("api_name");
		}

		/** 长度为2 */
		public String[] getDescription() {
			return JsonUtils.getStringArray(this.json, "api_description");
		}
	}
}
