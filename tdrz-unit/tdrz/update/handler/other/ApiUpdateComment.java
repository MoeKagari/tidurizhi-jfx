package tdrz.update.handler.other;

import java.util.Map;

import javax.json.JsonValue;

import tdrz.update.UnitHandler;
import tdrz.update.UnitManager;

public class ApiUpdateComment extends UnitHandler {
	private final String comment;

	public ApiUpdateComment(UnitManager unitManager,long time, Map<String, String> fields, JsonValue api_data) {
		this.comment = fields.get("api_cmt");
	}

	@Override
	public String getComment() {
		return this.comment;
	}
}
