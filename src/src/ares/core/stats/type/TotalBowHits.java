package src.ares.core.stats.type;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import src.ares.core.client.Client;
import src.ares.core.stats.Statistic;

public class TotalBowHits extends Statistic
{
	public TotalBowHits()
	{
		super("Total Bow Hits");
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerShootBow(EntityDamageByEntityEvent event)
	{
		Entity entityVictim = event.getEntity();
		Entity damager = event.getDamager();

		if (entityVictim instanceof Player && damager instanceof Arrow)
		{
			Arrow arrow = (Arrow) damager;

			if (arrow.getShooter() instanceof Player)
			{
				Player attacker = (Player) arrow.getShooter();
				Client client = new Client(attacker);
				int updated = client.getManager().getStatistic(getTitle()) + 1;
				client.getManager().setStatistic(getTitle(), updated);
			}
		}
	}
}
