package tdrz.update.part.other;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tdrz.update.Unit;
import tdrz.update.UnitManager;
import tdrz.update.data.word.WordUseItem;
import tdrz.update.part.other.UseItemUnit.UseItemUnitHandler;
import tool.function.FunctionUtils;

public class UseItemUnit extends Unit<UseItemUnitHandler> {
	public final Map<Integer, WordUseItem> useItemMap = new HashMap<>();

	@Override
	public void accept(UnitManager unitManager, UseItemUnitHandler unitHandler) {
		FunctionUtils.ifRunnable(unitHandler.needClearUseItemList(), this.useItemMap::clear);
		unitHandler.getAddUseItemList().forEach(useItem -> this.useItemMap.put(useItem.getId(), useItem));
		unitHandler.getRemoveUseItemList().forEach(this.useItemMap::remove);
	}

	public static interface UseItemUnitHandler {
		public default boolean needClearUseItemList() {
			return false;
		}

		public default List<WordUseItem> getAddUseItemList() {
			return Collections.emptyList();
		}

		public default List<Integer> getRemoveUseItemList() {
			return Collections.emptyList();
		}

	}
}
