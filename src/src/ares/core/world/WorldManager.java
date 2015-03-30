package src.ares.core.world;

import java.util.ArrayList;
import java.util.List;

import src.ares.core.Main;

public class WorldManager
{
	private static WorldManager instance = new WorldManager();

	public static WorldManager getInstance()
	{
		return instance;
	}

	private ArrayList<CoreWorld> worldBag;

	public void createWorlds()
	{
		this.worldBag = new ArrayList<>();

		worldBag.add(new CoreWorldHub("hub-1"));
		worldBag.add(new CoreWorldHub("hub-2"));
		worldBag.add(new CoreWorldHub("hub-3"));

		worldBag.add(new CoreWorldBattle("pvp-1"));
		worldBag.add(new CoreWorldBattle("pvp-2"));
		worldBag.add(new CoreWorldBattle("pvp-3"));

		for (CoreWorld world : worldBag)
		{
			Main.debug("Creating " + world.getName() + ".");
		}
	}

	public List<CoreWorldHub> getHubWorlds()
	{
		List<CoreWorldHub> hubs = new ArrayList<>();

		for (CoreWorld world : worldBag)
		{
			if (world.getWorldType() == WorldType.HUB)
			{
				hubs.add((CoreWorldHub) world);
			}
		}

		return hubs;
	}

	public List<CoreWorldBattle> getPvPWorlds()
	{
		List<CoreWorldBattle> pvps = new ArrayList<>();

		for (CoreWorld world : worldBag)
		{
			if (world.getWorldType() == WorldType.PVP)
			{
				pvps.add((CoreWorldBattle) world);
			}
		}

		return pvps;
	}

	/**
	 * Returns a registered world by name.
	 * 
	 * @param name
	 *            The name of the world.
	 * @return CoreWorld
	 */
	public CoreWorld getWorld(String name)
	{
		for (CoreWorld world : worldBag)
		{
			if (world.getRealName().contains(name))
				return world;
		}

		return null;
	}

	/**
	 * Returns the list of the registered worlds.
	 * 
	 * @return ArrayList<CoreWorld>
	 */
	public ArrayList<CoreWorld> getWorldBag()
	{
		return worldBag;
	}
}
