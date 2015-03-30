package src.ares.core.portal;

import java.util.ArrayList;

import src.ares.core.portal.type.PvPPortal;

public class PortalManager
{
	private static PortalManager instance = new PortalManager();

	public static PortalManager getInstance()
	{
		return instance;
	}

	// Array with the registered Portals. //
	private ArrayList<Portal> portalBag;

	public void createPortals()
	{
		portalBag = new ArrayList<>();

		portalBag.add(new PvPPortal());
		// portalBag.add(new SurvivalPortal());

		for (Portal portal : portalBag)
		{
			portal.registerEvents();
		}
	}

	/**
	 * Gets a specific portal by name.
	 * 
	 * @param name Then name of the portal.
	 * @return Portal
	 */
	public Portal getPortal(String name)
	{
		for (Portal portal : portalBag)
		{
			if (portal.getName().contains(name) || portal.getName().startsWith(name)) { return portal; }
		}

		return null;
	}

	/**
	 * Gets the Array of registered Portals.
	 * 
	 * @return ArrayList<Portal>
	 */
	public ArrayList<Portal> getPortalBag()
	{
		return portalBag;
	}
}
