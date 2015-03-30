package src.ares.core.command.type.staff;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;
import src.ares.core.condition.type.FlyCondition;

public class CommandFly extends CoreCommand
{
	private FlyCondition flyCondition = FlyCondition.getCondition();

	public CommandFly()
	{
		super("fly", new String[] {}, 0, Rank.ADMIN, null);
	}

	private void allowFlight()
	{
		flyCondition.addItem(getClient().getPlayer());
		getClient().sendMessage(flyCondition.getType(), "You have enabled flymode.");

		getClient().getPlayer().setAllowFlight(true);
		getClient().getPlayer().setFlying(true);
	}

	private void disallowFlight()
	{
		flyCondition.removeItem(getClient().getPlayer());
		getClient().sendMessage(flyCondition.getType(), "You have disabled flymode.");

		getClient().getPlayer().setAllowFlight(false);
		getClient().getPlayer().setFlying(false);
	}

	@Override
	public void execute()
	{
		if (flyCondition.hasItem(getClient().getPlayer()))
		{
			getClient().playLocationSound(Sound.BAT_TAKEOFF, 1.0F, 0.5F);
			disallowFlight();
		}
		else
		{
			getClient().playLocationSound(Sound.BAT_TAKEOFF, 1.0F, 1.5F);
			allowFlight();
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();

		if (flyCondition.hasItem(player))
			flyCondition.removeItem(player);
	}
}
