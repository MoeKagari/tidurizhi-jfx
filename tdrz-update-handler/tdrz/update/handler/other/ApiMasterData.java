package tdrz.update.handler.other;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import javax.json.JsonObject;
import javax.json.JsonValue;

import tdrz.update.UnitManager;
import tdrz.update.data.word.WordMasterData;
import tdrz.update.handler.UnitHandler;

public class ApiMasterData extends UnitHandler {
	private final WordMasterData masterData;

	public ApiMasterData(UnitManager unitManager,long time, Map<String, String> fields, JsonValue api_data) {
		this.masterData = new WordMasterData((JsonObject) api_data);
	}

	@Override
	public WordMasterData getMasterData() {
		return this.masterData;
	}

	public static void main(String[] args) {
		for (String name : new String[] {
				"PracticeList", "PracticeEnemyInfo", "PracticeChangeMatchingKind"
		}) {
			name = "Api" + name;

			StringBuilder sb = new StringBuilder();
			sb.append("package tdrz.unit.update.handler.practice;").append("\r\n");
			sb.append("import java.util.Map;import tdrz.unit.update.UnitManager;").append("\r\n");
			sb.append("import tdrz.unit.update.UnitHandler;").append("\r\n");
			sb.append("import javax.json.JsonValue;").append("\r\n");
			sb.append("public class " + name + " extends UnitHandler");
			sb.append('{').append("\r\n");
			{
				sb.append("public " + name + "(long time,  Map<String, String> fields, JsonValue api_data) {").append("\r\n");
				sb.append("\r\n");
				sb.append("}").append("\r\n");
			}
			sb.append('}');

			try {
				Files.write(new File(name + ".java").toPath(), sb.toString().getBytes("utf-8"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
