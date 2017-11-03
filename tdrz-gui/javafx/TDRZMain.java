package javafx;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class TDRZMain extends Application {
	public static void main(String[] args) {
		//		System.setProperty("socksProxyHost", "127.0.0.1");
		//		System.setProperty("socksProxyPort", "1080");
		//
		//		URL.setURLStreamHandlerFactory((new URLStreamHandlerFactory() {
		//			@Override
		//			public URLStreamHandler createURLStreamHandler(String protocol) {
		//				switch (protocol) {
		//					case "http":
		//						return new URLStreamHandler() {
		//							@Override
		//							protected URLConnection openConnection(URL u) throws IOException {
		//								HttpURLConnection huc = new HttpURLConnection(u, new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8099)));
		//								return huc;
		//							}
		//						};
		//					default:
		//						return null;
		//				}
		//			}
		//		}));

		Application.launch(args);
	}

	private int gameWidth = 800, gameHeight = 480;

	private Node initTop() {
		WebView webView = new WebView();
		webView.setMinSize(this.gameWidth, this.gameHeight);
		webView.setMaxSize(this.gameWidth, this.gameHeight);
		{
			WebEngine webEngine = webView.getEngine();
			//webEngine.load("http://www.dmm.com/netgame/social/-/gadgets/=/app_id=854854/");
			webEngine.load("https://www.baidu.com/");
		}

		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);
		hbox.getChildren().add(webView);
		return hbox;
	}

	private Node initCenter() {
		TextArea text = new TextArea();
		text.setEditable(false);
		text.setWrapText(true);

		return text;
	}

	@Override
	public void start(Stage primaryStage) {
		BorderPane root = new BorderPane();
		root.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		root.setTop(this.initTop());
		root.setCenter(this.initCenter());

		primaryStage.setScene(new Scene(root, Color.BLACK));
		primaryStage.setTitle("提督日志");
		primaryStage.setMinWidth(this.gameWidth);
		primaryStage.setMinHeight(this.gameHeight);
		primaryStage.setWidth(this.gameWidth + 400);
		primaryStage.setHeight(this.gameHeight + 400);
		primaryStage.show();
	}
}
