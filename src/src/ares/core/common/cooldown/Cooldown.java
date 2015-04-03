package src.ares.core.common.cooldown;

import java.text.DecimalFormat;
import java.util.HashMap;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import src.ares.core.Main;
import src.ares.core.common.util.Chat;

public class Cooldown
{
	private static HashMap<CooldownData, Player> timers = new HashMap<>();

	public static boolean create(final Player player, final double delay, final String use, final boolean inform)
	{
		for (CooldownData data : timers.keySet())
		{
			for (Player players : timers.values())
			{
				if (timers.get(data).equals(players))
				{
					if (data.getUse().contains(use) && timers.get(data).equals(player))
					{
						inform(player, data);
						return true;
					}
				}
			}
		}

		final CooldownData data = new CooldownData(use, delay);
		timers.put(data, player);

		BukkitRunnable runnable = new BukkitRunnable()
		{
			@Override
			public void run()
			{
				double elapsed = data.getDelay() - 0.1;
				data.setDelay(elapsed);

				if (elapsed <= 0.0)
				{
					player.sendMessage(Chat.format("Cooldown", "You can use " + Chat.tool(use) + " again."));
					player.playSound(player.getLocation(), Sound.CLICK, 0.5F, 1.5F);
					timers.remove(data);
					cancel();
				}
			}
		};

		runnable.runTaskTimer(Main.getPlugin(), 1L, 1L);
		return false;
	}

	private static void inform(Player player, CooldownData data)
	{
		DecimalFormat formatter = new DecimalFormat("#.##");
		
		if (data.getDelay() <= 1.0)
		{
			player.sendMessage(Chat.format("Cooldown", "You cannot use " + Chat.tool(data.getUse()) + " for " + Chat.time(formatter.format(data.getDelay()).replace(',', '.') + " second") + "."));
		}
		else
		{
			player.sendMessage(Chat.format("Cooldown", "You cannot use " + Chat.tool(data.getUse()) + " for " + Chat.time(formatter.format(data.getDelay()).replace(',', '.') + " seconds") + "."));
		}		
	}
}
