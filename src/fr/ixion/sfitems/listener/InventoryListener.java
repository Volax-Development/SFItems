package fr.ixion.sfitems.listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Chest;
import org.bukkit.block.Hopper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.ixion.sfitems.Main;
import net.brcdev.shopgui.ShopGuiPlusApi;

public class InventoryListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void transfer(InventoryMoveItemEvent e) {
		if (!e.getDestination().getType().equals(InventoryType.CHEST))
			return;
		if (!e.getSource().getType().equals(InventoryType.HOPPER))
			return;
		if (e.getSource().getSize() > 27)
			return;
		Chest chest;
		try {

			chest = (Chest) e.getDestination().getHolder();
		} catch (Exception e2) {
			return;
		}
		if (!Main.getInstance().sellChest.containsKey(chest.getLocation())) {
			return;
		}
		OfflinePlayer offP = Bukkit
				.getOfflinePlayer(UUID.fromString(Main.getInstance().sellChest.get(chest.getLocation())));
		if (!offP.isOnline()) {
			e.setCancelled(true);
			return;
		}
		if (sell(chest, e.getDestination())) {
			e.getDestination().clear();
			e.setCancelled(ShopGuiPlusApi.getItemStackPriceSell(offP.getPlayer(), e.getItem()) <= 0.00);
		}
	}

	@EventHandler
	public void open(InventoryOpenEvent e) {
		if (!e.getInventory().getType().equals(InventoryType.CHEST))
			return;
		Chest chest;
		try {
			chest = (Chest) e.getInventory().getHolder();

			if (chest != null && Main.getInstance().sellChest.containsKey(chest.getLocation())) {
				e.setCancelled(true);
			}
		} catch (Exception e2) {
			return;
		}
	}

	public static List<Chest> canotuse = new ArrayList<Chest>();

	public static boolean sell(Chest chest, Inventory inventory) {
		OfflinePlayer offP = Bukkit
				.getOfflinePlayer(UUID.fromString(Main.getInstance().sellChest.get(chest.getLocation())));
		double d = 0;
		for (int i = 0; i < inventory.getSize(); i++) {
			ItemStack item = inventory.getItem(i);
			if (item != null && !item.getType().equals(Material.AIR))
			    d = ShopGuiPlusApi.getItemStackPriceSell(offP.getPlayer(), item);
		}

		if (d > 0.00) {
			d *= 1.5;
			Main.getEcon().depositPlayer(offP, d);
			return true;
		} else {
			return false;
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onHopperPickup(InventoryPickupItemEvent event) {
		if (!(event.getInventory().getHolder() instanceof Hopper))
			return;
		Hopper hopper = (Hopper) event.getInventory().getHolder();
		event.setCancelled(cancel(hopper, event.getItem().getItemStack()));
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onHopperMove(InventoryMoveItemEvent event) {
		if (!(event.getDestination().getHolder() instanceof Hopper))
			return;
		Hopper hopper = (Hopper) event.getDestination().getHolder();
		event.setCancelled(cancel(hopper, event.getItem()));
	}

	List<ItemStack> mats = Arrays.asList(new ItemStack(Material.CARROT_ITEM), new ItemStack(Material.WHEAT),
			new ItemStack(Material.MELON), new ItemStack(Material.CACTUS), new ItemStack(Material.PUMPKIN),
			new ItemStack(Material.ROTTEN_FLESH), new ItemStack(Material.LEATHER),
			new ItemStack(Material.INK_SACK, 1, (short) 3), new ItemStack(Material.SUGAR_CANE),
			new ItemStack(Material.GOLD_INGOT), new ItemStack(Material.GOLD_NUGGET), new ItemStack(Material.BONE),
			new ItemStack(Material.IRON_INGOT), new ItemStack(Material.BOW), new ItemStack(Material.COOKED_BEEF),
			new ItemStack(Material.getMaterial(372)), new ItemStack(Material.RAW_BEEF),
			new ItemStack(Material.POTATO_ITEM));

	private boolean cancel(Hopper hopper, ItemStack item) {
		if (hopper == null)
			return false;
		if (!Main.getInstance().farmHopper.contains(hopper.getLocation()))
			return false;
		boolean inclusive = false;
		if (item.hasItemMeta() && item.getItemMeta().hasEnchants()) {
			return false;
		}
		for (ItemStack fItem : mats) {
			if (fItem.getType().equals(Material.AIR))
				continue;
			inclusive = true;
			if (matchBroadly(fItem, item))
				return false;
			continue;
		}
		return inclusive;
	}

	private boolean allow(Hopper hopper, ItemStack item) {
		if (hopper == null)
			return false;
		boolean inclusive = false;
		if (item.hasItemMeta() && item.getItemMeta().hasEnchants()) {
			return false;
		}
		for (ItemStack fItem : mats) {
			if (fItem.getType().equals(Material.AIR))
				continue;
			inclusive = true;
			if (matchBroadly(fItem, item))
				return false;
			continue;
		}
		return inclusive;
	}

	@EventHandler
	public void c(EntitySpawnEvent e) {
		if (e.getEntity().getType().equals(EntityType.DROPPED_ITEM)) {
			for (Location loc : Main.getInstance().farmHopper) {
				if (loc.getChunk().equals(e.getEntity().getLocation().getChunk())
						&& loc.getBlockY() < e.getEntity().getLocation().getY()) {
					Hopper hopper = (Hopper) loc.getBlock().getState();
					if (hopper.getInventory().firstEmpty() != -1
							&& !allow(hopper, ((Item) e.getEntity()).getItemStack())) {
						hopper.getInventory().addItem(((Item) e.getEntity()).getItemStack());
						e.getEntity().remove();
						return;
					}
				}
			}
		}
	}

	public static boolean matchBroadly(ItemStack base, ItemStack other) {
		other = other.clone();
		if (base.hasItemMeta()) {
			ItemMeta bM = base.getItemMeta();
			ItemMeta oM = other.getItemMeta();
			assert bM != null;
			assert oM != null;
			if (!bM.hasDisplayName())
				oM.setDisplayName(null);
			if (!bM.hasLore())
				oM.setLore(null);
		}
		return base.isSimilar(other);
	}

}
