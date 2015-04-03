package src.ares.core.gadget.type.premium;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import src.ares.core.Main;
import src.ares.core.client.Rank;
import src.ares.core.gadget.GadgetManager;
import src.ares.core.gadget.ParticleGadget;

public class GadgetParticleElite extends ParticleGadget
{
	private GadgetManager manager = GadgetManager.getInstance();

	public GadgetParticleElite()
	{
		super("Elite Particle", Material.WOOL, Rank.ELITE);
		setData((byte) 11);
	}

	public void useParticleGadget(Player player)
	{
		int task = new GadgetParticleFlameRingAction(player).runTaskTimer(Main.getPlugin(), 0L, 1L).getTaskId();
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
			player.getWorld().spigot().playEffect(player.getLocation().add(0, 1, 0), Effect.TILE_BREAK, 22, 0, 0.5F, 0.5F, 0.5F, 0.1F, 2, 15);
		}
	}
}
