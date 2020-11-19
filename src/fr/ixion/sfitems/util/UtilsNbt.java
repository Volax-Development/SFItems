package fr.ixion.sfitems.util;

import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class UtilsNbt {
	public static boolean hasNbt(ItemStack item, String key) {
		net.minecraft.server.v1_8_R3.ItemStack i = CraftItemStack.asNMSCopy(item);
		try {
			if (i.hasTag() && i.getTag() != null) {
				return i.getTag().hasKey(key);
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	public static ItemStack setString(ItemStack item, String key, String value) {
		NBTTagCompound tag;
		net.minecraft.server.v1_8_R3.ItemStack i = CraftItemStack.asNMSCopy(item);

		if (i.hasTag() && i.getTag() != null) {
			tag = i.getTag();
		} else {
			tag = new NBTTagCompound();
		}
		tag.setString(key, value);
		i.setTag(tag);
		return CraftItemStack.asBukkitCopy(i);
	}

	public static ItemStack setInt(ItemStack item, String key, int value) {
		NBTTagCompound tag;
		net.minecraft.server.v1_8_R3.ItemStack i = CraftItemStack.asNMSCopy(item);

		if (i.hasTag() && i.getTag() != null) {
			tag = i.getTag();
		} else {
			tag = new NBTTagCompound();
		}
		tag.setInt(key, value);
		i.setTag(tag);
		return CraftItemStack.asBukkitCopy(i);
	}

	public static ItemStack setBoolean(ItemStack item, String key, boolean value) {
		NBTTagCompound tag;
		net.minecraft.server.v1_8_R3.ItemStack i = CraftItemStack.asNMSCopy(item);

		if (i.hasTag() && i.getTag() != null) {
			tag = i.getTag();
		} else {
			tag = new NBTTagCompound();
		}
		tag.setBoolean(key, value);
		i.setTag(tag);
		return CraftItemStack.asBukkitCopy(i);
	}

	public static ItemStack setDouble(ItemStack item, String key, double value) {
		NBTTagCompound tag;
		net.minecraft.server.v1_8_R3.ItemStack i = CraftItemStack.asNMSCopy(item);

		if (i.hasTag() && i.getTag() != null) {
			tag = i.getTag();
		} else {
			tag = new NBTTagCompound();
		}
		tag.setDouble(key, value);
		i.setTag(tag);
		return CraftItemStack.asBukkitCopy(i);
	}

	public static String getString(ItemStack item, String key) {
		NBTTagCompound tag;
		if (!hasNbt(item, key)) {
			return "";
		}
		net.minecraft.server.v1_8_R3.ItemStack i = CraftItemStack.asNMSCopy(item);

		if (i.hasTag() && i.getTag() != null) {
			tag = i.getTag();
		} else {
			tag = new NBTTagCompound();
		}
		return tag.getString(key);
	}

	public static int getInt(ItemStack item, String key) {
		NBTTagCompound tag;
		if (!hasNbt(item, key)) {
			return 0;
		}
		net.minecraft.server.v1_8_R3.ItemStack i = CraftItemStack.asNMSCopy(item);

		if (i.hasTag() && i.getTag() != null) {
			tag = i.getTag();
		} else {
			tag = new NBTTagCompound();
		}
		return tag.getInt(key);
	}

	public static boolean getBoolean(ItemStack item, String key) {
		NBTTagCompound tag;
		if (!hasNbt(item, key)) {
			return false;
		}
		net.minecraft.server.v1_8_R3.ItemStack i = CraftItemStack.asNMSCopy(item);

		if (i.hasTag() && i.getTag() != null) {
			tag = i.getTag();
		} else {
			tag = new NBTTagCompound();
		}
		return tag.getBoolean(key);
	}

	public static double getDouble(ItemStack item, String key) {
		NBTTagCompound tag;
		if (!hasNbt(item, key)) {
			return 0.0D;
		}
		net.minecraft.server.v1_8_R3.ItemStack i = CraftItemStack.asNMSCopy(item);

		if (i.hasTag() && i.getTag() != null) {
			tag = i.getTag();
		} else {
			tag = new NBTTagCompound();
		}
		return tag.getDouble(key);
	}
}