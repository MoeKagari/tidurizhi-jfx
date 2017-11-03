package tdrz.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

import javax.json.Json;
import javax.json.JsonObject;

import tool.function.FunctionUtils;

public final class RawApiData {
	private final long time;
	private final String serverName;
	private final String uri;
	private final RawApiDataType type;
	private final Map<String, String> fields;
	private final JsonObject json;

	public RawApiData(long time, String serverName, String uri, Map<String, String> headers, ByteArrayOutputStream requestBody, ByteArrayOutputStream responseBody) throws Exception {
		this.time = time;
		this.serverName = serverName;
		this.uri = uri;
		this.type = RawApiDataType.getType(uri);

		try {
			this.fields = Arrays.stream(URLDecoder.decode(new String(requestBody.toByteArray()), "utf-8").trim().split("&"))//
					.map(param -> param.split("="))
					.filter(pair -> pair.length == 2)
					.filter(pair -> FunctionUtils.isFalse("api_token".equals(pair[0]) || "api_verno".equals(pair[0])))
					.collect(Collectors.toMap(pair -> pair[0], pair -> pair[1]));
		} catch (Exception e) {
			throw new Exception("fields解析出错", e);
		}

		try {
			InputStream stream = new ByteArrayInputStream(responseBody.toByteArray());
			if ("gzip".equalsIgnoreCase(headers.get("Content-Encoding"))) {
				stream = new GZIPInputStream(stream);
			}

			int b;
			while ((b = stream.read()) != -1) {
				if (b == '=') {
					break;
				}
			}

			this.json = Json.createReader(stream).readObject();
		} catch (Exception e) {
			throw new Exception("json解析出错\r\n" + new String(responseBody.toByteArray()), e);
		}
	}

	//	@Override
	//	public String toString() {
	//		return (this.type == null ? "null" : String.format("%s,%s", this.type, this.type.getDetail())) + "\r\n"//
	//				+ this.getUrl() + "\r\n"//
	//				+ new HashMap<>(this.fields) + "\r\n" //
	//				+ this.json;
	//	}

	public RawApiDataType getType() {
		return this.type;
	}

	public String getServerName() {
		return this.serverName;
	}

	public String getUri() {
		return this.uri;
	}

	public String getUrl() {
		return this.serverName + this.uri;
	}

	public long getTime() {
		return this.time;
	}

	public JsonObject getJsonObject() {
		return this.json;
	}

	public Map<String, String> getFields() {
		return this.fields;
	}
}
