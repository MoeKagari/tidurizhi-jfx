package tdrz.update.data.memory.useitem;

public class MemoryUseItemLostUse extends MemoryUseItemLost {
	private static final long serialVersionUID = 1L;
	private final long time;

	public MemoryUseItemLostUse(long time) {
		this.time = time;
	}

	@Override
	public long getTime() {
		return this.time;
	}
}
