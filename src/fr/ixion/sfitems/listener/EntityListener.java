package fr.ixion.sfitems.listener;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import fr.ixion.sfitems.items.SFItem;

public class EntityListener implements Listener {

	private List<EntityType> onShot = Arrays.asList(EntityType.ZOMBIE, EntityType.SKELETON, EntityType.PIG_ZOMBIE,
			EntityType.CAVE_SPIDER, EntityType.SPIDER, EntityType.IRON_GOLEM, EntityType.COW, EntityType.BLAZE,
			EntityType.CHICKEN);

	@EventHandler(priority = EventPriority.MONITOR)
	public void damageEntityByEntity(EntityDamageByEntityEvent e) {
		if (!e.isCancelled()) {
			if (e.getDamager() instanceof Player) {
				Player player = (Player) e.getDamager();
				if (SFItem.EPEE_DE_FARM.isSimilarTo(player.getItemInHand())) {
					if (onShot.contains(e.getEntity().getType())) {
						((LivingEntity) e.getEntity()).damage(((LivingEntity) e.getEntity()).getMaxHealth());
					}
					player.setItemInHand(SFItem.sumDura(player.getItemInHand(), 1));
				}
			}
		}
	}
}
