package src.ares.core.battle.ability;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import src.ares.core.battle.kit.Kit;
import src.ares.core.client.Client;
import src.ares.core.common.util.Chat;
import src.ares.core.common.util.UtilPlayer;

public class AbilityWaterDrift extends Ability
{
	private static int CHANCE = 5;

	public AbilityWaterDrift(Kit kit)
	{
		super(kit, "Water Drift");
	}

	@Override
	public String getTip(Client client)
	{
		int level = level(client.getPlayer());
		return "Recieve " + Chat.ability("Power I") + " and " + Chat.ability("Breathing I") + " while swimming for " + Chat.time((CHANCE + level) + " seconds") + ".";
	}

	@EventHandler
	public void toggleAbility(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();

		if (validate(player))
			useAbility(player);
	}

	@Override
	public void useAbility(Player player)
	{
		Client client = new Client(player);
		int level = level(player);

		if (UtilPlayer.isOnWater(player))
		{
			player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 99999, level));
			player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 99999, level));
			player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 99999, level));
		}
		else
		{
			player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
			player.removePotionEffect(PotionEffectType.WATER_BREATHING);
			player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
		}

		client.unload();
	}
}
