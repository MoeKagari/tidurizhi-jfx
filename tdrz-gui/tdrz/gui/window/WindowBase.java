package tdrz.gui.window;

import javafx.stage.Stage;

public abstract class WindowBase {
	public final Stage stage;

	public WindowBase() {
		this.stage = new Stage();
		this.stage.setOnCloseRequest(ev -> {
			ev.consume();
			this.stage.hide();
		});
	}
}
