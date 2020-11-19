package fr.ixion.sfitems.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import fr.ixion.sfitems.items.SFItem;

public class CraftListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void craft(PrepareItemCraftEvent e) {
		if (SFItem.HAMMER.isSimilarTo(e.getRecipe().getResult())) {
			return;
		}
		for (ItemStack item : e.getInventory().getMatrix()) {
			if (SFItem.isStuff(item)) {
				e.getInventory().setResult(null);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void craft2(PrepareItemCraftEvent e) {
		if (SFItem.HAMMER.isSimilarTo(e.getRecipe().getResult())) {
			for (ItemStack item : e.getInventory().getMatrix()) {
				if (item != null && item.getType().equals(Material.STICK) && !SFItem.DIAMOND_STICK.isSimilarTo(item)) {
					e.getInventory().setResult(null);
				}
			}
		}

	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void craftLast(PrepareItemCraftEvent e) {
		if (SFItem.isStuff(e.getInventory().getResult())) {
			e.getInventory().setResult(SFItem.getStuffByItem(e.getInventory().getResult()).getItem());
		}
	}
	
	@EventHandler
	public void enchant(EnchantItemEvent e) {
		e.setCancelled(SFItem.isStuff(e.getItem()));
	}	

}
