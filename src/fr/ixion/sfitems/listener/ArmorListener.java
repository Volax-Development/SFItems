package fr.ixion.sfitems.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.ixion.sfitems.items.SFItem;
import hkr.ArmorEquipEventFiles.ArmorEquipEvent;

public class ArmorListener implements Listener {

	@EventHandler
	public void join(PlayerJoinEvent e) {
		for (ItemStack item : e.getPlayer().getInventory().getArmorContents()) {
			if (SFItem.isStuff(item)) {
				exec(SFItem.getStuffByItem(item), true, e.getPlayer());
			}
		}
	}

	@EventHandler
	public void armor(ArmorEquipEvent e) {
		if (SFItem.isStuff(e.getOldArmorPiece())) {
			exec(SFItem.getStuffByItem(e.getOldArmorPiece()), true, e.getPlayer());
		}
		if (SFItem.isStuff(e.getNewArmorPiece())) {
			exec(SFItem.getStuffByItem(e.getNewArmorPiece()), false, e.getPlayer());
		}
	}

	public void exec(SFItem it, boolean remove, Player p) {
		PotionEffectType type = null;
		int amp = 0;
		if (SFItem.FARM_HELMET.equals(it)) {
			type = PotionEffectType.NIGHT_VISION;
			amp = 0;
		}
		if (SFItem.FARM_CHESTPLATE.equals(it)) {
			type = PotionEffectType.DAMAGE_RESISTANCE;
			amp = 0;
		}
		if (SFItem.FARM_LEGGINGS.equals(it)) {
			type = PotionEffectType.FIRE_RESISTANCE;
			amp = 0;
		}
		if (SFItem.FARM_BOOTS.equals(it)) {
			type = PotionEffectType.SPEED;
			amp = 1;
		}
		if (type != null) {
			if (remove) {
				p.removePotionEffect(type);
			} else {
				p.addPotionEffect(new PotionEffect(type, Integer.MAX_VALUE, amp), true);
			}
		}
	}
}
