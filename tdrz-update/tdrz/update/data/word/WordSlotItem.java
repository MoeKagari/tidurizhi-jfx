package tdrz.update.data.word;

import java.io.Serializable;

import javax.json.JsonObject;

import tdrz.core.translator.MasterDataTranslator;
import tdrz.update.data.AbstractWord;
import tdrz.update.data.word.WordMasterData.WordMasterSlotitem;

/**
 * 舰娘的装备
 * 
 * @author MoeKagari
 */
public class WordSlotItem extends AbstractWord implements Serializable {
	private static final long serialVersionUID = 1L;
	private final int id, slotitemId, level, alv;
	private boolean isLocked;
	private transient WordMasterSlotitem masterData;

	public WordSlotItem(JsonObject json) {
		this.id = json.getInt("api_id");
		this.slotitemId = json.getInt("api_slotitem_id");
		this.isLocked = json.getInt("api_locked", 0) == 1;
		this.level = json.getInt("api_level", 0);
		this.alv = json.getInt("api_alv", 0);

		this.masterData = MasterDataTranslator.getMasterSlotitem(this.slotitemId);
	}

	public int getSlotitemId() {
		return this.slotitemId;
	}

	public int getAlv() {
		return this.alv;
	}

	/** 获得的第几个装备 */
	public int getId() {
		return this.id;
	}

	public boolean isLocked() {
		return this.isLocked;
	}

	public int getLevel() {
		return this.level;
	}

	public WordMasterSlotitem getMasterData() {
		if (this.masterData == null) {
			this.masterData = MasterDataTranslator.getMasterSlotitem(this.slotitemId);
		}
		return this.masterData;
	}

	/*-----------------------------------------------------------------------------------------*/

	public void slotItemLock(boolean lock) {
		this.isLocked = lock;
	}
}
