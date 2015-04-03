package src.ares.core.battle.event;

import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;

import src.ares.core.Main;
import src.ares.core.common.Module;
import src.ares.core.nms.Title;
import src.ares.core.world.CoreWorldBattle;
import src.ares.core.world.WorldManager;

public class MobEvent extends Module
{
	private Random random = new Random();
	private boolean running = false;
	private CoreWorldBattle pvp = (CoreWorldBattle) WorldManager.getInstance().getWorld("pvp-1");

	public MobEvent()
	{
		super("Event");

		initialize();
	}

	private void initialize()
	{
		Main.debug("Starting Event Timer");
		getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable()
		{
			public void run()
			{
				if (running == true)
					return;

				// pvp = manager.getPvPWorlds().get(random.nextInt(manager.getPvPWorlds().size()));

				Main.debug("Mob Event: Selected " + pvp.getName() + " for check.");

				if (pvp.onlinePlayers() >= 1)
				{
					running = true;
					startBattle();
				}
			}
		}, 20 * 60, 20 * 60); // 10 Minutes
	}

	private void startBattle()
	{
		if (running == true)
		{
			Main.debug("Mob Event: Starting event on " + pvp.getName() + ".");
			List<Player> players = pvp.getWorld().getPlayers();

			for (Player player : players)
			{
				Title title = new Title("Monster Attack", "Creatures from the underworld arrived!", 2, 3, 2);
				title.setTitleColor(ChatColor.RED);
				title.send(player);

				player.playSound(player.getLocation(), Sound.ENDERDRAGON_DEATH, 1.0F, 1.0F);

				for (int i = 0; i < random.nextInt(7) + 3; i++)
				{
					spawnMobs();
				}
			}
		}
	}

	private void spawnMobs()
	{
		getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable()
		{
			public void run()
			{
				Zombie zombie = (Zombie) pvp.getWorld().spawnEntity(pvp.getSpawnLocation(), EntityType.ZOMBIE);
				Skeleton skeleton = (Skeleton) pvp.getWorld().spawnEntity(pvp.getSpawnLocation(), EntityType.SKELETON);
				Creeper creeper = (Creeper) pvp.getWorld().spawnEntity(pvp.getSpawnLocation(), EntityType.CREEPER);

				zombie.setCustomNameVisible(true);
				skeleton.setCustomNameVisible(true);
				creeper.setCustomNameVisible(true);

				zombie.setCustomName(ChatColor.RED + "Underworld Zombie");
				skeleton.setCustomName(ChatColor.RED + "Underworld Skeleton");
				creeper.setCustomName(ChatColor.RED + "Underworld Creeper");
			}
		}, 20 * 2);
	}
}
