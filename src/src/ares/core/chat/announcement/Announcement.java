package src.ares.core.chat.announcement;

import org.bukkit.scheduler.BukkitRunnable;

import src.ares.core.chat.Notification;

public class Announcement extends BukkitRunnable
{
	int next = -1;

	private AnnouncementManager manager = AnnouncementManager.getInstance();
	private Notification notification = new Notification();

	@Override
	public void run()
	{
		if (++next >= manager.getAnnouncements().size())
		{
			next = 0;
		}
		if (next < manager.getAnnouncements().size())
		{
			notification.sendToPlayers(true, "Hint", manager.getAnnouncements().get(next));
		}
	}
}
