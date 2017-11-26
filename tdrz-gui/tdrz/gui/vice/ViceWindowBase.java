package tdrz.gui.vice;

import java.util.function.Consumer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import tdrz.gui.main.ApplicationMainTDRZJFX;
import tdrz.server.RawApiDataType;
import tdrz.update.UnitManager.AAAAAAAAA;
import tool.function.FunctionUtils;

public abstract class ViceWindowBase extends Stage implements AAAAAAAAA {
	private final ApplicationMainTDRZJFX main;

	public ViceWindowBase(ApplicationMainTDRZJFX main, String title) {
		this.main = main;
		this.setTitle(title);

		//解决 show -> (resize and relocation) -> hide -> show , 不会恢复 size 和 location
		//最大化 以及 关闭窗口 时也会 (resize and relocation) 所以不set
		this.widthProperty().addListener((source, oldValue, newValue) -> {
			if (FunctionUtils.isFalse(this.isMaximized() || this.isShowing())) {
				this.setWidth(newValue.doubleValue());
			}
		});
		this.heightProperty().addListener((source, oldValue, newValue) -> {
			if (FunctionUtils.isFalse(this.isMaximized() || this.isShowing())) {
				this.setHeight(newValue.doubleValue());
			}
		});
		this.xProperty().addListener((source, oldValue, newValue) -> {
			if (FunctionUtils.isFalse(this.isMaximized() || this.isShowing())) {
				this.setX(newValue.doubleValue());
			}
		});
		this.yProperty().addListener((source, oldValue, newValue) -> {
			if (FunctionUtils.isFalse(this.isMaximized() || this.isShowing())) {
				this.setY(newValue.doubleValue());
			}
		});
	}

	@Override
	public void update(RawApiDataType type) {}

	protected ApplicationMainTDRZJFX getApplicationMain() {
		return this.main;
	}

	protected class ControlEventHandler implements EventHandler<ActionEvent> {
		private final Consumer<ActionEvent> handler;

		public ControlEventHandler(Runnable handler) {
			this.handler = ev -> handler.run();
		}

		public ControlEventHandler(Consumer<ActionEvent> handler) {
			this.handler = handler;
		}

		@Override
		public void handle(ActionEvent event) {
			this.handler.accept(event);
		}
	}
}
