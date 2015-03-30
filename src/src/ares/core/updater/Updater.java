package src.ares.core.updater;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import src.ares.core.Main;

public class Updater extends BukkitRunnable
{
	@SuppressWarnings("deprecation")
    public Updater()
	{
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), this, 0L, 20L);
	}

	@Override
	public void run()
	{
		UpdateEvent event = new UpdateEvent();
		Bukkit.getPluginManager().callEvent(event);
	}
}
