package tdrz.update.unit;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import tdrz.update.UnitManager.Unit;
import tdrz.update.unit.UnitPrint.UnitHandlerPrint;
import tool.function.FunctionUtils;

public class UnitPrint extends Unit<UnitHandlerPrint> {
	private final List<Printer> printers = new ArrayList<>();

	public void addPrinter(Printer printer) {
		FunctionUtils.ifNotConsumer(printer, this.printers::contains, this.printers::add);
	}

	@Override
	public void accept(UnitHandlerPrint unitHandler) {
		List<PrintItem> printItems = new LinkedList<>(unitHandler.getPrintItems());
		FunctionUtils.notNull(unitHandler.getPrintItem(), printItems::add);
		this.printers.forEach(printer -> printer.print(printItems));
	}

	public static interface UnitHandlerPrint {
		/** 单个 */
		public default PrintItem getPrintItem() {
			return null;
		}

		/** 多个 */
		public default List<PrintItem> getPrintItems() {
			return FunctionUtils.emptyList();
		}
	}

	public static interface Printer {
		public void print(List<? extends PrintItem> items);
	}

	public static class PrintItem {}
}
