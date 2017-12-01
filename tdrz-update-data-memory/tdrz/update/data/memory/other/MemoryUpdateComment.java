package tdrz.update.data.memory.other;

import tdrz.update.data.memory.MemoryOther;

public class MemoryUpdateComment extends MemoryOther {
	private static final long serialVersionUID = 1L;
	private final long time;
	private final String oldComment;
	private final String newComment;

	public MemoryUpdateComment(long time, String oldComment, String newComment) {
		this.time = time;
		this.oldComment = oldComment;
		this.newComment = newComment;
	}

	@Override
	public long getTime() {
		return this.time;
	}

	public String getOldComment() {
		return this.oldComment;
	}

	public String getNewComment() {
		return this.newComment;
	}
}
