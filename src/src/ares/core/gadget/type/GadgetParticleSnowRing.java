package src.ares.core.gadget.type;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import src.ares.core.Main;
import src.ares.core.client.Rank;
import src.ares.core.effect.Animation;
import src.ares.core.gadget.GadgetManager;
import src.ares.core.gadget.ParticleGadget;

public class GadgetParticleSnowRing extends ParticleGadget
{
	private GadgetManager manager = GadgetManager.getInstance();

	public GadgetParticleSnowRing()
	{
		super("Snow Ring Particle", Material.SNOW_BALL, Rank.PLAYER);
	}

	public void useParticleGadget(Player player)
	{
		int task = new GadgetParticleSnowRingAction(player).runTaskTimer(Main.getPlugin(), 0L, 2L).getTaskId();
		manager.getParticleTasks().put(player, task);
	}

	public class GadgetParticleSnowRingAction extends BukkitRunnable
	{
		private Player player;

		public GadgetParticleSnowRingAction(Player player)
		{
			this.player = player;
		}

		@Override
		public void run()
		{
			for (Location location : Animation.getCircle(player.getLocation().add(0, 1, 0), 1, 20))
			{
				player.getWorld().spigot().playEffect(location, Effect.SNOW_SHOVEL, 0, 0, 0.0F, 0.0F, 0.0F, 0.1F, 2, 15);
			}
		}
	}
}
