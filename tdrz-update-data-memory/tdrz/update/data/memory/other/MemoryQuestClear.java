package tdrz.update.data.memory.other;

import java.io.Serializable;

import javax.json.JsonArray;
import javax.json.JsonObject;

import org.apache.commons.lang3.ArrayUtils;

import tdrz.update.data.memory.MemoryOther;
import tdrz.update.data.word.WordQuest;

public class MemoryQuestClear extends MemoryOther {
	private static final long serialVersionUID = 1L;
	private final long time;
	private final QuestClearQuest questClearQuest;
	private final int[] oldMaterial, newMaterial;
	private final QuestClearBonus[] questClearBonusArray;

	public MemoryQuestClear(long time, WordQuest quest, int[] oldMaterial, int[] newMaterial, JsonArray api_bounus) {
		this.time = time;
		this.questClearQuest = new QuestClearQuest(quest);
		this.oldMaterial = ArrayUtils.clone(oldMaterial);
		this.newMaterial = ArrayUtils.clone(newMaterial);
		this.questClearBonusArray = api_bounus.getValuesAs(JsonObject.class).stream().map(QuestClearBonus::new).toArray(QuestClearBonus[]::new);
	}

	@Override
	public long getTime() {
		return this.time;
	}

	public QuestClearQuest getQuestClearQuest() {
		return this.questClearQuest;
	}

	public int[] getOldMaterial() {
		return this.oldMaterial;
	}

	public int[] getNewMaterial() {
		return this.newMaterial;
	}

	public QuestClearBonus[] getQuestClearBonusArray() {
		return this.questClearBonusArray;
	}

	public static class QuestClearQuest implements Serializable {
		private static final long serialVersionUID = 1L;
		private final int questNo;
		private final String questName, questDetail, questType, questCategory;

		public QuestClearQuest(WordQuest quest) {
			this.questNo = quest.getNo();
			this.questName = quest.getTitle();
			this.questDetail = quest.getDetail();
			this.questType = quest.getTypeString();
			this.questCategory = quest.getCategoryString();
		}

		public int getQuestNo() {
			return this.questNo;
		}

		public String getQuestName() {
			return this.questName;
		}

		public String getQuestDetail() {
			return this.questDetail;
		}

		public String getQuestType() {
			return this.questType;
		}

		public String getQuestCategory() {
			return this.questCategory;
		}
	}

	public static class QuestClearBonus implements Serializable {
		private static final long serialVersionUID = 1L;

		public QuestClearBonus(JsonObject json) {
			//TODO 任务奖励
		}
	}
}
