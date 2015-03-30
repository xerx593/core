package src.ares.core.settings;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import src.ares.core.chat.FlashNotification;
import src.ares.core.client.Client;
import src.ares.core.common.Module;
import src.ares.core.common.util.Chat;

public class SettingsListener extends Module
{
	private static SettingsListener instance = new SettingsListener();

	public static SettingsListener getInstance()
	{
		return instance;
	}

	public SettingsListener()
	{
		super("Settings");
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event)
	{
		Player player = event.getPlayer();
		Client client = new Client(player);
		Setting setting = client.getManager().getSetting("Public Chat");

		if (setting.getName().contains("Public Chat"))
		{
			if (!client.getManager().hasEnabledSetting(setting))
			{
				event.getRecipients().remove(player);
				event.setCancelled(true);
				FlashNotification.getInstance().send(event.getPlayer(), Chat.format(setting.getName(), "Disabled, open the settings menu to re-enable it."));
			}
		}

		for (Player players : event.getRecipients())
		{
			Client others = new Client(players);

			if (!others.getManager().hasEnabledSetting(setting))
			{
				event.getRecipients().remove(players);
			}
		}

		client.unload();
	}
}
