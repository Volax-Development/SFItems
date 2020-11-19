package fr.ixion.sfitems.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import com.google.common.collect.Maps;

public class UtilRecipe {
	char[] c;
	Material[] m;
	String[] s;

	public UtilRecipe(HashMap<Integer, ItemStack> its) {
		String[] shape = { "012", "345", "678" };
		HashMap<Material, Integer> mats = new HashMap<Material, Integer>();
		for (int i = 0; i < 9; i++) {
			if (!its.containsKey(i)) {
				shape[i / 3] = shape[i / 3].replace(i + "", "");
				if (shape[i / 3].equals("")) {
					List<String> ar = Arrays.asList(shape).stream().map(String::toLowerCase)
							.collect(Collectors.toList());
					ar.remove(i / 3);
					shape = ar.toArray(new String[ar.size()]);
				}
			} else {
				ItemStack itemstack = its.get(i);
				if (!mats.containsKey(itemstack.getType())) {
					mats.put(itemstack.getType(), i);
				} else {
					shape[i / 3] = shape[i / 3].replace(i + "", mats.get(itemstack.getType()) + "");
				}
			}
		}
		Integer[] ints = mats.values().toArray(new Integer[mats.size()]);
		char[] c = new char[mats.size()];
		for (int i = 0; i < ints.length; i++) {
			c[i] = (ints[i] + "").charAt(0);
		}
		init(c, mats.keySet().toArray(new Material[mats.size()]), shape);
	}

	public void init(char[] chars, Material[] mat, String[] shapes) {
		this.c = chars;
		this.m = mat;
		this.s = shapes;
	}

	public ShapedRecipe generateRecipe(ItemStack result) {
		ShapedRecipe recipe = new ShapedRecipe(result);
		recipe.shape(s);
		if (this.m.length == this.c.length) {
			for (int i = 0; i < this.c.length; i++) {
				boolean done = false;
				byte b;
				int j;
				String[] arrayOfString;
				for (j = (arrayOfString = this.s).length, b = 0; b < j;) {
					String shape = arrayOfString[b];
					if (shape.contains((new StringBuilder(String.valueOf(this.c[i]))).toString()) && !done) {
						done = true;
						recipe.setIngredient(this.c[i], this.m[i]);
					}
					b++;
				}

			}
		} else {
			return null;
		}
//		System.out.println(recipe.getShape());
//		System.out.println(convertWithIteration(recipe.getIngredientMap()));
//		for (int i = 0; i < s.length; i++) {
//			String message = "";
//			char[] c = s[i].toCharArray();
//			for (int j = 0; j < c.length; j++) {
//				if (j != 0) {
//					message += ",";
//				}
//				message += recipe.getIngredientMap().get(c[j]).getType();
//
//			}
//			System.out.println(message);
//		}
		return recipe;
	}


	public ShapedRecipe generateRecipe(ItemStack result, int amount) {
		result.setAmount(amount);
		return generateRecipe(result);
	}
}