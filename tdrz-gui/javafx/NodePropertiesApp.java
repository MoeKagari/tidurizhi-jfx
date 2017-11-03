package javafx;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * A sample that demonstrates some properties of nodes. Use the controls to
 * change opacity or horizontal position.
 */
public class NodePropertiesApp extends Application {
	private Rectangle rectA;
	private Rectangle rectB;
	private Rectangle rectC;

	public Parent createContent() {
		//X position of node = X + LayoutX + TranslateX
		this.rectA = new Rectangle(50, 50, Color.LIGHTSALMON);
		//set position of node temporary (can be changed after)
		this.rectA.setTranslateX(10);

		this.rectB = new Rectangle(50, 50, Color.LIGHTGREEN);
		//set position of node when addinf to some layout
		this.rectB.setLayoutX(20);
		this.rectB.setLayoutY(10);

		this.rectC = new Rectangle(50, 50, Color.DODGERBLUE);
		//last posibility of setting X position of node
		this.rectC.setX(30);
		this.rectC.setY(20);
		//opacity of node can be set
		this.rectC.setOpacity(0.8);

		Pane root = new Pane(this.rectA, this.rectB, this.rectC);
		root.setPrefSize(130, 100);
		root.setMinSize(130, 100);
		root.setMaxSize(130, 100);
		return root;
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
