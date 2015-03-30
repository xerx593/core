package src.ares.core.battle.ability;

import java.util.Random;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import src.ares.core.battle.kit.Kit;
import src.ares.core.client.Client;
import src.ares.core.common.util.Chat;

public class AbilityConfusion extends Ability
{
	private Player victim;
	private Random random;

	private static int CHANCE = 10;
	private static int DURATION = 3;

	public AbilityConfusion(Kit kit)
	{
		super(kit, "Confusion");
		this.random = new Random();
	}

	@Override
	public String getTip(Client client)
	{
		int level = level(client.getPlayer());
		return "Target has a chance of " + Chat.time((CHANCE + (level * 10)) + "%") + " to recieve " + Chat.ability("Blindness I") + " for " + Chat.time((DURATION + level) + " seconds") + " on every hit.";
	}

	@EventHandler
	public void toggleAbility(EntityDamageByEntityEvent event)
	{
		if (event.getEntity() instanceof Player && event.getDamager() instanceof Player)
		{
			Player victim = (Player) event.getEntity();
			Player damager = (Player) event.getDamager();
			this.victim = victim;

			if (validate(damager))
				useAbility(damager);
		}
	}

	@Override
	public void useAbility(Player player)
	{
		Client client = new Client(victim);
		int level = level(player);
		int randomizer = random.nextInt(100);

		if (randomizer <= CHANCE + (level * 10))
		{
			victim.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * (DURATION + level), 0));

			client.playLocationSound(Sound.AMBIENCE_CAVE, 1.0F, 1.0F);
			client.playLocationSound(Sound.EXPLODE, 1.0F, 0.7F);
			client.unload();
		}
	}
}
