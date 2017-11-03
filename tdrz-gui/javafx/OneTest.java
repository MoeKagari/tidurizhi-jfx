package javafx;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class OneTest extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

		Stage stage = new Stage();
		Group group = new Group();
		stage.setTitle("Example");
		stage.setScene(new Scene(group, 300, 275));
		stage.show();

		Button button0 = new Button("button0");
		Button button1 = new Button("button1");
		group.getChildren().addAll(button0, button1);

		//注意，高潮来了
		button0.layoutXProperty().bind(stage.widthProperty().multiply(2).divide(3));
		button0.layoutYProperty().bind(stage.heightProperty().multiply(2).divide(3));
		button0.prefWidthProperty().bind(stage.widthProperty().multiply(.1));

		button1.layoutXProperty().bind(stage.widthProperty().multiply(1).divide(3));
		button1.layoutYProperty().bind(stage.heightProperty().multiply(2).divide(3));
		button1.prefWidthProperty().bind(stage.widthProperty().multiply(.1));
	}

	public static void main(String[] args) {
		launch(args);
	}
}
