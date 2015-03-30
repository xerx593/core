package src.ares.core.gadget.type;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import src.ares.core.client.Rank;
import src.ares.core.currency.type.AmbrosiaCurrency;
import src.ares.core.gadget.HandGadget;

public class GadgetHandSnowballRifle extends HandGadget
{
	public GadgetHandSnowballRifle()
	{
		super("Snowball Rifle Gadget", Material.DIAMOND_BARDING, Rank.PLAYER, new AmbrosiaCurrency(1));
	}

	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event)
	{
		if (event.getEntity() instanceof Snowball)
		{
			Snowball snowball = (Snowball) event.getEntity();
			snowball.getWorld().playSound(snowball.getLocation(), Sound.DIG_SNOW, 0.7F, 1.3F);
			snowball.getWorld().playEffect(snowball.getLocation(), Effect.TILE_BREAK, 80);
			snowball.getWorld().playEffect(snowball.getLocation(), Effect.TILE_BREAK, 80);

			for (Entity entity : snowball.getNearbyEntities(1.3, 1.3, 1.3))
			{
				if (entity instanceof LivingEntity)
				{
					LivingEntity living = (LivingEntity) entity;
					living.setVelocity(event.getEntity().getVelocity().normalize().multiply(0.3).setY(0.5));
					living.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 0));
				}
			}
		}
	}

	@Override
	public void useHandGadget(Player player)
	{
		Snowball snowball = player.launchProjectile(Snowball.class);
		snowball.setVelocity(player.getLocation().subtract(0, 1, 0).getDirection().normalize().multiply(1.5));

		player.getWorld().playSound(player.getLocation(), Sound.DIG_SNOW, 0.5F, 1.3F);
	}
}
