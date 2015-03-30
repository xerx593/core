package src.ares.core.stats.type;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import src.ares.core.client.Client;
import src.ares.core.stats.Statistic;

public class TotalKillsStatistic extends Statistic
{
	public TotalKillsStatistic()
	{
		super("Total Kills");
	}

	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent event)
	{
		Player killer = event.getEntity().getKiller();

		if (killer == null)
			return;

		Client client = new Client(killer);
		client.getManager().setStatistic(getTitle(), client.getManager().getStatistic(getTitle()) + 1);
	}
}
