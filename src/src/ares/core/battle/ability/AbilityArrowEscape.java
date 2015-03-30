package src.ares.core.battle.ability;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import src.ares.core.battle.kit.Kit;
import src.ares.core.client.Client;
import src.ares.core.common.Cooldown;
import src.ares.core.common.util.Chat;
import src.ares.core.common.util.UtilPlayer;

public class AbilityArrowEscape extends HotkeyAbility
{
	private static int DURATION = 5;
	private Location landing;

	public AbilityArrowEscape(Kit kit)
	{
		super(kit, "Arrow Escape", Material.BOW);
	}

	@Override
	public String getTip(Client client)
	{
		int level = level(client.getPlayer());
		return "Press " + Chat.tool("Drop Key") + " to activate, when the arrow lands, you teleport to that location with " + Chat.tool("Slowness I") + " for " + Chat.time(DURATION - level + " seconds") + ".";
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void toggleAbility(ProjectileHitEvent event)
	{
		Projectile projectile = event.getEntity();

		if (!(projectile instanceof Arrow))
			return;

		Arrow arrow = (Arrow) projectile;

		if (!(arrow.getShooter() instanceof Player))
			return;

		Player player = (Player) arrow.getShooter();
		this.landing = arrow.getLocation();

		if (validate(player) && hasToggledUse(player))
		{
			if (player.getLocation().distance(landing) >= 30)
			{
				player.sendMessage(Chat.format(getModuleName(), "The shot landed too far away."));
			}
			else
			{
				useAbility(player);
			}
		}
	}

	@Override
	public void useAbility(Player player)
	{
		if (UtilPlayer.isOnWater(player))
			return;

		int level = level(player);

		if (!Cooldown.create(player, 10, getName(), true))
		{
			showUse(player);
			player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * (DURATION - level), 3));
			player.teleport(landing);
			player.getWorld().playSound(landing, Sound.ARROW_HIT, 1.0F, 1.3F);
		}
	}
}
