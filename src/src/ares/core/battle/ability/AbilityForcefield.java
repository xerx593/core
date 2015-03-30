package src.ares.core.battle.ability;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import src.ares.core.battle.kit.Kit;
import src.ares.core.client.Client;
import src.ares.core.common.util.Chat;

public class AbilityForcefield extends Ability
{
	private static int DURATION = 3;

	public AbilityForcefield(Kit kit)
	{
		super(kit, "Forcefield");
	}

	@Override
	public String getTip(Client client)
	{
		int level = level(client.getPlayer());
		return "While walking, nearby players recieve " + Chat.tool("Weakness I") + " for " + Chat.time((DURATION + level) + " seconds") + ".";
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event)
	{
		Player target = event.getPlayer();

		for (Entity nearby : target.getNearbyEntities(2.0, 2.0, 2.0))
		{
			if (nearby instanceof LivingEntity)
			{
				LivingEntity attacker = (LivingEntity) nearby;

				if (validate(target))
				{
					int level = level(target);
					attacker.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, DURATION + level, 0));
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
