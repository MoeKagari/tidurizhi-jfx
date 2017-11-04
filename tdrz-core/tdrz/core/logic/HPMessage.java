package tdrz.core.logic;

import javafx.scene.paint.Color;

public class HPMessage {
	public static final String ESCAPE_STRING = "退避";

	private static final Color RED = Color.rgb(255, 85, 17);//大破
	private static final Color GRAY = Color.GRAY;//中破
	private static final Color BROWN = Color.rgb(119, 102, 34);//击沉
	private static final Color CYAN = Color.CYAN;//小破
	private static final Color ESCAPE_COLOR = Color.DARKGRAY;//退避

	public static Color getColor(String state) {
		switch (state) {
			case "击沉":
				return BROWN;
			case "大破":
				return RED;
			case "中破":
				return GRAY;
			case "小破":
				return CYAN;
			case HPMessage.ESCAPE_STRING:
				return ESCAPE_COLOR;
			default:
				return null;
		}
	}

	public static String getString(double percent) {
		if (percent == 1.00) {
			return "完好";
		}

		if (percent < 1.00 && percent > 0.75) {
			return "擦伤";
		}

		if (percent <= 0.75 && percent > 0.50) {
			return "小破";
		}

		if (percent <= 0.50 && percent > 0.25) {
			return "中破";
		}

		if (percent <= 0.25 && percent > 0.00) {
			return "大破";
		}

		return "击沉";
	}
}
