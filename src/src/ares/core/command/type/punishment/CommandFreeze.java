package src.ares.core.command.type.punishment;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import src.ares.core.client.Client;
import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;
import src.ares.core.common.util.Chat;
import src.ares.core.condition.type.FreezeCondition;

public class CommandFreeze extends CoreCommand
{
	private FreezeCondition freeze = FreezeCondition.getCondition();

	public CommandFreeze()
	{
		super("freeze", new String[] {}, 1, Rank.TRIAL_MOD, "<player>");
	}

	@Override
	public void execute()
	{
		Player player = Bukkit.getPlayer(getArgs()[0]);

		if (player == null)
		{
			getClient().sendMessage(getModuleName(), Chat.player(getArgs()[0]) + " is not online.");
			return;
		}

		Client offender = new Client(player);

		if (offender.isStaff())
		{
			getClient().sendMessage(getModuleName(), "You cannot freeze a staff member.");
			return;
		}

		if (!freeze.hasItem(player))
		{
			freeze.addItem(player);
			offender.sendMessage(freeze.getType(), "You are being detained for suspicious activity.");
			getClient().sendMessage(freeze.getType(), "You have frozen " + Chat.player(offender.getName()) + ".");
			offender.playLocationSound(Sound.DOOR_CLOSE, 1.0F, 1.0F);
		}
		else
		{
			freeze.removeItem(player);
			offender.sendMessage(freeze.getType(), "You are no longer detained, now you can go.");
			getClient().sendMessage(freeze.getType(), "You have unfrozen " + Chat.player(offender.getName()) + ".");
			offender.playLocationSound(Sound.DOOR_OPEN, 1.0F, 1.0F);
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event)
	{
		if (freeze.hasItem(event.getPlayer()))
		{
			Location f = event.getFrom();
			Location t = event.getTo();

			if (f.getX() != t.getX() || f.getY() != t.getY() || f.getZ() != t.getZ())
			{
				event.getPlayer().teleport(f);
			}
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		if (freeze.hasItem(event.getPlayer()))
		{
			freeze.removeItem(event.getPlayer());
		}
	}
}
