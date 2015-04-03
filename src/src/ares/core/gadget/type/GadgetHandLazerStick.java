package src.ares.core.gadget.type;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import src.ares.core.Main;
import src.ares.core.client.Rank;
import src.ares.core.currency.type.AmbrosiaCurrency;
import src.ares.core.gadget.HandGadget;

public class GadgetHandLazerStick extends HandGadget
{
	public GadgetHandLazerStick()
	{
		super("Laser Stick Gadget", Material.BLAZE_ROD, Rank.PLAYER, new AmbrosiaCurrency(20));
	}

	@Override
	public void useHandGadget(Player player)
	{
		final Entity entity = player.getWorld().dropItem(player.getLocation().add(0, 1, 0), new ItemStack(Material.EYE_OF_ENDER));
		entity.setVelocity(player.getLocation().getDirection().normalize().multiply(3.5));

		player.getWorld().playSound(player.getLocation(), Sound.NOTE_PIANO, 1.0F, 1.5F);

		final int task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable()
		{
			@Override
			public void run()
			{
				if (entity.isOnGround())
				{
					entity.remove();
				}
				if (!entity.isDead())
				{
					entity.getWorld().playEffect(entity.getLocation(), Effect.HAPPY_VILLAGER, 5);
					entity.getWorld().playEffect(entity.getLocation(), Effect.COLOURED_DUST, 5);
				}
			}
		}, 1L, 1L);

		new GadgetHandLazerPerform(task, entity).runTaskLater(Main.getPlugin(), 30L);
	}

	public class GadgetHandLazerPerform extends BukkitRunnable
	{
		private int task;
		private Entity entity;

		public GadgetHandLazerPerform(int task, Entity entity)
		{
			this.task = task;
			this.entity = entity;
		}

		@Override
		public void run()
		{
			Bukkit.getScheduler().cancelTask(task);

			if (!entity.isDead())
			{
				Firework firework = entity.getWorld().spawn(entity.getLocation(), Firework.class);
				FireworkEffect effect = FireworkEffect.builder().trail(true).flicker(true).withColor(Color.GREEN).with(Type.BALL).build();
				FireworkMeta fireworkmeta = firework.getFireworkMeta();
				fireworkmeta.clearEffects();
				fireworkmeta.addEffect(effect);
				Field field;

				try
				{
					field = fireworkmeta.getClass().getDeclaredField("power");
					field.setAccessible(true);
					field.set(fireworkmeta, -1);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

				firework.setFireworkMeta(fireworkmeta);
			}
			entity.remove();
		}
	}
}
