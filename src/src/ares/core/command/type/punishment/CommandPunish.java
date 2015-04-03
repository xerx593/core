package src.ares.core.command.type.punishment;

import org.bukkit.Sound;

import src.ares.core.client.OfflineClient;
import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;
import src.ares.core.common.util.UtilString;
import src.ares.core.menu.type.MenuPunish;

public class CommandPunish extends CoreCommand
{
	private OfflineClient offender;
	private OfflineClient punisher;
	private double duration;
	private String reason;

	public CommandPunish()
	{
		super("punish", new String[]
		{
			"p"
		}, 3, Rank.TRIAL_MOD, "<player> <duration> <reason>");
	}

	@Override
	public void execute()
	{
		try
		{
			offender = new OfflineClient(getArgs()[0]);
			punisher = new OfflineClient(getClient().getName());
			duration = Integer.parseInt(getArgs()[1]);
			reason = UtilString.build(getArgs(), 2);

			MenuPunish punish = new MenuPunish(punisher, offender, duration, reason);
			punish.registerEvents();
			punish.ShowPage(getClient().getPlayer());

			getClient().playSound(Sound.HORSE_SADDLE, 1.0F, 0.8F);
		}
		catch (IllegalArgumentException e)
		{
			getClient().sendMessage(getModuleName(), "Please enter a valid duration.");
		}
	}
}
