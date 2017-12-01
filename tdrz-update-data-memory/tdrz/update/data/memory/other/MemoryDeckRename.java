package tdrz.update.data.memory.other;

import tdrz.update.data.memory.MemoryOther;

public class MemoryDeckRename extends MemoryOther {
	private static final long serialVersionUID = 1L;
	private final long time;
	private final MemoryObjDeck renamedDeck;
	private final String oldName, newName;

	public MemoryDeckRename(long time, MemoryObjDeck renamedDeck, String oldName, String newName) {
		this.time = time;
		this.renamedDeck = renamedDeck;
		this.oldName = oldName;
		this.newName = newName;
	}

	@Override
	public long getTime() {
		return this.time;
	}

	public MemoryObjDeck getRenamedDeck() {
		return this.renamedDeck;
	}

	public String getOldName() {
		return this.oldName;
	}

	public String getNewName() {
		return this.newName;
	}
}
