package src.ares.core.battle.ability;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import src.ares.core.battle.kit.Kit;
import src.ares.core.client.Client;
import src.ares.core.common.cooldown.Cooldown;
import src.ares.core.common.util.Chat;

public class AbilityEarthquake extends HotkeyAbility
{
	private static double DAMAGE = 4.0;

	public AbilityEarthquake(Kit kit)
	{
		super(kit, "Earthquake", Material.IRON_HOE);
	}

	@Override
	public String getTip(Client client)
	{
		int level = level(client.getPlayer());
		return "Throw in the air nearby players dealing " + Chat.tool(((DAMAGE / 2) + level) + " heart") + " damage.";
	}

	@EventHandler
	public void toggleAbility(PlayerInteractEvent event)
	{
		if (validate(event.getPlayer()))
		{
			Player player = event.getPlayer();

			if (player.getItemInHand().getType() != getHandItem())
				return;

			if (!hasToggledUse(player))
				return;

			if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
			{
				event.setCancelled(true);
				useAbility(player);
			}
		}
	}

	@Override
	public void useAbility(Player player)
	{
		int level = level(player);

		if (!Cooldown.create(player, 10, getName(), true))
		{
			Client client = new Client(player);
			showUse(player);

			for (Entity entities : player.getNearbyEntities(3.0 + level, 3.0 + level, 3.0 + level))
			{
				if (entities instanceof LivingEntity)
				{
					LivingEntity entity = (LivingEntity) entities;
					entity.damage(DAMAGE + level);
					entity.getWorld().playSound(entity.getLocation(), Sound.BLAZE_HIT, 1.0F, 1.0F);
					Vector direction = entity.getLocation().toVector().normalize().subtract(player.getLocation().toVector().normalize());
					entity.setVelocity(direction.multiply(12.0 + level).setY(1));
				}
			}

			client.unload();
		}
	}
}
