package tdrz.update.handler.other;

import java.util.Map;
import java.util.Optional;

import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.data.memory.MemoryOther;
import tdrz.update.data.memory.other.MemoryUpdateComment;
import tdrz.update.data.word.WordRecord;
import tdrz.update.handler.UnitHandler;
import tdrz.update.unit.UnitMemory.MemoryChange;

public class ApiOtherUpdateComment extends UnitHandler {
	private final String newComment;
	private final MemoryUpdateComment memoryUpdateComment;

	public ApiOtherUpdateComment(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data) {
		this.newComment = fields.get("api_cmt");
		this.memoryUpdateComment = Optional.ofNullable(unitManager.getRecord())
				.map(WordRecord::getComment)
				.map(oldComment -> new MemoryUpdateComment(time, oldComment, this.newComment))
				.orElse(null);
	}

	@Override
	public MemoryChange<MemoryOther> getMemoryOtherChange() {
		return new MemoryChange<MemoryOther>() {
			@Override
			public MemoryOther getMemoryChange() {
				return ApiOtherUpdateComment.this.memoryUpdateComment;
			}
		};
	}

	@Override
	public String getUpdateComment() {
		return this.newComment;
	}
}
