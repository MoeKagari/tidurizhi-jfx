package javafx;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * A sample showing horizontal and vertical scroll bars.
 */
public class ScrollBarApp extends Application {
	private Circle circle;
	private ScrollBar xscrollBar;
	private ScrollBar yscrollBar;
	private double xscrollValue = 0;
	private double yscrollValue = 15;
	private static final int xBarWidth = 393;
	private static final int xBarHeight = 15;
	private static final int yBarWidth = 15;
	private static final int yBarHeight = 393;
	private static final int circleRadius = 90;

	public Parent createContent() {
		Rectangle bg = new Rectangle(xBarWidth + yBarWidth, xBarHeight + yBarHeight, Color.rgb(90, 90, 90));
		Rectangle box = new Rectangle(100, 100, Color.rgb(150, 150, 150));
		box.setTranslateX(147);
		box.setTranslateY(147);

		// create moveable circle
		this.circle = new Circle(45, 45, circleRadius, Color.rgb(90, 210, 210));
		this.circle.setOpacity(0.4);
		this.circle.relocate(0, 15);

		this.xscrollBar = this.horizontalScrollBar();
		this.xscrollBar.setUnitIncrement(20.0);
		this.xscrollBar.valueProperty().addListener((observable, oldValue, newValue) -> {
			// changes the x position of the circle
			this.setScrollValueX(this.xscrollBar.getValue(), this.circle);
		});

		this.yscrollBar = this.verticalScrollBar();
		this.yscrollBar.setUnitIncrement(20.0);
		this.yscrollBar.valueProperty().addListener((observable, oldValue, newValue) -> {
			// changes the y position of the circle
			this.setScrollValueY(this.yscrollBar.getValue(), this.circle);
		});

		//shift position of vertical scrollbar to right side of scene
		this.yscrollBar.setTranslateX(yBarHeight);
		this.yscrollBar.setTranslateY(yBarWidth);
		this.yscrollBar.setOrientation(Orientation.VERTICAL);

		Group group = new Group();
		group.getChildren().addAll(bg, box, this.circle, this.xscrollBar, this.yscrollBar);
		return group;
	}

	// Create a ScrollBar with given parameters
	private ScrollBar horizontalScrollBar() {
		final ScrollBar scrollBar = new ScrollBar();
		scrollBar.setMinSize(-1, -1);
		scrollBar.setPrefSize(xBarWidth, xBarHeight);
		scrollBar.setMaxSize(xBarWidth, xBarHeight);
		scrollBar.setVisibleAmount(50);
		scrollBar.setMax(xBarWidth - (2 * circleRadius));
		return scrollBar;
	}

	// Create a ScrollBar with given parameters
	private ScrollBar verticalScrollBar() {
		final ScrollBar scrollBar = new ScrollBar();
		scrollBar.setMinSize(-1, -1);
		scrollBar.setPrefSize(yBarWidth, yBarHeight);
		scrollBar.setMaxSize(yBarWidth, yBarHeight);
		scrollBar.setVisibleAmount(50);
		scrollBar.setMax(yBarHeight - (2 * circleRadius));
		return scrollBar;
	}

	// Updates x values
	private void setScrollValueX(double v, Circle circle) {
		this.xscrollValue = v;
		circle.relocate(this.xscrollValue, this.yscrollValue);
	}

	// Updates x values
	private void setScrollValueY(double v, Circle circle) {
		this.yscrollValue = v + xBarHeight;
		circle.relocate(this.xscrollValue, this.yscrollValue);
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
