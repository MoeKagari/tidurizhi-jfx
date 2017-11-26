package tdrz.update.data.word;

import java.util.Arrays;

import tdrz.update.data.AbstractWord;

/**
 * 八种资源
 * 
 * @author MoeKagari
 */
public class WordMaterial extends AbstractWord {
	private static final String[] TEXT = { "油", "弹", "钢", "铝", "高修建造材", "高速修复材", "开发资材", "螺丝" };
	private final int[] material;

	public WordMaterial(int[] material) {
		if (material == null) {
			throw new RuntimeException("参数不能为null");
		}
		if (material.length != 8) {
			throw new RuntimeException("参数长度应==8");
		}
		this.material = Arrays.copyOf(material, material.length);
	}

	public int[] getAmount() {
		return Arrays.copyOf(this.material, this.material.length);
	}

	public static String[] getMaterialText() {
		return Arrays.copyOf(TEXT, TEXT.length);
	}
}
