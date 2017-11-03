package javafx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

/**
 * A sample that demonstrates the HTML Editor. You can make changes to the
 * example text, and the resulting generated HTML is displayed.
 */
public class HTMLEditorApp extends Application {
	private final String INITIAL_TEXT = "<html><body>Lorem ipsum dolor sit "
			+ "amet, consectetur adipiscing elit."
			+ "Nam tortor felis, pulvinar in scelerisque cursus, pulvinar "
			+ "at ante. Nulla consequat "
			+ "congue lectus in sodales. </body></html> ";

	@Override
	public void start(Stage primaryStage) throws Exception {
		HTMLEditor htmlEditor = new HTMLEditor();
		htmlEditor.setHtmlText(this.INITIAL_TEXT);

		TextArea htmlContent = new TextArea();
		htmlContent.setEditable(false);
		htmlContent.setWrapText(true);
		htmlContent.setPrefHeight(100);

		Button showHTMLButton = new Button("Show the HTML below");
		showHTMLButton.setOnAction((ActionEvent arg0) -> {
			htmlContent.setText(htmlEditor.getHtmlText());
		});

		BorderPane bp = new BorderPane();
		bp.setCenter(htmlEditor);
		bp.setBottom(new VBox(showHTMLButton, htmlContent));

		primaryStage.setScene(new Scene(bp));
		primaryStage.show();
	}

	/**
	 * Java main for when running without JavaFX launcher
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
