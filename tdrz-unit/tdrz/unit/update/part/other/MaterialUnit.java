package tdrz.unit.update.part.other;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

import tdrz.unit.update.Unit;
import tdrz.unit.update.UnitManager;
import tdrz.unit.update.dto.word.MaterialDto;
import tdrz.unit.update.part.other.MaterialUnit.MaterialUnitHandler;
import tool.function.FunctionUtils;

public class MaterialUnit extends Unit<MaterialUnitHandler> {
	private final MaterialHolder currentMaterial = new MaterialHolder();

	@Override
	public void accept(UnitManager unitManager, MaterialUnitHandler unitHandler) {
		FunctionUtils.notNull(unitHandler.getMaterialChangeHodler(), this.currentMaterial::change);
	}

	public static interface MaterialUnitHandler {
		public default MaterialChangeHodler getMaterialChangeHodler() {
			return null;
		}
	}

	public static class MaterialHolder {
		private MaterialDto material = null;

		public MaterialDto getMaterial() {
			return this.material;
		}

		@SuppressWarnings("unused")
		private void change(MaterialChangeHodler mch) {
			long time = mch.time;
			String event = mch.event;
			int[] mc = mch.mc;
			MaterialChangeHodler.MaterialChangeOption mco = mch.mco;

			int[] resource;
			if (mco == MaterialChangeHodler.MaterialChangeOption.UPDATE) {
				if (this.material != null && Arrays.equals(this.material.getMaterial(), mc)) {
					return;
				}
				resource = ArrayUtils.addAll(mc);
			} else {
				if (this.material == null) {
					return;
				}
				if (mco == MaterialChangeHodler.MaterialChangeOption.UPDATE_MAIN) {
					resource = ArrayUtils.addAll(mc, Arrays.copyOfRange(this.material.getMaterial(), 4, 8));
				} else {
					if (mco.onlyMain) {
						resource = ArrayUtils.addAll(mc, 0, 0, 0, 0);
					} else {
						resource = ArrayUtils.addAll(mc);
					}
					for (int index = 0; index < resource.length; index++) {
						resource[index] = this.material.getMaterial()[index] + (mco.isIncrease ? 1 : -1) * resource[index];
					}
				}
			}

			this.material = new MaterialDto(resource);
		}
	}

	public static class MaterialChangeHodler {
		private final long time;
		private final String event;
		private final int[] mc;
		private final MaterialChangeOption mco;

		public MaterialChangeHodler(long time, String event, int[] mc, MaterialChangeOption mco) {
			this.time = time;
			this.event = event;
			this.mc = mc;
			this.mco = mco;
		}

		public static enum MaterialChangeOption {
			UPDATE(true, false, false),
			UPDATE_MAIN(true, true, false),
			INCREASE(false, false, true),
			INCREASE_MAIN(false, true, true),
			DECREASE(false, false, false),
			DECREASE_MAIN(false, true, false);
			public final boolean isUpdate;
			public final boolean onlyMain;
			public final boolean isIncrease;

			private MaterialChangeOption(boolean isUpdate, boolean onlyMain, boolean isIncrease) {
				this.isUpdate = isUpdate;
				this.onlyMain = onlyMain;
				this.isIncrease = isIncrease;
			}
		}
	}
}
