package tdrz.update.data.memory.other;

import tdrz.update.data.memory.MemoryOther;

public class MemoryGetIncentive extends MemoryOther {
	private static final long serialVersionUID = 1L;
	private final long time;

	public MemoryGetIncentive(long time) {
		//TODO 暂时未处理
		this.time = time;
	}

	@Override
	public long getTime() {
		return this.time;
	}
}
