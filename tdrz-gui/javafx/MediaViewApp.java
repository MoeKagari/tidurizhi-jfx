package javafx;

import java.awt.Toolkit;
import java.io.File;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MediaViewApp extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Media media = new Media(new File("01.mp4").toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		MediaView mediaView = new MediaView(mediaPlayer);
		mediaView.setOnMouseClicked(ev -> {
			System.out.println(ev.getClickCount());
			if (ev.getButton() == MouseButton.PRIMARY) {
				int count = ev.getClickCount();

				if (count == 1) {
					switch (mediaPlayer.getStatus()) {
						case PLAYING:
							mediaPlayer.pause();
							break;
						case READY:
						case STALLED:
						case STOPPED:
						case UNKNOWN:
						case DISPOSED:
						case HALTED:
						case PAUSED:
						default:
							mediaPlayer.play();
							break;
					}
					return;
				} else if (count == 2) {
					primaryStage.setFullScreen(!primaryStage.isFullScreen());
					return;
				}
			}
		});

		VBox root = new VBox(mediaView) {
			@Override
			protected void layoutChildren() {
				mediaView.setFitWidth(this.getWidth());
				mediaView.setFitHeight(this.getHeight());
				super.layoutChildren();
			}
		};
		root.setAlignment(Pos.CENTER);

		primaryStage.setScene(new Scene(root, Color.BLACK));
		primaryStage.setTitle("提督日志");
		primaryStage.setMaximized(true);
		primaryStage.setX((Toolkit.getDefaultToolkit().getScreenSize().getWidth() - primaryStage.getWidth()) / 2);
		primaryStage.setY((Toolkit.getDefaultToolkit().getScreenSize().getHeight() - primaryStage.getHeight()) / 2);
		primaryStage.show();
	}
}
