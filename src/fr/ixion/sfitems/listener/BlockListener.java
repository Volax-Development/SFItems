package fr.ixion.sfitems.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NetherWartsState;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.NetherWarts;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import fr.ixion.sfitems.Main;
import fr.ixion.sfitems.items.SFItem;

public class BlockListener implements Listener {

	Map<UUID, BlockFace> lastBlockFace = new HashMap<>();

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.LEFT_CLICK_BLOCK)
			this.lastBlockFace.put(e.getPlayer().getUniqueId(), e.getBlockFace());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void blockBreackEvent(BlockBreakEvent e) {
		if (!e.isCancelled()) {

			if (SFItem.SPECIAL_PICKAXE.isSimilarTo(e.getPlayer().getItemInHand())) {
				if (Main.getInstance().sellChest.containsKey(e.getBlock().getLocation())) {
					e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), SFItem.SELLCHEST.getItem());
					e.getBlock().setType(Material.AIR);
					e.setCancelled(true);
					Main.getInstance().sellChest.remove(e.getBlock().getLocation());
					e.getPlayer().setItemInHand(SFItem.sumDura(e.getPlayer().getItemInHand(), 1));
				}
				if (Main.getInstance().farmHopper.contains(e.getBlock().getLocation())) {
					e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), SFItem.FARMHOPPER.getItem());
					e.getBlock().setType(Material.AIR);
					e.setCancelled(true);
					Main.getInstance().farmHopper.remove(e.getBlock().getLocation());
					e.getPlayer().setItemInHand(SFItem.sumDura(e.getPlayer().getItemInHand(), 1));
				}
				return;
			}
			if (Main.getInstance().sellChest.containsKey(e.getBlock().getLocation())) {
				e.getBlock().setType(Material.AIR);
				e.setCancelled(true);
				Main.getInstance().sellChest.remove(e.getBlock().getLocation());
			}
			if (Main.getInstance().farmHopper.contains(e.getBlock().getLocation())) {
				e.getBlock().setType(Material.AIR);
				e.setCancelled(true);
				Main.getInstance().farmHopper.remove(e.getBlock().getLocation());
			}
			if (SFItem.HAMMER.isSimilarTo(e.getPlayer().getItemInHand())) {
				Player p = e.getPlayer();
				ItemStack i = e.getPlayer().getItemInHand();
				if (this.lastBlockFace.get(p.getUniqueId()) == null
						|| this.lastBlockFace.get(p.getUniqueId()) == BlockFace.DOWN
						|| this.lastBlockFace.get(p.getUniqueId()) == BlockFace.UP) {
					processBlock(i, e.getBlock().getRelative(BlockFace.NORTH), p);
					processBlock(i, e.getBlock().getRelative(BlockFace.NORTH_EAST), p);
					processBlock(i, e.getBlock().getRelative(BlockFace.EAST), p);
					processBlock(i, e.getBlock().getRelative(BlockFace.SOUTH_EAST), p);
					processBlock(i, e.getBlock().getRelative(BlockFace.SOUTH), p);
					processBlock(i, e.getBlock().getRelative(BlockFace.SOUTH_WEST), p);
					processBlock(i, e.getBlock().getRelative(BlockFace.WEST), p);
					processBlock(i, e.getBlock().getRelative(BlockFace.NORTH_WEST), p);
				}
				if (this.lastBlockFace.get(p.getUniqueId()) == BlockFace.EAST
						|| this.lastBlockFace.get(p.getUniqueId()) == BlockFace.WEST) {
					processBlock(i, e.getBlock().getRelative(BlockFace.UP), p);
					processBlock(i, e.getBlock().getRelative(BlockFace.DOWN), p);
					processBlock(i, e.getBlock().getRelative(BlockFace.NORTH), p);
					processBlock(i, e.getBlock().getRelative(BlockFace.SOUTH), p);
					processBlock(i, e.getBlock().getRelative(BlockFace.NORTH).getRelative(BlockFace.UP), p);
					processBlock(i, e.getBlock().getRelative(BlockFace.NORTH).getRelative(BlockFace.DOWN), p);
					processBlock(i, e.getBlock().getRelative(BlockFace.SOUTH).getRelative(BlockFace.UP), p);
					processBlock(i, e.getBlock().getRelative(BlockFace.SOUTH).getRelative(BlockFace.DOWN), p);
				}
				if (this.lastBlockFace.get(p.getUniqueId()) == BlockFace.NORTH
						|| this.lastBlockFace.get(p.getUniqueId()) == BlockFace.SOUTH) {
					processBlock(i, e.getBlock().getRelative(BlockFace.UP), p);
					processBlock(i, e.getBlock().getRelative(BlockFace.DOWN), p);
					processBlock(i, e.getBlock().getRelative(BlockFace.EAST), p);
					processBlock(i, e.getBlock().getRelative(BlockFace.WEST), p);
					processBlock(i, e.getBlock().getRelative(BlockFace.EAST).getRelative(BlockFace.UP), p);
					processBlock(i, e.getBlock().getRelative(BlockFace.EAST).getRelative(BlockFace.DOWN), p);
					processBlock(i, e.getBlock().getRelative(BlockFace.WEST).getRelative(BlockFace.UP), p);
					processBlock(i, e.getBlock().getRelative(BlockFace.WEST).getRelative(BlockFace.DOWN), p);
				}
				e.getPlayer().setItemInHand(SFItem.sumDura(i, 1));
				return;
			}
			if (SFItem.HOUE_DE_FARM.isSimilarTo(e.getPlayer().getItemInHand())) {
				List<ItemStack> drops = new ArrayList<ItemStack>();
				for (int x = -1; x <= 1; x++) {
					for (int z = -1; z <= 1; z++) {
						Block b = e.getBlock().getWorld().getBlockAt(e.getBlock().getLocation().getBlockX() + x,
								e.getBlock().getLocation().getBlockY(), e.getBlock().getLocation().getBlockZ() + z);
                        boolean replant = false;
                        BlockState state = b.getState();
                        switch (state.getType()) {
                            case CARROT:
                                if (isGrow(b)) {
                                    replant = true;
                                }
                                e.setCancelled(true);
                                break;
                            case POTATO:
                                if (isGrow(b)) {
                                    replant = true;
                                }
                                e.setCancelled(true);
                                break;
                            case CROPS:
                                if (isGrow(b)) {
                                    replant = true;
                                }
                                e.setCancelled(true);
                                break;
                            case NETHER_WARTS:
                                if (isGrow(b)) {
                                    replant = true;
                                }
                                e.setCancelled(true);
                                break;
                        }

                        if (replant) {
                            drops.addAll(b.getDrops(e.getPlayer().getItemInHand()));
                            b.setType(Material.AIR);
                            switch (state.getType()) {
                                case CARROT:
                                    b.getState().setRawData((byte) 0);
                                    replant(b.getLocation(), Material.CARROT, 2, b.getData());
                                    break;
                                case POTATO:
                                    b.getState().setRawData((byte) 0);
                                    replant(b.getLocation(), Material.POTATO, 2, b.getData());
                                    break;
                                case CROPS:
                                    b.getState().setRawData((byte) 0);
                                    replant(b.getLocation(), Material.CROPS, 2, b.getData());
                                    break;
                                case NETHER_WARTS:
                                    replant(b.getLocation(), Material.NETHER_WARTS, 2, b.getData());
                                    break;
                            }
                        }
					}
				}
				e.getPlayer().setItemInHand(SFItem.sumDura(e.getPlayer().getItemInHand(), 1));
				return;
			}

		}
	}

	private boolean isChest(Block clicked_block) {
		Material btype = clicked_block.getType();
		if (btype.equals(Material.CHEST)) {
			return true;
		} else {
			return false;
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void place(BlockPlaceEvent e) {
		if (!e.isCancelled() && e.canBuild()) {
			if (e.getPlayer() != null) {
				Player p = e.getPlayer();
				if (e.getItemInHand() != null) {
					if (SFItem.SELLCHEST.isSimilarTo(e.getItemInHand())) {
						if (!isChest(e.getBlock().getRelative(BlockFace.NORTH))
								&& !isChest(e.getBlock().getRelative(BlockFace.SOUTH))
								&& !isChest(e.getBlock().getRelative(BlockFace.WEST))
								&& !isChest(e.getBlock().getRelative(BlockFace.EAST))) {
							Main.getInstance().sellChest.put(e.getBlock().getLocation(),
									e.getPlayer().getUniqueId().toString());
						} else {
							e.setCancelled(true);
						}
					}
					if (SFItem.FARMHOPPER.isSimilarTo(e.getItemInHand())) {
						Main.getInstance().farmHopper.add(e.getBlock().getLocation());
					}
				}
			}
		}
	}

	public boolean isGrow(Block b) {
		BlockState state = b.getState();
		switch (state.getType()) {
		case CARROT:
			return (state.getRawData() >= 7);
		case POTATO:
			return (state.getRawData() >= 7);
		case CROPS:
			return (state.getRawData() >= 7);
		case NETHER_WARTS:
			return (((NetherWarts) state.getData()).getState() == NetherWartsState.RIPE);
		}
		return false;
	}

	public void replant(final Location l, final Material mat, int time, byte b) {
		(new BukkitRunnable() {
			public void run() {
				if (l.getWorld().getBlockAt(l).getType().equals(Material.AIR)) {
					l.getWorld().getBlockAt(l).setType(mat);

				}
			}
		}).runTaskLater((Plugin) Main.getInstance(), time);
	}

	private void processBlock(ItemStack i, Block b, Player p) {
		if (b.isLiquid())
			return;
		if (b.getType() == Material.BEDROCK || b.getType() == Material.BARRIER || b.getType() == Material.MOB_SPAWNER
				|| b.getType() == Material.ENDER_CHEST || b.getType() == Material.ENDER_PORTAL
				|| b.getType() == Material.ENDER_PORTAL_FRAME || b.getType() == Material.PORTAL)
			return;

		b.breakNaturally(i);
		if (Main.getInstance().sellChest.containsKey(b.getLocation())) {
			Main.getInstance().sellChest.remove(b.getLocation());
		}
		if (Main.getInstance().farmHopper.contains(b.getLocation())) {
			Main.getInstance().farmHopper.remove(b.getLocation());
		}
	}
}
