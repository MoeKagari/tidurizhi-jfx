package tdrz.update.data.memory.mission;

import tdrz.update.data.memory.MemoryMission;

public class MemoryMissionStart extends MemoryMission {
	private static final long serialVersionUID = 1L;

	private final MemoryObjDeck deck;
	private final String missionName;
	private final long returnTime;

	public MemoryMissionStart(MemoryObjDeck deck, String missionName, long returnTime) {
		this.deck = deck;
		this.missionName = missionName;
		this.returnTime = returnTime;
	}

	public MemoryObjDeck getDeck() {
		return this.deck;
	}

	public String getMissionName() {
		return this.missionName;
	}

	public long getReturnTime() {
		return this.returnTime;
	}
}
