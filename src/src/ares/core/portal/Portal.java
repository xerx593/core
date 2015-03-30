package src.ares.core.portal;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerPortalEvent;

import src.ares.core.Main;
import src.ares.core.common.Module;
import src.ares.core.common.util.Chat;
import src.ares.core.common.util.UtilLocation;

/**
 * Portals are used to teleport players to a specific location of a server world.
 * Currently, this class only supports 4x2 portal frames.
 * The method onEnterPortal() is triggered <b>before</b> the player gets teleported to the destination.
 */
public abstract class Portal extends Module
{
	public static Player getPlayer()
	{
		return player;
	}

	// The bottom left corner of the portal. //
	private Location corner1;

	// The top right corner of the portal. //
	private Location corner2;

	// The destination of the portal. //
	private Location to;

	// The name of the portal. //
	private String name;

	private static Player player;

	// The sound used when the player walks through the portal. //
	private static final Sound PORTAL_TRAVEL_SOUND = Sound.PORTAL_TRAVEL;

	public Portal(String portalName, Location fromCorner1, Location fromCorner2, Location toLocation)
	{
		super("Portal");

		name = portalName;
		corner1 = fromCorner1;
		corner2 = fromCorner2;
		to = toLocation;
	}

	/**
	 * Gets bottom left corner of the portal.
	 * 
	 * @return Location
	 */
	public Location getBottomLeft()
	{
		return corner1;
	}

	/**
	 * Gets the name of the portal.
	 * 
	 * @return String
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Gets the destination of the portal.
	 * 
	 * @return Location
	 */
	public Location getTo()
	{
		return to;
	}

	/**
	 * Gets top right corner of the portal.
	 * 
	 * @return Location
	 */
	public Location getTopRight()
	{
		return corner2;
	}

	/**
	 * Triggered before the player teleports to the destination.
	 */
	public abstract void onEnterPortal();

	@EventHandler
	public void onPlayerEnterPortal(PlayerPortalEvent event)
	{
		Main.debug("Here 1");
		Player player = event.getPlayer();
		Portal.player = player;

		if (UtilLocation.inside(player.getLocation(), corner1, corner2))
		{
			Main.debug("Here 2");
			onEnterPortal();

			player.teleport(to);
			player.sendMessage(Chat.format(getModuleName(), "You have used the " + Chat.tool(name) + "."));
			player.getWorld().playSound(event.getFrom(), PORTAL_TRAVEL_SOUND, 1.0F, 1.0F);

			event.setCancelled(true);
		}
	}
}
