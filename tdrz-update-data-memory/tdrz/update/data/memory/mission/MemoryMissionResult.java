package tdrz.update.data.memory.mission;

import java.io.Serializable;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.json.JsonObject;

import org.apache.commons.lang3.ArrayUtils;

import tdrz.update.data.memory.MemoryMission;

public class MemoryMissionResult extends MemoryMission {
	private static final long serialVersionUID = 1L;
	private final long time;
	private final MemoryObjDeck deck;
	private final String missionAreaName, missionDetail, missionName, missionClearResult;
	private final int admiralGetExp, admiralExp;
	private final int[] oldMaterial, newMaterial;
	private final int[] shipGetExpArray;
	private final int[][] shipExpArrayArray;
	private final MissionResultItem[] missionResultItemArray;

	public MemoryMissionResult(
			long time,
			MemoryObjDeck deck,
			String api_maparea_name, String api_detail, String api_quest_name, int api_clear_result,
			int api_get_exp, int api_member_exp,
			int[] api_get_ship_exp, int[][] api_get_exp_lvup,
			int[] oldMaterial, int[] newMaterial,
			MissionResultItem[] missionResultItemArray
	/**/) {
		this.time = time;
		this.deck = deck;
		this.missionAreaName = api_maparea_name;
		this.missionDetail = api_detail;
		this.missionName = api_quest_name;
		switch (api_clear_result) {
			case 0:
				this.missionClearResult = "失败";
				break;
			case 1:
				this.missionClearResult = "成功";
				break;
			case 2:
				this.missionClearResult = "大成功";
				break;
			default:
				this.missionClearResult = Integer.toString(api_clear_result);
				break;
		}
		this.admiralGetExp = api_get_exp;
		this.admiralExp = api_member_exp;
		this.oldMaterial = ArrayUtils.clone(oldMaterial);
		this.newMaterial = ArrayUtils.clone(newMaterial);
		this.shipGetExpArray = ArrayUtils.clone(api_get_ship_exp);
		this.shipExpArrayArray = Stream.of(api_get_exp_lvup).map(shipExpArray -> IntStream.of(shipExpArray).toArray()).toArray(int[][]::new);
		this.missionResultItemArray = missionResultItemArray;
	}

	@Override
	public long getTime() {
		return this.time;
	}

	public MemoryObjDeck getDeck() {
		return this.deck;
	}

	public String getMissionAreaName() {
		return this.missionAreaName;
	}

	public String getMissionDetail() {
		return this.missionDetail;
	}

	public String getMissionName() {
		return this.missionName;
	}

	public String getMissionClearResult() {
		return this.missionClearResult;
	}

	public int getAdmiralGetExp() {
		return this.admiralGetExp;
	}

	public int getAdmiralExp() {
		return this.admiralExp;
	}

	public int[] getOldMaterial() {
		return this.oldMaterial;
	}

	public int[] getNewMaterial() {
		return this.newMaterial;
	}

	public int[] getShipGetExpArray() {
		return this.shipGetExpArray;
	}

	public int[][] getShipExpArrayArray() {
		return this.shipExpArrayArray;
	}

	public MissionResultItem[] getMissionResultItemArray() {
		return this.missionResultItemArray;
	}

	public static class MissionResultItem implements Serializable {
		private static final long serialVersionUID = 1L;
		private final int flag;
		private final int id;
		private final String name;
		private final int count;

		public MissionResultItem(JsonObject json, int flag) {
			this.flag = flag;
			this.id = json.getInt("api_useitem_id");
			this.name = json.getString("api_useitem_name", null);
			this.count = json.getInt("api_useitem_count");
		}

		public String getName() {
			switch (this.flag) {
				case 1:
					return "高速修复材";
				case 2:
					return "高速建造材";
				case 3:
					return "开发资材";
				case 4:
					return this.name;
			}
			return "[" + this.flag + "," + this.id + "," + this.name + "]";
		}

		public int getCount() {
			return this.count;
		}
	}
}
