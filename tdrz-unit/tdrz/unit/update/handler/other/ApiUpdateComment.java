package tdrz.unit.update.handler.other;

import java.util.Map;import tdrz.unit.update.UnitManager;

import javax.json.JsonValue;

import tdrz.unit.update.UnitHandler;

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
