package src.ares.core.command.type.punishment;

import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;

public class CommandKick extends CoreCommand
{
	public CommandKick()
	{
		super("kick", new String[] {}, 2, Rank.TRIAL_MOD, "<player> <reason>");
	}

	@Override
	public void execute()
	{

	}
}
