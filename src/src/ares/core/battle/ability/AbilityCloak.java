package src.ares.core.battle.ability;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import src.ares.core.Main;
import src.ares.core.battle.kit.Kit;
import src.ares.core.client.Client;
import src.ares.core.common.cooldown.Cooldown;
import src.ares.core.common.util.Chat;
import src.ares.core.common.util.ItemStackFactory;

public class AbilityCloak extends Ability
{
	private static final int DURATION = 3;
	private static final Sound SOUND = Sound.DIG_GRASS;

	public AbilityCloak(Kit kit)
	{
		super(kit, "Cloak");
	}

	@Override
	public String getTip(Client client)
	{
		int level = level(client.getPlayer());
		return "Press " + Chat.tool("Right-Click") + " with " + Chat.tool("Gunpowder") + " to cloak for " + Chat.time((DURATION + level) + " seconds") + ".";
	}

	@EventHandler
	public void disableCloakDamage(EntityDamageByEntityEvent event)
	{
		if (event.getEntity() instanceof Player && event.getDamager() instanceof Player)
		{
			Player victim = (Player) event.getEntity();
			Player attacker = (Player) event.getDamager();

			// Make sure the player can't hit a target while cloaked.

			if (validate(attacker) && !victim.canSee(attacker))
			{
				attacker.sendMessage(Chat.format(getModuleName(), "You cannot deal damage while cloaked."));
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void toggleAbility(PlayerInteractEvent event)
	{
		if (validate(event.getPlayer()))
		{
			Player player = event.getPlayer();

			if (player.getItemInHand().getType() != Material.SULPHUR)
				return;

			if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
			{
				event.setCancelled(true);

				if (player.getItemInHand().getAmount() > 1)
				{
					player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
				}
				else
				{
					player.setItemInHand(ItemStackFactory.getFactory().create(Material.AIR));
				}

				useAbility(player);
			}
		}
	}

	@Override
	public void useAbility(final Player player)
	{
		int level = level(player);

		if (!Cooldown.create(player, 10, getName(), true))
		{
			player.getWorld().createExplosion(player.getLocation(), 0);
			player.getWorld().playSound(player.getLocation(), SOUND, 1.0F, 1.0F);

			// We're adding an invisibility anyway so the others can see particles moving.

			player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * (DURATION + level), 0));

			showUse(player);

			// Hide the player so it doesn't show it's armor while moving.

			for (final Player players : player.getWorld().getPlayers())
			{
				players.hidePlayer(player);
			}

			// Make the player visible after the duration specified.

			getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable()
			{
				@Override
				public void run()
				{
					for (final Player players : player.getWorld().getPlayers())
					{
						players.showPlayer(player);
					}

					player.sendMessage(Chat.format(getModuleName(), "You are now visible again."));
				}
			}, 20 * (DURATION + level));

			// Adding blindness effect to nearby players.

			for (Entity near : player.getNearbyEntities(3D, 3D, 3D))
			{
				if (!(near instanceof Player))
					return;

				Player nearPlayer = (Player) near;
				nearPlayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * (DURATION + level), 0));
			}
		}
	}
}
