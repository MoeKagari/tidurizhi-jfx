package tdrz.update.data.memory.other;

import tdrz.update.data.memory.MemoryOther;

public class MemoryEventMapRankSelect extends MemoryOther {
	private static final long serialVersionUID = 1L;
	private final long time;
	private final int mapareaId, mapNo;
	private final String rank;

	public MemoryEventMapRankSelect(long time, int api_maparea_id, int api_map_no, int api_rank) {
		this.time = time;
		this.mapareaId = api_maparea_id;
		this.mapNo = api_map_no;
		switch (api_rank) {
			case 1:
				this.rank = "丙";
				break;
			case 2:
				this.rank = "乙";
				break;
			case 3:
				this.rank = "甲";
				break;
			default:
				this.rank = String.valueOf(api_rank);
				break;
		}
	}

	public int getMapareaId() {
		return this.mapareaId;
	}

	public int getMapNo() {
		return this.mapNo;
	}

	public String getRank() {
		return this.rank;
	}

	@Override
	public long getTime() {
		return this.time;
	}
}
