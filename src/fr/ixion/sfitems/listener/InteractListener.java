package fr.ixion.sfitems.listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import net.brcdev.shopgui.exception.player.PlayerDataNotLoadedException;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import fr.ixion.sfitems.Main;
import fr.ixion.sfitems.items.SFItem;
import fr.ixion.sfitems.util.TagUtils;
import net.brcdev.shopgui.ShopGuiPlusApi;

public class InteractListener implements Listener {

	private List<Material> mats = Arrays.asList(Material.CHEST, Material.TRAPPED_CHEST, Material.ENDER_CHEST,
			Material.HOPPER, Material.ENCHANTMENT_TABLE, Material.BREWING_STAND, Material.FURNACE,
			Material.BURNING_FURNACE, Material.DISPENSER, Material.DROPPER, Material.WORKBENCH);

	HashMap<UUID, Long> coolUnclaimFinder = new HashMap<UUID, Long>();

	/*
	 * 
	 * double mToAdd = 0; for (ItemStack itemStack : drops) { try { double d =
	 * ShopGuiPlusApi.getItemStackPriceSell(e.getPlayer(), itemStack); //
	 * System.out.println("sell" + itemStack.getType() + "x" + itemStack.getAmount()
	 * // + "for " + d); mToAdd += d; } catch (Exception e2) { System.out.println(
	 * "[HidaHoue]" + "L'item " + itemStack.getType() +
	 * " ne peut pas �tre vendu au shop!"); } }
	 * 
	 */

	List<Chest> canotuse = new ArrayList<Chest>();

	@EventHandler
	public void interact(PlayerInteractEvent e) {

		if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (Main.getInstance().sellChest.containsKey(e.getClickedBlock().getLocation())) {
				if (!isChest(e.getClickedBlock())) {
					Main.getInstance().sellChest.remove(e.getClickedBlock().getLocation());
				}
				e.getPlayer().sendMessage("§6Chest de vente");
			}
			if (Main.getInstance().farmHopper.contains(e.getClickedBlock().getLocation())) {
				if (!e.getClickedBlock().getType().equals(Material.HOPPER)) {
					Main.getInstance().farmHopper.remove(e.getClickedBlock().getLocation());
				}
				e.getPlayer().sendMessage("§4Hopper de farm");
			}
			if (SFItem.SELLSTICK.isSimilarTo(e.getPlayer().getItemInHand())) {
				e.setCancelled(true);
				int am = 0;
				int benef = 0;
				if (isChest(e.getClickedBlock())) {
					Player p = e.getPlayer();
					Chest chest = (Chest) e.getClickedBlock().getState();
					canotuse.add(chest);
					for (int i = 0; i < chest.getInventory().getSize(); i++) {
						ItemStack item = chest.getInventory().getItem(i);
						if (item != null && !item.getType().equals(Material.AIR)) {
							double d = 0.0D;
								d = ShopGuiPlusApi.getItemStackPriceSell(p, item);
							if (d > 0.00) {
								am += item.getAmount();
								chest.getInventory().setItem(i, null);
								benef += d;
							}
						}
					}
					benef *= 1.5;
					if (benef > 0) {
						Main.getEcon().depositPlayer(e.getPlayer(), benef);
						p.sendMessage(SFItem.SELLSTICK.getUsemessage().replaceAll(TagUtils.QUANTITE_TAG, am + "")
								.replaceAll(TagUtils.PRIX_TAG, benef + "")
								.replaceAll(TagUtils.PLAYER_TAG, p.getName()));
						e.getPlayer().setItemInHand(SFItem.sumDura(p.getItemInHand(), 1));
					}
					canotuse.remove(chest);
				}
				return;
			}
		}
		if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
			if (SFItem.UNCLAIM_FINDER.isSimilarTo(e.getPlayer().getItemInHand())) {
				e.setCancelled(true);
				Player p = e.getPlayer();
				if (coolUnclaimFinder.containsKey(p.getUniqueId())) {
					int sm = (int) ((System.currentTimeMillis() - coolUnclaimFinder.get(p.getUniqueId())) / 1000);
					int am = SFItem.UNCLAIM_FINDER.getCooldown();
					if (sm < am) {
						p.sendMessage(SFItem.UNCLAIM_FINDER.getCooldownmessage()
								.replaceAll(TagUtils.TEMPS_TAG, (am - sm) + "")
								.replaceAll(TagUtils.PLAYER_TAG, p.getName()));
						return;
					}
				}
				coolUnclaimFinder.put(p.getUniqueId(), System.currentTimeMillis());
				Chunk chunk = e.getPlayer().getLocation().getChunk();
				int i = 0;
				for (int x = 0; x < 16; x++) {
					for (int z = 0; z < 16; z++) {
						for (int y = 0; y < 256; y++) {
							if (mats.contains(chunk.getBlock(x, y, z).getType())) {
								i++;
							}
						}
					}
				}
				p.sendMessage(SFItem.UNCLAIM_FINDER.getUsemessage()
						.replaceAll(TagUtils.QUANTITE_TAG, (i < 100 ? i : 100) + "")
						.replaceAll(TagUtils.PLAYER_TAG, p.getName()));
				e.getPlayer().setItemInHand(SFItem.sumDura(p.getItemInHand(), 1));
				return;
			}
		}
	}

	private boolean isChest(Block clicked_block) {
		Material btype = clicked_block.getType();
		if (btype.equals(Material.TRAPPED_CHEST) || btype.equals(Material.CHEST)) {
			return true;
		} else {
			return false;
		}
	}

	@EventHandler
	public void click(InventoryClickEvent e) {

	}

}
