package src.ares.core.client;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import src.ares.core.Main;
import src.ares.core.chat.Notification;
import src.ares.core.common.Module;
import src.ares.core.common.util.Chat;
import src.ares.core.settings.Setting;

public class StaffListener extends Module
{
	private static StaffListener instance = new StaffListener();

	public static StaffListener getInstance()
	{
		return instance;
	}

	private Notification notification = new Notification();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void toggleStaffChat(AsyncPlayerChatEvent event)
	{
		Client him = new Client(event.getPlayer());
		String message = event.getMessage();
		Setting setting = him.getManager().getSetting("Staff Chat");

		if (message.startsWith("@") && him.isStaff())
		{
			if (!him.getManager().hasEnabledSetting(setting))
			{
				Main.debug(him.getName() + " has disabled his " + setting.getName() + ".");
				him.sendMessage(setting.getName(), "Disabled, open the settings menu to re-enable it.");
				event.setCancelled(true);
				return;
			}

			if (event.getMessage().length() <= 1)
				return;

			event.setCancelled(true);
			
			for (Player player : Bukkit.getOnlinePlayers())
			{
				Client other = new Client(player);

				if (other.isStaff())
				{
					String formatted = event.getMessage().replaceFirst("@", "").trim();
					String chat = Chat.structure(him.getManager().getRank(), ChatColor.GOLD + him.getName(), ChatColor.GRAY + formatted);

					if (other.getManager().hasEnabledSetting(setting))
					{
						notification.playSound(other.getPlayer());
						other.sendRaw(chat);
					}
				}

				other.unload();
			}
		}

		him.unload();
	}
}
