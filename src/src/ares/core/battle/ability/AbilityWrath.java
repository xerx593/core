package src.ares.core.battle.ability;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import src.ares.core.battle.kit.Kit;
import src.ares.core.common.Cooldown;

public class AbilityWrath extends Ability
{
	private Player victim;

	public AbilityWrath(Kit kit)
	{
		super(kit, "Wrath");
	}

	@EventHandler
	public void checkIfToggled(EntityDamageByEntityEvent event)
	{
		if (event.getEntity() instanceof Player && event.getDamager() instanceof Player)
		{
			Player victim = (Player) event.getEntity();
			Player damager = (Player) event.getDamager();

			this.victim = victim;

			if (validate(damager))
			{
				if (hasToggledUse(damager))
					useAbility(damager);
			}
		}
	}

	@EventHandler
	public void checkIfUsed(PlayerDropItemEvent event)
	{
		event.setCancelled(true);

		Player player = event.getPlayer();
		Material stack = event.getItemDrop().getItemStack().getType();

		if (validate(player) && pressedEnableKey(event) && stack.equals(Material.IRON_AXE))
		{
			toggleUse(player);
		}
	}

	@Override
	public void useAbility(Player player)
	{
		if (!Cooldown.create(player, 10, getName(), true))
		{
			victim.setVelocity(player.getLocation().getDirection().normalize().multiply(3.0).setY(0.5));
			showUse(player);
		}
	}
}
