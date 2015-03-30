package src.ares.core.common;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import src.ares.core.Main;
import src.ares.core.common.util.Chat;

public class Cooldown
{
	private static HashMap<Player, Integer> cooldownTime = new HashMap<>();

	private static HashMap<Player, BukkitRunnable> cooldownTask = new HashMap<>();

	public static boolean create(final Player player, int cooldown, final String use, final boolean inform)
	{
		if (cooldownTime.containsKey(player))
		{
			int remaining = cooldownTime.get(player);

			if (remaining <= 1)
				player.sendMessage(Chat.format("Cooldown", "You cannot use " + Chat.tool(use) + " for " + Chat.time(remaining + " second") + "."));
			else player.sendMessage(Chat.format("Cooldown", "You cannot use " + Chat.tool(use) + " for " + Chat.time(remaining + " seconds") + "."));
			return true;
		}

		cooldownTime.put(player, cooldown);

		cooldownTask.put(player, new BukkitRunnable()
		{
			@Override
			public void run()
			{
				cooldownTime.put(player, cooldownTime.get(player) - 1);
				if (cooldownTime.get(player) == 0)
				{
					cooldownTime.remove(player);
					cooldownTask.remove(player);

					if (inform)
						player.sendMessage(Chat.format("Cooldown", "You can now use " + Chat.tool(use) + " again."));

					cancel();
				}
			}
		});

		cooldownTask.get(player).runTaskTimer(Main.getPlugin(), 20L, 20L);
		return false;
	}

	public static boolean create(final Player player, int cooldown, final boolean inform)
	{
		if (cooldownTime.containsKey(player))
		{
			int remaining = cooldownTime.get(player);

			if (remaining <= 1)
				player.sendMessage(Chat.format("Cooldown", "You cannot use that for " + Chat.time(remaining + " second") + "."));
			else player.sendMessage(Chat.format("Cooldown", "You cannot use that for " + Chat.time(remaining + " seconds") + "."));
			return true;
		}

		cooldownTime.put(player, cooldown);

		cooldownTask.put(player, new BukkitRunnable()
		{
			@Override
			public void run()
			{
				cooldownTime.put(player, cooldownTime.get(player) - 1);

				if (cooldownTime.get(player) == 0)
				{
					cooldownTime.remove(player);
					cooldownTask.remove(player);

					if (inform)
						player.sendMessage(Chat.format("Cooldown", "You can now use that again."));

					cancel();
				}
			}
		});

		cooldownTask.get(player).runTaskTimer(Main.getPlugin(), 20L, 20L);
		return false;
	}
}
