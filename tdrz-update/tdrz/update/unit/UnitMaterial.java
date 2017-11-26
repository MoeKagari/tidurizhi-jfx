package tdrz.update.unit;

import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.lang3.ArrayUtils;

import tdrz.update.UnitManager.Unit;
import tdrz.update.data.word.WordMaterial;
import tdrz.update.unit.UnitMaterial.UnitHandlerMaterial;

public class UnitMaterial extends Unit<UnitHandlerMaterial> {
	private WordMaterial currentMaterial = null;

	public WordMaterial getCurrentMaterial() {
		return this.currentMaterial;
	}

	@Override
	public void accept(UnitHandlerMaterial unitHandler) {
		this.currentMaterial = Optional.ofNullable(unitHandler.getNewMaterial()).map(WordMaterial::new).orElse(this.currentMaterial);
	}

	public static interface UnitHandlerMaterial {
		public default int[] getNewMaterial() {
			return null;
		}
	}

	public static enum MaterialChangeOption {
		UPDATE(true, false, false),//全部更新
		UPDATE_MAIN(true, true, false),//只更新前四种
		INCREASE(false, false, true),//增加
		INCREASE_MAIN(false, true, true),//增加前四种
		DECREASE(false, false, false),//减少
		DECREASE_MAIN(false, true, false);//减少前四种
		public final boolean isUpdate;
		public final boolean onlyMain;
		public final boolean isIncrease;

		private MaterialChangeOption(boolean isUpdate, boolean onlyMain, boolean isIncrease) {
			this.isUpdate = isUpdate;
			this.onlyMain = onlyMain;
			this.isIncrease = isIncrease;
		}

		public static int[] getNewMaterial(int[] oldMaterial, int[] materialChange, MaterialChangeOption option) {
			if (option == UnitMaterial.MaterialChangeOption.UPDATE) {
				if (Arrays.equals(oldMaterial, materialChange)) {
					return null;
				} else {
					return ArrayUtils.clone(materialChange);
				}
			} else {
				int[] newMaterial;
				if (option == UnitMaterial.MaterialChangeOption.UPDATE_MAIN) {
					newMaterial = ArrayUtils.addAll(materialChange, Arrays.copyOfRange(oldMaterial, 4, 8));
				} else {
					if (option.onlyMain) {
						newMaterial = ArrayUtils.addAll(materialChange, 0, 0, 0, 0);
					} else {
						newMaterial = ArrayUtils.clone(materialChange);
					}
					for (int index = 0; index < newMaterial.length; index++) {
						newMaterial[index] = oldMaterial[index] + (option.isIncrease ? 1 : -1) * newMaterial[index];
					}
				}
				return newMaterial;
			}
		}
	}
}
