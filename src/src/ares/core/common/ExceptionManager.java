package src.ares.core.common;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import src.ares.core.Main;
import src.ares.core.client.Client;
import src.ares.core.client.Rank;
import src.ares.core.common.util.Chat;

public class ExceptionManager
{
	private static ChatColor C;

	private static ChatColor cMsgHeader = C.RED;
	private static ChatColor cMsgBody = C.WHITE;
	private static ChatColor cMsgError = C.YELLOW;

	private static String type;
	private static String file;
	private static String method;
	private static int where;

	// Code Snippet
	private static StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[2];
	private static Sound stackTraceSound = Sound.HORSE_SKELETON_IDLE;

	/**
	 * Manages a new Exception, formats and displays the stack trace to all staff members.
	 * 
	 * @param e The Exception to handle.
	 */
	public static void handle(Exception e)
	{
		Main.log(Chat.seperator(), Level.SEVERE);
		e.printStackTrace();
		Main.log(Chat.seperator(), Level.SEVERE);

		for (Player player : Bukkit.getOnlinePlayers())
		{
			Client client = new Client(player);

			if (client.getManager().getRank().ordinal() >= Rank.TRIAL_MOD.ordinal())
			{
				player.playSound(player.getLocation(), stackTraceSound, 1.0F, 1.0F);

				type = e.getClass().getSimpleName();
				file = stackTrace.getFileName();
				method = stackTrace.getMethodName();
				where = stackTrace.getLineNumber();

				client.sendRaw("");
				client.sendRaw(cMsgHeader + "" + ChatColor.BOLD + "Internal Error Occured");
				client.sendRaw(cMsgBody + " Please forward a screenshot to an online staff member.");
				client.sendRaw(cMsgError + " " + type + " at " + file + ":" + where + " on " + method + "()");
				client.sendRaw("");
			}

			client.unload();
		}
	}
}
