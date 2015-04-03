package src.ares.core.battle.ability;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import src.ares.core.Main;
import src.ares.core.battle.kit.Kit;
import src.ares.core.client.Client;
import src.ares.core.common.cooldown.Cooldown;
import src.ares.core.common.util.Chat;

public class AbilityThunderstorm extends HotkeyAbility
{
	private double DAMAGE = 2.5;
	private static int DURATION = 3;

	public AbilityThunderstorm(Kit kit)
	{
		super(kit, "Thunderstorm", Material.IRON_AXE);
	}

	@Override
	public String getTip(Client client)
	{
		int level = level(client.getPlayer());
		return "Activate with " + Chat.tool("Drop Key") + ", press " + Chat.tool("Right-Click") + " to strike nearby enemies. Players recieve " + Chat.tool("Slow I") + " for " + Chat.time((DURATION + level) + " seconds") + " and " + Chat.tool(((DAMAGE / 2) + level) + " heart") + ".";
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
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
	public void useAbility(final Player player)
	{
		final List<Entity> nearbyPlayers = player.getNearbyEntities(3.0, 3.0, 3.0);

		if (nearbyPlayers.size() == 0)
		{
			player.sendMessage(Chat.format(getModuleName(), "No nearby targets found."));
			return;
		}

		if (!Cooldown.create(player, 20, getName(), true))
		{
			for (int i = 1; i < 3; i++)
			{
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable()
				{
					@Override
					public void run()
					{
						strikeThunderstorm(player, nearbyPlayers);
					}
				}, 20 * i);
			}

			showUse(player);
		}
	}

	private void strikeThunderstorm(Player player, List<Entity> nearby)
	{
		int level = level(player);

		for (Entity entities : nearby)
		{
			if (entities instanceof LivingEntity)
			{
				LivingEntity entity = (LivingEntity) entities;
				Location dropsite = entity.getLocation();

				entity.getWorld().strikeLightningEffect(dropsite);
				entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1, 20 * (DURATION + level)));
				entity.damage(DAMAGE + level);
				entity.getWorld().playSound(entity.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
			}
		}
	}
}
