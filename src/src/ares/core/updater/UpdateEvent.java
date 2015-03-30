package src.ares.core.updater;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UpdateEvent extends Event
{
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlerList()
	{
		return handlers;
	}

	public UpdateEvent()
	{
		/**
		 * Should add more. Nothing at the moment.
		 */
	}

	@Override
	public HandlerList getHandlers()
	{
		return handlers;
	}
}
