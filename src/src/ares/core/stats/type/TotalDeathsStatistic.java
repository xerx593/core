package src.ares.core.stats.type;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import src.ares.core.client.Client;
import src.ares.core.stats.Statistic;

public class TotalDeathsStatistic extends Statistic
{
	public TotalDeathsStatistic()
	{
		super("Total Deaths");
	}

	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent event)
	{
		Player victim = event.getEntity();

		if (victim == null)
			return;

		Client client = new Client(victim);
		int updated = client.getManager().getStatistic(getTitle()) + 1;
		client.getManager().setStatistic(getTitle(), updated);
	}
}
