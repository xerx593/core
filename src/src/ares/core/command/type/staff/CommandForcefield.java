package src.ares.core.command.type.staff;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.util.Vector;

import src.ares.core.Main;
import src.ares.core.client.Client;
import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;

public class CommandForcefield extends CoreCommand
{
	ArrayList<Player> toggled = new ArrayList<Player>();
	double forceStartRange = 3;

	public CommandForcefield()
	{
		super("forcefield", new String[] {}, 0, Rank.ADMIN, null);
	}

	@Override
	public void execute()
	{ // Add the method up here if it's in the toggled

		Client sender = getClient();
		Player sender1 = getClient().getPlayer();

		if (!(toggled.contains(sender1)))
		{
			sender.sendMessage("Forcefield", "You have enabled forcefield.");
			toggled.add(sender1);
		}
		else if (toggled.contains(sender1))
		{
			toggled.remove(sender1);
			sender.sendMessage("Forcefield", "You have disabled forcefield.");
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e)
	{
		Player player = e.getPlayer();

		if (toggled.contains(player))
		{
			toggled.remove(player);
		}
	}

	@EventHandler
	public void onPluginEnable(PluginEnableEvent e)
	{
		toggledMuch();
	}

	@SuppressWarnings("deprecation")
	public void toggledMuch()
	{
		Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(Main.getPlugin(), new Runnable()
		{
			@Override
			public void run()
			{
				if (getClient() == null)
					return;

				Player player = getClient().getPlayer();

				if (toggled.contains(player))
				{
					List<Entity> nearEntities = player.getNearbyEntities(forceStartRange, forceStartRange, forceStartRange);

					for (Entity AnimalandPlayer : nearEntities)
					{
						if (AnimalandPlayer instanceof Player)
						{
							Vector direction = AnimalandPlayer.getLocation().toVector().normalize().subtract(player.getLocation().toVector().normalize());
							AnimalandPlayer.getWorld().playSound(AnimalandPlayer.getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 1.0F);
							AnimalandPlayer.setVelocity(direction.multiply(20).setY(.85));

						}
					}
				}

			}

		}, 10, 10); // This should work now if I did the PluginEnableEvent correctly :P
	}
}