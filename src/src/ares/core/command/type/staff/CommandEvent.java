package src.ares.core.command.type.staff;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import src.ares.core.chat.Notification;
import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;
import src.ares.core.common.util.Chat;
import src.ares.core.common.util.UtilString;
import src.ares.core.world.CoreWorldHub;
import src.ares.core.world.WorldManager;

public class CommandEvent extends CoreCommand
{
	private Notification notification = new Notification();

	public CommandEvent()
	{
		super("event", new String[] {}, 1, Rank.SR_MOD, "<event name>");
	}

	@Override
	public void execute()
	{
		String event = UtilString.build(getArgs(), 0);

		getClient().sendMessage(getModuleName(), "Event has been set to: " + Chat.tool(event) + ".");

		if (!event.equalsIgnoreCase("none"))
			broadcastEvent(event);

		for (CoreWorldHub hub : WorldManager.getInstance().getHubWorlds())
		{
			Location signLocation = new Location(hub.getWorld(), 0, 47, 52);
			Location beaconLocation = new Location(hub.getWorld(), 0, 44, 54);

			Block signBlock = hub.getWorld().getBlockAt(signLocation);
			signBlock.setType(Material.WALL_SIGN);
			Sign sign = (Sign) signBlock.getState();

			Block beaconBlock = hub.getWorld().getBlockAt(beaconLocation);

			sign.setLine(0, ChatColor.STRIKETHROUGH + "--------------");

			if (!event.equalsIgnoreCase("none"))
			{
				sign.setLine(1, ChatColor.DARK_GREEN + "Current Events");
				sign.setLine(2, UtilString.format(event));
				beaconBlock.setType(Material.BEACON);
			}
			else
			{
				sign.setLine(1, ChatColor.DARK_RED + "Current Events");
				sign.setLine(2, "None");
				beaconBlock.setType(Material.AIR);
			}

			sign.setLine(3, ChatColor.STRIKETHROUGH + "--------------");
			sign.update();
		}
	}

	@SuppressWarnings("deprecation")
	private void broadcastEvent(String event)
	{
		notification.sendToPlayers(true, event + " Event", Chat.player(getClient().getName()) + " has started a " + Chat.tool(event + " Event") + ".");

		for (Player player : Bukkit.getOnlinePlayers())
		{
			notification.playSound(player);
		}
	}
}
