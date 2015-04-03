package src.ares.core.gadget.type;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import src.ares.core.Main;
import src.ares.core.client.Rank;
import src.ares.core.currency.type.AmbrosiaCurrency;
import src.ares.core.gadget.GadgetManager;
import src.ares.core.gadget.ParticleGadget;

public class GadgetParticleBreedingLove extends ParticleGadget
{
	private GadgetManager manager = GadgetManager.getInstance();

	public GadgetParticleBreedingLove()
	{
		super("Breeding Love Particle", Material.REDSTONE, Rank.PLAYER);
		setShouldBeOwned(true, new AmbrosiaCurrency(5000));
	}

	public void useParticleGadget(Player player)
	{
		int task = new GadgetParticleFlameRingAction(player).runTaskTimer(Main.getPlugin(), 0L, 5L).getTaskId();
		manager.getParticleTasks().put(player, task);
	}

	public class GadgetParticleFlameRingAction extends BukkitRunnable
	{
		private Player player;

		public GadgetParticleFlameRingAction(Player player)
		{
			this.player = player;
		}

		@Override
		public void run()
		{
			player.getWorld().spigot().playEffect(player.getLocation().add(0, .5, 0), Effect.HEART, 0, 0, .5F, .5F, .5F, 15, 1, 15);
		}
	}
}
