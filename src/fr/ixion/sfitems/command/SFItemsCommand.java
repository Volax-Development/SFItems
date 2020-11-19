package fr.ixion.sfitems.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.ixion.sfitems.items.SFItem;
import fr.ixion.sfitems.util.TagUtils;
import fr.ixion.sfitems.util.Utils;

public class SFItemsCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().contentEquals("sfitems")) {
			if (sender.isOp() || sender.hasPermission("sfitems.perm")) {
				if (args.length == 1 && args[0].contentEquals("list")) {
					sender.sendMessage("§aListe des id:");
					for (SFItem item : SFItem.getAll()) {
						sender.sendMessage("§a-§e " + item.getId() + " §a-> " + item.getName());
					}
				} else {
					if (args.length == 2 || args.length == 3) {
						if (Utils.isInteger(args[0])) {
							SFItem item = SFItem.getStuffById(Integer.parseInt(args[0]));
							if (item != null) {
								if (Bukkit.getPlayer(args[1]) != null && Bukkit.getPlayer(args[1]).isOnline()) {
									Player p = Bukkit.getPlayer(args[1]);
									int amount = 1;
									if (args.length == 3 && Utils.isInteger(args[2])) {
										amount = Integer.parseInt(args[2]);
									}
									ItemStack it;
									it = item.getItem();
									it.setAmount(amount);
									if (p.getInventory().firstEmpty() != -1) {
										p.getInventory().addItem(it);
									} else p.getWorld().dropItemNaturally(p.getLocation(), it);
									sender.sendMessage(item.getGiveText().replaceAll(TagUtils.PLAYER_TAG, sender.getName()).replaceAll(TagUtils.QUANTITE_TAG, amount + ""));
									return true;
								} else sender.sendMessage("§cNom du joueur invalide.");
							} else sender.sendMessage("§cL'id n'exeste pas.");
						} else sender.sendMessage("§cId invalide.");
					} else help(sender);
				}
			}
		}
		return false;
	}

	public static void help(CommandSender sender) {
		sender.sendMessage("§c/sfitems [id] [player] ([amount])");
		sender.sendMessage("§c/sfitems list");
	}
}
