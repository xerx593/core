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

public class AbilityRevenge extends Ability
{
	private static int DURATION = 3;

	public AbilityRevenge(Kit kit)
	{
		super(kit, "Revenge");
	}

	@Override
	public String getTip(Client client)
	{
		int level = level(client.getPlayer());
		return "Recieve " + Chat.ability("Resistance I") + " on player kill for " + Chat.time((DURATION + level) + " seconds") + ".";
	}

	@EventHandler
	public void toggleAbility(PlayerDeathEvent event)
	{
		Player killer = event.getEntity().getKiller();

		if (validate(killer))
			useAbility(killer);
	}

	@Override
	public void useAbility(Player player)
	{
		Client client = new Client(player);
		int level = level(player);
		player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * (DURATION + level), 1));
		client.playLocationSound(Sound.ZOMBIE_INFECT, 1.0F, 1.5F);
		showUse(player);
	}
}
