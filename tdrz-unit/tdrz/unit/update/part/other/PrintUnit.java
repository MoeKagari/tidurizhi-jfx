package tdrz.unit.update.part.other;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javafx.scene.Node;
import tdrz.unit.update.Unit;
import tdrz.unit.update.UnitManager;
import tdrz.unit.update.part.other.PrintUnit.PrintUnitHandler;

public class PrintUnit extends Unit<PrintUnitHandler> {
	private final Set<Printer> printers = new TreeSet<>();

	public void addPrinter(Printer printer) {
		this.printers.add(printer);
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
