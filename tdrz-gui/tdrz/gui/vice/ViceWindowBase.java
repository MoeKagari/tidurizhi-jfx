package tdrz.gui.vice;

import javafx.stage.Stage;
import tdrz.update.unit.UnitManager;

public abstract class ViceWindowBase extends Stage {
	private final UnitManager unitManager;

	public ViceWindowBase(UnitManager unitManager, String title) {
		this.unitManager = unitManager;
		this.setTitle(title);

		this.widthProperty().addListener((source, oldValue, newValue) -> {
			this.setWidth(newValue.doubleValue());
		});
		this.heightProperty().addListener((source, oldValue, newValue) -> {
			this.setHeight(newValue.doubleValue());
		});
		this.xProperty().addListener((source, oldValue, newValue) -> {
			this.setX(newValue.doubleValue());
		});
		this.yProperty().addListener((source, oldValue, newValue) -> {
			this.setY(newValue.doubleValue());
		});
	}

	public UnitManager getUnitManager() {
		return this.unitManager;
	}
}
