package src.ares.core.battle.ability;

import java.util.Random;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import src.ares.core.battle.kit.Kit;
import src.ares.core.client.Client;
import src.ares.core.common.util.Chat;

public class AbilityFireCharge extends Ability
{
	private static int CHANCE = 10;
	private Random random = new Random();

	public AbilityFireCharge(Kit kit)
	{
		super(kit, "Fire Charge");
	}

	@Override
	public String getTip(Client client)
	{
		int level = level(client.getPlayer());
		return "Hitting with " + Chat.tool("Blaze Rod") + " has a " + Chat.tool((CHANCE + (level * 10)) + "%") + " chance to ignite the player.";
	}

	@EventHandler
	public void apolloStickDamage(EntityDamageByEntityEvent event)
	{
		if (event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity)
		{
			Player attacker = (Player) event.getDamager();
			LivingEntity victim = (LivingEntity) event.getEntity();

			if (validate(attacker))
			{
				int level = level(attacker);

				if (attacker.getItemInHand().getType() == Material.BLAZE_ROD)
				{
					int randomizer = random.nextInt(100);

					if (randomizer <= CHANCE + (level * 10))
					{
						victim.setFireTicks(20 * 3);
						victim.getWorld().playEffect(victim.getLocation().add(0, 1, 0), Effect.BOW_FIRE, 0);
					}
				}
			}
		}
	}

	@Override
	public void useAbility(Player player)
	{
		//
	}
}
