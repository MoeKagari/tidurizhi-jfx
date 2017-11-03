package tdrz.gui.window;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import tool.FXUtils;

public final class ApplicationMain {
	private final Stage primaryStage;

	public ApplicationMain(Stage stage) {
		this.primaryStage = stage;
		this.primaryStage.setScene(new Scene(new BorderPane(this.createContent(this.primaryStage), this.createMenuBar(this.primaryStage), null, null, null), 800, 600));
		FXUtils.addCloseConfirmationWindow(this.primaryStage, "退出", "是否退出提督日志?");
	}

	private MenuBar createMenuBar(Stage primaryStage) {
		return null;
	}

	private Region createContent(Stage primaryStage) {
		BorderPane borderPane = new BorderPane();

		return borderPane;
	}

	public static class MainStart extends Application {
		public static void main(String[] args) {
			launch(args);
		}

		@Override
		public void start(Stage primaryStage) throws Exception {
			ApplicationMain main = new ApplicationMain(primaryStage);
			main.primaryStage.show();
		}
	}
}
