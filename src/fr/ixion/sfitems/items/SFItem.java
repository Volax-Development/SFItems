package fr.ixion.sfitems.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.ixion.sfitems.Main;
import fr.ixion.sfitems.util.TagUtils;
import fr.ixion.sfitems.util.UtilItem;
import fr.ixion.sfitems.util.UtilRecipe;
import fr.ixion.sfitems.util.Utils;
import fr.ixion.sfitems.util.UtilsNbt;

public class SFItem {

	public static String NBTTAG = "ixion.sfitems.id";
	private static String NBTTAG_DURA = "ixion.sfitems.dura";

	private static List<SFItem> all = new ArrayList<>();
	private static int ids = 0;
	public static SFItem DIAMOND_STICK = new SFItem("diamond_stick", new HashMap<Integer, ItemStack>() {
		{
			put(0, new ItemStack(Material.DIAMOND_BLOCK));
			put(3, new ItemStack(Material.DIAMOND_BLOCK));
		}
	});

	public static SFItem HAMMER = new SFItem("hammer", new HashMap<Integer, ItemStack>() {
		{
			put(0, new ItemStack(Material.DIAMOND_BLOCK));
			put(1, new ItemStack(Material.EMERALD_BLOCK));
			put(2, new ItemStack(Material.DIAMOND_BLOCK));
			put(3, new ItemStack(Material.EMERALD_BLOCK));
			put(4, new ItemStack(Material.DIAMOND_PICKAXE));
			put(5, new ItemStack(Material.EMERALD_BLOCK));
			put(6, DIAMOND_STICK.getItem());
			put(7, new ItemStack(Material.EMERALD_BLOCK));
			put(8, DIAMOND_STICK.getItem());
		}
	});
	public static SFItem HOUE_DE_FARM = new SFItem("houe_de_farm");
	public static SFItem EPEE_DE_FARM = new SFItem("epee_de_farm", new HashMap<Integer, ItemStack>() {
		{
			put(0, new ItemStack(Material.DIAMOND));
			put(1, new ItemStack(Material.EMERALD));
			put(2, new ItemStack(Material.DIAMOND));
			put(3, new ItemStack(Material.EMERALD));
			put(4, new ItemStack(Material.DIAMOND_SWORD));
			put(5, new ItemStack(Material.EMERALD));
			put(6, new ItemStack(Material.DIAMOND));
			put(7, new ItemStack(Material.EMERALD));
			put(8, new ItemStack(Material.DIAMOND));
		}
	});
	public static SFItem UNCLAIM_FINDER = new SFItem("unclaim_finder");
	public static SFItem SELLSTICK = new SFItem("sellstick");
	public static SFItem SELLCHEST = new SFItem("sellchest");
	public static SFItem FARMHOPPER = new SFItem("farmhopper");

	public static SFItem FARM_HELMET = new SFItem("farm_helmet");
	public static SFItem FARM_CHESTPLATE = new SFItem("farm_chestplate");
	public static SFItem FARM_LEGGINGS = new SFItem("farm_leggings");
	public static SFItem FARM_BOOTS = new SFItem("farm_boots");
	public static SFItem SPECIAL_PICKAXE = new SFItem("special_pickaxe");

	public static SFItem ENCH_1_PICKAXE = new SFItem("ench_1_pickaxe").setEnchantments(Utils.generateEnchantements(
			new Enchantment[] { Enchantment.DIG_SPEED, Enchantment.DURABILITY, Enchantment.LOOT_BONUS_BLOCKS },
			new int[] { 7, 3, 4 }));
	public static SFItem ENCH_2_PICKAXE = new SFItem("ench_2_pickaxe").setEnchantments(Utils.generateEnchantements(
			new Enchantment[] { Enchantment.DIG_SPEED, Enchantment.DURABILITY, Enchantment.LOOT_BONUS_BLOCKS },
			new int[] { 10, 4, 7 }));
	public static SFItem ENCH_3_PICKAXE = new SFItem("ench_3_pickaxe").setEnchantments(Utils.generateEnchantements(
			new Enchantment[] { Enchantment.DIG_SPEED, Enchantment.LOOT_BONUS_BLOCKS }, new int[] { 15, 10 }));

	public static void generate() {
		all.add(DIAMOND_STICK);
		all.add(HAMMER);
		all.add(HOUE_DE_FARM);
		all.add(EPEE_DE_FARM);
		all.add(UNCLAIM_FINDER);
		all.add(SELLSTICK);
		all.add(SELLCHEST);
		all.add(FARMHOPPER);

		all.add(FARM_HELMET);
		all.add(FARM_CHESTPLATE);
		all.add(FARM_LEGGINGS);
		all.add(FARM_BOOTS);

		all.add(SPECIAL_PICKAXE);
		
		all.add(ENCH_1_PICKAXE);
		all.add(ENCH_2_PICKAXE);
		all.add(ENCH_3_PICKAXE);
	}

	public static List<SFItem> getAll() {
		return all;
	}

	private String name = "§cNo name";
	private int dura = -1;
	private boolean stackable = true;
	private String path = "";

	private ItemStack item;
	private int id;
	private boolean unbreakable = false;;

	private int cooldown = -1;
	private int item_id = 1;
	private short item_data = -1;
	private String cooldownmessage = "";
	private Map<Enchantment, Integer> enchantments = new HashMap<Enchantment, Integer>();
	private String usemessage = "";
	private String giveText = "";
	private boolean craft = false;
	private boolean glowing = false;
	private List<String> lore = new ArrayList<String>();

    public static String getNBTTAG() {
        return NBTTAG;
    }

    public String getName() {
        return name;
    }

    public int getDura() {
        return dura;
    }

    public boolean isStackable() {
        return stackable;
    }

    public String getPath() {
        return path;
    }

    public int getId() {
        return id;
    }

    public boolean isUnbreakable() {
        return unbreakable;
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getItem_id() {
        return item_id;
    }

    public short getItem_data() {
        return item_data;
    }

    public String getCooldownmessage() {
        return cooldownmessage;
    }

    public Map<Enchantment, Integer> getEnchantments() {
        return enchantments;
    }

    public String getUsemessage() {
        return usemessage;
    }

    public String getGiveText() {
        return giveText;
    }

    public boolean isCraft() {
        return craft;
    }

    public boolean isGlowing() {
        return glowing;
    }

    public List<String> getLore() {
        return lore;
    }

    public SFItem(String tag) {
		this.path = tag.toLowerCase();
		this.id = ids;
		ids++;
		if (Main.getInstance().getConfig().isConfigurationSection(tag)) {
			ConfigurationSection section = Main.getInstance().getConfig().getConfigurationSection(tag);
			if (section.contains("item_id")) {
				item_id = section.getInt("item_id", 1);
			}
			if (section.contains("item_data")) {
				item_data = (short) section.getInt("item_data", 1);
			}
			if (section.contains("name")) {
				name = section.getString("name", "").replaceAll(TagUtils.COLOR_TAG, "§");
			}
			if (section.contains("lore")) {
				lore = section.getStringList("lore").stream().map(string -> string.replaceAll(TagUtils.COLOR_TAG, "§"))
						.collect(Collectors.toList());
			}
			if (section.contains("dura")) {
				dura = section.getInt("dura");
			}
			if (dura == -1 && section.contains("unbreakable")) {
				unbreakable = section.getBoolean("unbreakable");
			}
			if (dura == -1 && section.contains("stackable")) {
				stackable = section.getBoolean("stackable");
			}
			if (section.contains("cooldown")) {
				cooldown = section.getInt("cooldown");
			}
			if (section.contains("cooldownmessage")) {
				cooldownmessage = section.getString("cooldownmessage", "").replaceAll(TagUtils.COLOR_TAG, "§");
			}
			if (section.contains("usemessage")) {
				usemessage = section.getString("usemessage", "").replaceAll(TagUtils.COLOR_TAG, "§");
			}
			if (section.contains("givemessage")) {
				giveText = section.getString("givemessage", "").replaceAll(TagUtils.COLOR_TAG, "§");
			}
			if (section.contains("enchantments")) {
				enchantments = Utils.generateEnchantements(section.getString("enchantments", ""));
			}
			if (section.contains("craft")) {
				craft = section.getBoolean("craft", false);
			}
			if (section.contains("glowing")) {
				glowing = section.getBoolean("glowing", false);
			}
			UtilItem item = new UtilItem(item_data != -1 ? new ItemStack(Material.getMaterial(item_id), 1, item_data)
					: new ItemStack(Material.getMaterial(item_id)))
							.setName(name)
							.setLore(lore.stream()
									.map(string -> string.replaceAll(TagUtils.DURABILITY_TAG, dura + "")
											.replaceAll(TagUtils.DURABILITY_MAX_TAG, dura + ""))
									.collect(Collectors.toList()));
			item.setGlowing(glowing);
			if (!enchantments.isEmpty()) {
				item.addUnsafeEnchantments(enchantments);
			}
			if (unbreakable || dura != -1) {
				item.setUnbreakable();
			}
			ItemStack it = UtilsNbt.setInt(item, NBTTAG, id);
			if (dura != -1) {
				it = UtilsNbt.setInt(it, NBTTAG_DURA, dura);
			}
			this.item = it;
			if (!this.stackable && item.getType().getMaxStackSize() == 1) {
				this.stackable = true;
			}
		} else {
			System.out.println("[SFItems] " + tag + " can't be load");
		}
	}

	public SFItem(String tag, HashMap<Integer, ItemStack> its) {
		this(tag, its, 1);
	}

	public SFItem(String tag, HashMap<Integer, ItemStack> its, int amount) {
		this(tag);
		if (craft) {
			Bukkit.addRecipe(new UtilRecipe(its).generateRecipe(this.getItem(), amount));
		}
	}

	public SFItem setEnchantments(HashMap<Enchantment, Integer> ench) {
		this.item.addUnsafeEnchantments(ench);
		return this;
	}

	public ItemStack getItem() {
		if (!stackable) {
			return UtilsNbt.setString(item, "ixion.stackable", UUID.randomUUID().toString()).clone();
		} else {
			return this.item.clone();
		}
	}

	public boolean isSimilarTo(ItemStack item) {
		if (item == null) {
			return false;
		}
		if (!UtilsNbt.hasNbt(item, NBTTAG)) {
			return false;
		}
		if (UtilsNbt.getInt(item, NBTTAG) != getId()) {
			return false;
		}
		return true;
	}

	public static boolean isStuff(ItemStack item) {
		return UtilsNbt.hasNbt(item, NBTTAG);
	}

	public static SFItem getStuffById(int id) {
		for (SFItem stuff : getAll()) {
			if (stuff.getId() == id) {
				return stuff;
			}
		}
		return null;
	}

	public static SFItem getStuffByItem(ItemStack item) {
		for (SFItem stuff : getAll()) {
			if (stuff.isSimilarTo(item)) {
				return stuff;
			}
		}
		return null;
	}

	public static int unbreackLvl(ItemStack item) {
		int i = 0;
		if (item.containsEnchantment(Enchantment.DURABILITY)) {
			i = item.getEnchantmentLevel(Enchantment.DURABILITY);
		}
		return i;
	}

	public static ItemStack sumDura(ItemStack i, int am) {
		if (!UtilsNbt.hasNbt(i, NBTTAG_DURA)) {
			i.setDurability((short) (i.getDurability() + 1));
			return i;
		} else {
			int dura = UtilsNbt.getInt(i, NBTTAG_DURA);
			double duraSum = (15 + 85 / (unbreackLvl(i) + 1));
			if (Utils.randInt(0, 100) < duraSum) {
				int newDura = dura - am;
				if (newDura > 0) {
					ItemMeta meta = i.getItemMeta();
					SFItem sfItem = getStuffByItem(i);
					meta.setLore(sfItem.getLore().stream()
							.map(string -> string.replaceAll(TagUtils.DURABILITY_TAG, newDura + "")
									.replaceAll(TagUtils.DURABILITY_MAX_TAG, sfItem.getDura() + ""))
							.collect(Collectors.toList()));
					i.setItemMeta(meta);
					i = UtilsNbt.setInt(i, NBTTAG_DURA, newDura);
					return i;
				}
			}
		}
		return i;
	}

}
