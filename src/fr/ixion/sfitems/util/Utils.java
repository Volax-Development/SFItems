package fr.ixion.sfitems.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class Utils {

	public static int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	public static boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isFloat(String str) {
		try {
			Float.parseFloat(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static String getSecondStringVersion(int t) {
		int mins = (t / 60);
		int sec = t % 60;
		return (mins < 10 ? "0" : "") + mins + ":" + (sec < 10 ? "0" : "") + sec;
	}

	public static ItemStack[] restoreContent(File f) {
		YamlConfiguration c = YamlConfiguration.loadConfiguration(f);

		ItemStack[] content = new ItemStack[54];

		for (int i = 0; i < 54; i++) {
			if (c.isItemStack("items." + i + "")) {
				content[i] = c.getItemStack("items." + i + "");
			}
		}
		return content;
	}

	public static int[] restoreCost(File f) {
		YamlConfiguration c = YamlConfiguration.loadConfiguration(f);

		int[] content = new int[58];

		for (int i = 0; i < 54; i++) {
			if (c.isInt("cost." + i + "")) {
				content[i] = c.getInt("cost." + i + "");
			}
		}
		return content;
	}

//	public static void saveShop(W s, String path) throws IOException {
//		YamlConfiguration c = new YamlConfiguration();
//		int i = 0;
//		for (ItemStack itemStack : s.getItems()) {
//			c.set("items." + i + "", itemStack);
//			i++;
//		}
//		i = 0;
//
//		for (int j = 0; j < 58; j++) {
//			c.set("cost." + i + "", s.getCosts()[j]);
//			i++;
//		}
//		c.save(new File(path + ".yml"));
//	}

	public static void saveInventory(Inventory inv, String path) throws IOException {
		YamlConfiguration c = new YamlConfiguration();
		int i = 0;
		for (ItemStack itemStack : inv) {
			c.set("items." + i + "", itemStack);
			i++;
		}
		c.save(new File(path + ".yml"));
	}

	public static void saveContent(ItemStack[] inv, String path) throws IOException {
		YamlConfiguration c = new YamlConfiguration();
		int i = 0;
		for (ItemStack itemStack : inv) {
			c.set("items." + i + "", itemStack);
			i++;
		}
		c.save(new File(path + ".yml"));
	}

	public static void saveContent(ItemStack[] inv, File file) throws IOException {
		YamlConfiguration c = new YamlConfiguration();
		int i = 0;
		for (ItemStack itemStack : inv) {
			c.set("items." + i + "", itemStack);
			i++;
		}
		c.save(file);
	}

	public static void removeFile(File f) {
		if (f.exists()) {
			if (!f.isFile()) {
				for (File file : f.listFiles()) {
					removeFile(file);
				}
			} else {
				f.delete();
			}
		}
	}

	public static HashMap<Enchantment, Integer> generateEnchantements(Enchantment[] ench, int[] level) {
		HashMap<Enchantment, Integer> hash = new HashMap<Enchantment, Integer>();
		if (ench.length == level.length) {
			for (int i = 0; i < level.length; i++) {
				hash.put(ench[i], level[i]);
			}
		}
		return hash;
	}

	public static HashMap<Enchantment, Integer> generateEnchantements(String string) {
		HashMap<Enchantment, Integer> hash = new HashMap<Enchantment, Integer>();
		String[] arr1 = string.split(";");
		List<String[]> arr2 = new ArrayList<String[]>();
		for (String s : arr1) {
			arr2.add(s.split(":"));
		}
		for (String[] strings : arr2) {
			if (strings.length == 2 && Utils.isInteger(strings[0].replaceAll(" ", ""))
					&& Utils.isInteger(strings[1].replaceAll(" ", ""))) {
				hash.put(Enchantment.getById(Integer.parseInt(strings[0].replaceAll(" ", ""))),
						Integer.parseInt(strings[1].replaceAll(" ", "")));

			}
		}
		return hash;
	}

	public static ShapedRecipe generateRecipe(char[] chars, Material[] mat, String... shapes) {

		ShapedRecipe recipe = new ShapedRecipe(new ItemStack(Material.COBBLESTONE));
		switch (shapes.length) {
		case 1:
			recipe.shape(shapes[0]);
			break;
		case 2:
			recipe.shape(shapes[0], shapes[1]);
			break;
		case 3:
			recipe.shape(shapes[0], shapes[2], shapes[3]);
			break;
		default:
			return null;
		}
		if (mat.length == chars.length) {
			for (int i = 0; i < chars.length; i++) {
				recipe.setIngredient(chars[i], mat[i]);
			}
		} else {
			return null;
		}
		return recipe;
	}

	public static void saveLocationList(List<Location> locs, File file) throws IOException {
		YamlConfiguration c = new YamlConfiguration();
		c.set("locations", locs);
		c.save(file);
	}

	public static List<Location> restoreLocations(File f) {

		YamlConfiguration c = YamlConfiguration.loadConfiguration(f);

		List<Location> loc = new ArrayList<Location>();
		if (!f.exists()) {
			return loc;
		}

		loc = (List<Location>) c.getList("locations", loc);
		return loc;
	}

	public static void saveMaterialList(List<Material> materials, File file) throws IOException {
		YamlConfiguration c = new YamlConfiguration();
		List<String> strings = new ArrayList<String>();
		for (Material material : materials) {
			strings.add(material.toString());
		}
		c.set("materials", strings);
		c.save(file);
	}

	public static List<Material> restoreMaterial(File f) {
		YamlConfiguration c = YamlConfiguration.loadConfiguration(f);
		List<String> loc = new ArrayList<String>();
		List<Material> mats = new ArrayList<Material>();
		if (!f.exists()) {
			return mats;
		}

		loc = (List<String>) c.getList("materials", loc);
		for (String string : loc) {
			try {
				mats.add(Material.valueOf(string));
			} catch (Exception e) {
			}
		}
		return mats;
	}

	public static HashMap<Location, String> restoreLocationsAndUUID(File f) {
		List<Location> loc = new ArrayList<Location>();
		List<String> ints = new ArrayList<String>();
		YamlConfiguration c = YamlConfiguration.loadConfiguration(f);
		HashMap<Location, String> locs = new HashMap<Location, String>();
		if (!f.exists()) {
			return locs;
		}

		loc = (List<Location>) c.getList("locations", loc);

		ints = (List<String>) c.getList("uuids", ints);
		for (int i = 0; i < loc.size(); i++) {
			locs.put(loc.get(i), ints.get(i));
		}
		return locs;
	}

	public static void saveLocationsAndUUID(HashMap<Location, String> locs, File file) throws IOException {
		YamlConfiguration c = new YamlConfiguration();
		c.set("locations", new ArrayList<>(locs.keySet()));
		c.set("uuids", new ArrayList<>(locs.values()));
		c.save(file);
	}

}
