package src.ares.core.parkour;

import java.util.Random;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import src.ares.core.Main;
import src.ares.core.client.Client;
import src.ares.core.common.Module;
import src.ares.core.currency.type.AmbrosiaCurrency;
import src.ares.core.world.CoreWorld;
import src.ares.core.world.CoreWorldHub;
import src.ares.core.world.WorldManager;
import src.ares.core.world.WorldType;

public class ParkourListener extends Module
{
	private Random random = new Random();
	private Material reward = Material.CHEST;
	private int x1 = -6;
	private int x2 = -5;
	private int y = 72;
	private int z = -177;

	public ParkourListener()
	{
		super("Parkour");

		//decorateChests();
	}

	public void decorateChests()
	{
		for (CoreWorldHub hubs : WorldManager.getInstance().getHubWorlds())
		{
			final Location location1 = new Location(hubs.getWorld(), x1, y + 1, z);
			final Location location2 = new Location(hubs.getWorld(), x2, y + 1, z);

			final Block chest1 = hubs.getWorld().getBlockAt(location1);
			final Block chest2 = hubs.getWorld().getBlockAt(location2);

//			if (chest1.getType() == Material.AIR || chest2.getType() == Material.AIR)
//			{
//				location1.getBlock().setType(reward);
//				location2.getBlock().setType(reward);
//			}

			getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable()
			{
				public void run()
				{					
					for (int i = 0; i < 3; i++)
					{
						location1.getWorld().spigot().playEffect(location1, Effect.HAPPY_VILLAGER);
						location2.getWorld().spigot().playEffect(location2, Effect.HAPPY_VILLAGER);
					}
				}
			}, 20L, 20 * 3);
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		Client client = new Client(player);
		CoreWorld world = client.getCoreWorld();

		if (world.getWorldType() == WorldType.HUB)
		{
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK)
			{
				Block block = event.getClickedBlock();

				if (checkForRewardBlock(block))
				{
					if (client.getManager().hasCompletedHubParkour())
					{
						client.sendMessage(getModuleName(), "You have completed that challenge.");
					}
					else
					{
						completeParkourChallenge(client);
					}

					event.setCancelled(true);
				}
			}
		}
	}

	private void completeParkourChallenge(Client client)
	{
		client.sendRaw("&b&lYou completed the Hub Parkour Challenge!");
		client.addCurrency(new AmbrosiaCurrency(5000), true);
		client.getManager().setHubParkour(true);
		client.playSound(Sound.LEVEL_UP, 1.0F, 1.0F);
	}

	private boolean checkForRewardBlock(Block block)
	{
		if (block.getType() == reward)
		{
			if (block.getLocation().getY() == y && block.getLocation().getZ() == z)
			{
				if (block.getLocation().getX() == x1 || block.getLocation().getX() == x2)
				{
					return true;
				}
			}
		}

		return false;
	}
}
