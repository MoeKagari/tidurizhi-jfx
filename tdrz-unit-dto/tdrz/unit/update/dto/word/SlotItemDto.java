package tdrz.unit.update.dto.word;

import java.io.Serializable;

import javax.json.JsonObject;

import tdrz.unit.update.dto.AbstractWord;

/**
 * 舰娘的装备
 * 
 * @author MoeKagari
 */
public class SlotItemDto extends AbstractWord implements Serializable {
	private static final long serialVersionUID = 1L;
	private final int id, slotitemId, level, alv;
	private boolean isLocked;
	//private transient MasterSlotitemDto msdd;

	public SlotItemDto(JsonObject json) {
		this.id = json.getInt("api_id");
		this.slotitemId = json.getInt("api_slotitem_id");
		this.isLocked = json.getInt("api_locked", -1) == 1;
		this.level = json.getInt("api_level", 0);
		this.alv = json.getInt("api_alv", -1);

		//this.msdd = MasterDataTranslator.getMasterSlotitemDto(this.slotitemId);
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

	//	public MasterSlotitemDto getMasterData() {
	//		if (this.msdd == null) {
	//			this.msdd = MasterDataTranslator.getMasterSlotitemDto(this.slotitemId);
	//		}
	//		return this.msdd;
	//	}

	/*-----------------------------------------------------------------------------------------*/

	public void slotItemLock(boolean lock) {
		this.isLocked = lock;
	}
}
