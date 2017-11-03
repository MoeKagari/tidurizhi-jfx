package javafx;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * Demonstrates bi-directional text.
 */
public class BidiApp extends Application {
	Text text1;
	Text text2;

	public Parent createContent() {
		Font font = new Font("Tahoma", 48);
		this.text1 = new Text("He said \u0627\u0644\u0633\u0644\u0627\u0645");
		this.text1.setFill(Color.RED);
		this.text1.setFont(font);
		this.text2 = new Text(" \u0639\u0644\u064a\u0643\u0645 to me.");
		this.text2.setFill(Color.BLUE);
		this.text2.setFont(font);
		return new Group(new TextFlow(this.text1, this.text2));
	}

	@Override

	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(new Scene(this.createContent()));
		primaryStage.show();
	}

	/**
	 * Java main for when running without JavaFX launcher
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
