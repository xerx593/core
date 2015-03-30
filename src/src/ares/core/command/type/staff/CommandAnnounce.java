package src.ares.core.command.type.staff;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import src.ares.core.chat.Notification;
import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;
import src.ares.core.common.util.UtilString;

public class CommandAnnounce extends CoreCommand
{
	private Notification notification = new Notification();

	public CommandAnnounce()
	{
		super("announce", new String[] {}, 1, Rank.MOD, "<message>");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute()
	{
		String message = UtilString.build(getArgs(), 0);
		notification.sendToPlayers(true, ChatColor.AQUA + "" + ChatColor.BOLD + getClient().getName(), message);

		for (Player player : Bukkit.getOnlinePlayers())
		{
			player.playSound(player.getLocation(), Sound.NOTE_BASS, 1.0F, 1.0F);
		}
	}
}
