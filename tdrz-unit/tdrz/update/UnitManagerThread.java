package tdrz.update;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.json.JsonObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tdrz.server.RawApiData;
import tdrz.server.RawApiDataType;

public class UnitManagerThread extends Thread {
	private final static Logger LOG = LogManager.getLogger(UnitManagerThread.class);
	private final static SimpleDateFormat JSONFILETIMEFORMAT = new SimpleDateFormat("yyMMdd_HHmmss.SSS");
	private final long time;
	private final String serverName, uri;
	private final Map<String, String> headers;
	private final ByteArrayOutputStream requestBody, responseBody;

	public UnitManagerThread(long time, String serverName, String uri, Map<String, String> headers, ByteArrayOutputStream requestBody, ByteArrayOutputStream responseBody) {
		this.time = time;
		this.serverName = serverName;
		this.uri = uri;
		this.headers = headers;
		this.requestBody = requestBody;
		this.responseBody = responseBody;
	}

	@Override
	public void run() {
		RawApiData rawApiData;
		try {
			rawApiData = new RawApiData(this.time, this.serverName, this.uri, this.headers, this.requestBody, this.responseBody);
		} catch (Exception e) {
			LOG.warn(this.uri + "\r\n" + this.requestBody + "\r\n" + this.responseBody, e);
			LOG.warn("\r\n");
			return;
		}

		RawApiDataType type = rawApiData.getType();
		if (type == null) {
			System.err.println("Ｘ定义的api : " + rawApiData.getUri());
			return;
		}
		System.out.println("Ｏ定义的api : " + rawApiData.getUri());

		JsonObject json = rawApiData.getJsonObject();
		int api_result = json.getInt("api_result");
		if (api_result != 1) {//有猫
			LOG.warn(String.format("%s,猫了,%d,api-%s", JSONFILETIMEFORMAT.format(rawApiData.getTime()), api_result, type));
			LOG.warn("\r\n");
			return;
		}

		try {
			UnitManager unitManager = UnitManager.getUnitManager();
			UnitHandler handler = UnitHandler.getUnitHandler(type).getUnitHandler(unitManager, rawApiData.getTime(), rawApiData.getFields(), json.get("api_data"));
			unitManager.acceptUnitHandler(handler);
		} catch (Throwable ex) {
			LOG.warn(rawApiData);
			LOG.warn(String.format("api-%s更新错误", type), ex);
			LOG.warn("\r\n");
		}
	}
}
