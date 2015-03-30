package src.ares.core.command.type.staff;

import org.bukkit.entity.Player;

import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;
import src.ares.core.condition.type.SpectateCondition;

public class CommandVanish extends CoreCommand
{
	private SpectateCondition condition = SpectateCondition.getCondition();

	public CommandVanish()
	{
		super("vanish", new String[] {}, 0, Rank.TRIAL_MOD);
	}

	@Override
	public void execute()
	{
		Player player = getClient().getPlayer();

		if (condition.hasItem(player))
		{
			condition.removeSpectator(player);
		}
		else
		{
			condition.addSpectator(player);
		}
	}
}
