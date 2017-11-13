package tdrz.update.unit.other;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.Node;
import tdrz.update.unit.UnitManager;
import tdrz.update.unit.UnitManager.Unit;
import tdrz.update.unit.other.PrintUnit.PrintUnitHandler;
import tool.function.FunctionUtils;

public class PrintUnit extends Unit<PrintUnitHandler> {
	private final List<Printer> printers = new ArrayList<>();

	public void addPrinter(Printer printer) {
		FunctionUtils.ifNotConsumer(printer, this.printers::contains, this.printers::add);
	}

	@Override
	public void accept(UnitManager unitManager, PrintUnitHandler unitHandler) {
		List<PrintItem> items = unitHandler.getPrintItem();
		this.printers.forEach(printer -> printer.print(items));
	}

	public static interface PrintUnitHandler {
		public default List<PrintItem> getPrintItem() {
			return Collections.emptyList();
		}
	}

	public static interface Printer {
		public void print(List<? extends PrintItem> items);
	}

	public static class PrintItem {
		private final long time;
		private final Node item;

		public PrintItem(long time, Node item) {
			this.time = time;
			this.item = item;
		}

		public long getTime() {
			return this.time;
		}

		public Node getItem() {
			return this.item;
		}
	}
}
