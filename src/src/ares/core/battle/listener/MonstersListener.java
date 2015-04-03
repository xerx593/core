package src.ares.core.battle.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import src.ares.core.Main;
import src.ares.core.client.Client;
import src.ares.core.common.Module;
import src.ares.core.common.util.UtilEntity;
import src.ares.core.common.util.UtilString;
import src.ares.core.world.CoreWorld;
import src.ares.core.world.WorldManager;
import src.ares.core.world.WorldType;

public class MonstersListener extends Module
{
	private static MonstersListener instance = new MonstersListener();

	public static MonstersListener getInstance()
	{
		return instance;
	}

	private EntityType[] entities =
	{
	EntityType.ZOMBIE, EntityType.SKELETON, EntityType.SPIDER, EntityType.CREEPER, EntityType.WITCH, EntityType.SILVERFISH
	};

	private Random random = new Random();
	private HashMap<World, Integer> tasks = new HashMap<>();
	private HashMap<World, Boolean> started = new HashMap<>();

	public MonstersListener()
	{
		super("Battle");
	}

	@EventHandler
	public void onPlayerChangeWorld(PlayerChangedWorldEvent event)
	{
		Player player = event.getPlayer();
		Client client = new Client(player);

		World worldBefore = event.getFrom();
		World worldAfter = player.getWorld();

		CoreWorld coreWorldAfter = client.getCoreWorld();
		CoreWorld coreWorldBefore = WorldManager.getInstance().getWorld(worldBefore.getName());

		// Checking if the player joined a PVP world.

		if (coreWorldAfter.getWorldType() == WorldType.PVP)
		{
			// If he's alone, start spawning mobs.

			if (worldAfter.getPlayers().size() <= 1)
			{
				start(worldAfter, player);
			}
			else
			{
				stop(worldAfter);
			}
		}
		else
		{
			if (worldBefore != null && coreWorldBefore.getWorldType() == WorldType.PVP && worldBefore.getPlayers().size() == 0)
			{
				stop(worldBefore);
			}
		}

		if (coreWorldBefore.getWorldType() == WorldType.PVP)
		{
			Main.debug(worldBefore.getName() + " check");

			if (worldBefore.getPlayers().size() == 1)
			{
				Main.debug(worldBefore.getName() + " check passed");
				start(worldBefore, worldBefore.getPlayers().get(0));
			}
		}
	}

	private void start(final World world, final Player player)
	{
		Main.debug("Starting for " + player.getName() + ".");
		started.put(world, true);

		final int task = getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable()
		{
			public void run()
			{
				if (world.getPlayers().size() >= 2 || world.getPlayers().size() == 0)
				{
					stop(world);
					return;
				}

				if (world.getLivingEntities().size() <= 15)
				{
					Main.debug("Allowing mob spawn for < " + world.getLivingEntities().size() + ".");

					for (LivingEntity entity : spawnRandomMobs(player))
					{
						Main.debug("Spawning: " + UtilString.format(entity.getType().name()) + ".");

						if (entity instanceof Zombie)
						{
							UtilEntity.randomArmor(entity);
							UtilEntity.randomWeapon(entity);
						}
						else if (entity instanceof Skeleton)
						{
							UtilEntity.randomArmor(entity);
						}
					}
				}
				else
				{
					Main.debug("Mob spawn limit reached...");
				}
			}
		}, 20 * 10, 20 * 10);

		tasks.put(world, task);
	}

	private Location getNearLocation(Player player)
	{
		int x = random.nextInt(5) + 3;
		int z = random.nextInt(5) + 2;

		Location location = player.getLocation().add(x, 0, z);
		return location;
	}

	private List<LivingEntity> spawnRandomMobs(Player player)
	{
		List<LivingEntity> spawned = new ArrayList<>();

		for (int i = 0; i < (random.nextInt(5) + 2); i++)
		{
			Location location = getNearLocation(player);
			spawned.add((LivingEntity) location.getWorld().spawnEntity(location, entities[random.nextInt(entities.length)]));
		}

		return spawned;
	}

	private void stop(World world)
	{
		if (world == null)
			return;

		if (started.get(world))
		{
			Main.debug("Stopping " + world.getName() + ".");
			started.put(world, false);
			getScheduler().cancelTask(tasks.get(world));
			Main.debug("Cancelling task: " + tasks.get(world));

			for (LivingEntity entity : world.getLivingEntities())
			{
				if (entity instanceof Player)
					continue;

				Main.debug("Removing " + entity.getCustomName() + ".");
				entity.remove();
			}
		}
	}
}
