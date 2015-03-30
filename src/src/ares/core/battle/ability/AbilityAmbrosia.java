package src.ares.core.battle.ability;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import src.ares.core.battle.kit.Kit;
import src.ares.core.client.Client;
import src.ares.core.common.util.Chat;

public class AbilityAmbrosia extends Ability
{
	private static int DURATION = 5;

	public AbilityAmbrosia(Kit kit)
	{
		super(kit, "Ambrosia");
	}

	@Override
	public void useAbility(Player player)
	{
		Client client = new Client(player);
		int level = level(player);
		client.playLocationSound(Sound.BURP, 1.0F, 1.0F);
		client.unload();

		player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * (DURATION + level), 2));
		showUse(player);
	}

	@Override
	public String getTip(Client client)
	{
		int level = level(client.getPlayer());
		return "Recieve " + Chat.ability("Speed II") + " on player kill for " + Chat.time((DURATION + level) + " seconds") + " to get away easily and fast.";
	}

	@EventHandler
	public void toggleAbility(PlayerDeathEvent event)
	{
		Player damager = event.getEntity().getKiller();

		if (validate(damager))
			useAbility(damager);
	}
}
