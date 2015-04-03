package src.ares.core.common;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitScheduler;

import src.ares.core.Main;

/**
 * Represents a simple listener wrapper with basic utilities.
 */
public abstract class Module implements Listener
{
	private String name;
	private boolean events;

	public Module(String name, boolean events)
	{
		this.name = name;
		this.events = events;
	}

	public Module(String name)
	{
		this(name, true);
	}

	public Module()
	{
		this("Default", true);
	}

	public String getModuleName()
	{
		return name;
	}

	public BukkitScheduler getScheduler()
	{
		return Bukkit.getScheduler();
	}

	public void registerEvents()
	{
		if (events)
			Bukkit.getPluginManager().registerEvents(this, Main.getPlugin());
	}

	public void unregisterEvents()
	{
		if (events)
			HandlerList.unregisterAll(this);
	}
}
