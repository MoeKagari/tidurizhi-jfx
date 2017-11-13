package tdrz.server;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import server.CommunicationHandler;
import server.ProxyServerServlet;
import server.ServerConfig;
import tool.Downloader;
import tool.function.FunctionUtils;

@SuppressWarnings("serial")
public class TDRZServerSevlet extends ProxyServerServlet {
	private final static Logger LOG = LogManager.getLogger(TDRZServerSevlet.class);
	/** 游戏现有的服务器 */
	private final static List<String> GAME_SERVER_LIST = Arrays.asList(
			"125.6.184.16", "125.6.187.205", "125.6.187.229", "125.6.187.253",
			"125.6.188.25", "125.6.189.7", "125.6.189.39", "125.6.189.71",
			"125.6.189.103", "125.6.189.135", "125.6.189.167", "125.6.189.215",
			"125.6.189.247", "203.104.209.71", "203.104.209.87", "203.104.248.135",
			"203.104.209.23", "203.104.209.39", "203.104.209.55", "203.104.209.102" //
	);

	/** 一些关键文件不使用缓存 */
	private final static List<String> NOT_USE_CACHE = Arrays.asList(
			"/kcs/mainD2.swf",
			"/kcs/Core.swf",
			"/kcs/scenes/TitleMain.swf",
			"/kcs/resources/swf/font.swf",
			"/kcs/resources/swf/icons.swf",
			"/kcs/resources/swf/itemicons.swf",
			"/kcs/resources/swf/commonAssets.swf",
			"/kcs/resources/swf/sound_bgm.swf",
			"/kcs/resources/swf/sound_se.swf",
			"/kcs/PortMain.swf"//
	);

	private int port;
	private boolean useProxy;
	private String proxyHost;
	private int proxyPort;

	public TDRZServerSevlet(IntSupplier listenPort, BooleanSupplier useProxy, Supplier<String> proxyHost, IntSupplier proxyPort) {
		super(new ServerConfig(listenPort, useProxy, proxyHost, proxyPort));
	}

	@Override
	public void start() throws Exception {
		this.port = this.getConfig().getListenPort();
		this.useProxy = this.getConfig().isUseProxy();
		this.proxyHost = this.getConfig().getProxyHost();
		this.proxyPort = this.getConfig().getProxyPort();

		super.start();
	}

	public boolean isConfigChanged() {
		ServerConfig config = this.getConfig();
		if (this.port == config.getListenPort()) {
			if (this.useProxy == config.isUseProxy()) {
				if (this.useProxy == false) {
					return false;
				}
				if (this.proxyPort == config.getProxyPort() && StringUtils.equals(this.proxyHost, config.getProxyHost())) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void restart() throws Exception {
		if (FunctionUtils.isFalse(this.isConfigChanged())) {
			return;
		}

		this.port = this.getConfig().getListenPort();
		this.useProxy = this.getConfig().isUseProxy();
		this.proxyHost = this.getConfig().getProxyHost();
		this.proxyPort = this.getConfig().getProxyPort();

		super.restart();
	}

	@Override
	protected void service(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException, IOException {
		String serverName = httpRequest.getServerName();
		String uri = httpRequest.getRequestURI();

		byte[] bytes = null;
		if (GAME_SERVER_LIST.contains(serverName)) {
			if (uri != null && uri.startsWith("/kcs/")) {
				if (NOT_USE_CACHE.stream().noneMatch(str -> str.startsWith(uri))) {
					try {
						File cacheFile = new File("." + uri);

						if (cacheFile.exists()) {
							bytes = FileUtils.readFileToByteArray(cacheFile);
						} else {
							bytes = Downloader.download("http://" + serverName + uri, this.useProxy ? this.proxyHost : null, this.useProxy ? this.proxyPort : -1);
							FileUtils.writeByteArrayToFile(cacheFile, bytes);
						}
					} catch (Exception ex) {
						LOG.warn("缓存出错", ex);
					}
				}
			}
		}

		if (bytes != null) {
			try {
				httpResponse.getOutputStream().write(bytes);
			} catch (Exception ex) {
				httpResponse.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
			}
			return;
		}

		super.service(httpRequest, httpResponse);
	}

	@Override
	public CommunicationHandler getHandler(String serverName, String uri) {
		if (GAME_SERVER_LIST.contains(serverName)) {
			if (uri.startsWith("/kcsapi/")) {
				return new TDRZApiHandler(serverName, uri);
			}
		}

		return super.getHandler(serverName, uri);
	}
}
