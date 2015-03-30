package src.ares.core.battle.ability;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import src.ares.core.battle.kit.Kit;
import src.ares.core.client.Client;
import src.ares.core.common.Cooldown;
import src.ares.core.common.util.Chat;
import src.ares.core.common.util.UtilPlayer;

public class AbilityLeap extends Ability
{
	private static double POWER = 1.7;

	public AbilityLeap(Kit kit)
	{
		super(kit, "Leap");
	}

	@Override
	public String getTip(Client client)
	{
		int level = level(client.getPlayer());
		return "Press " + Chat.tool("Right-Click") + " to double jump with " + Chat.time((POWER + (level / 4)) + " power") + ".";
	}

	@EventHandler
	public void cancelFallDamage(EntityDamageEvent event)
	{
		if (event.getEntity() instanceof Player)
		{
			Player player = (Player) event.getEntity();

			if (validate(player))
			{
				if (event.getCause() == DamageCause.FALL)
				{
					event.setDamage(0.0);
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void toggleAbility(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();

		if (validate(player))
		{
			if (UtilPlayer.isHoldingItemOffensive(player))
			{
				if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
				{
					event.setCancelled(true);
					useAbility(player);
				}
			}
		}
	}

	@Override
	public void useAbility(Player player)
	{
		int level = level(player);
		Vector leap = player.getLocation().getDirection().normalize().multiply(POWER + (level / 4));

		if (UtilPlayer.isOnWater(player))
			return;

		if (!Cooldown.create(player, 5, getName(), true))
		{
			showUse(player);
			player.setVelocity(leap);
			player.getWorld().playSound(player.getLocation(), Sound.BAT_TAKEOFF, 1.3F, 1.2F);
		}
	}
}
