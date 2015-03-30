package src.ares.core.portal.type;

import org.bukkit.Location;

import src.ares.core.portal.Portal;
import src.ares.core.world.CoreWorldBattle;
import src.ares.core.world.CoreWorldHub;
import src.ares.core.world.WorldManager;

public class PvPPortal extends Portal
{
	private static CoreWorldHub hub = (CoreWorldHub) WorldManager.getInstance().getWorld("hub-1");
	private static CoreWorldBattle pvp = (CoreWorldBattle) WorldManager.getInstance().getWorld("pvp-1");

	public PvPPortal()
	{
		super("Battle Portal", new Location(hub.getWorld(), 48, 47, -3), new Location(hub.getWorld(), 48, 49, -2), pvp.getSpawnLocation());
	}

	@Override
	public void onEnterPortal()
	{
		//
	}
}
