package src.ares.core.command.type.general;

import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;
import src.ares.core.world.WorldSelector;
import src.ares.core.world.WorldType;

public class CommandHub extends CoreCommand
{
	public CommandHub()
	{
		super("hub", new String[] {}, 0, Rank.PLAYER, "Teleport back to the hub.");
	}

	@Override
	public void execute()
	{
		if (getClient().getCoreWorld().getWorldType() == WorldType.HUB)
		{
			getClient().sendMessage(getModuleName(), "You are already in the hub.");
			return;
		}

		WorldSelector.selectHub(getClient().getPlayer());
	}
}
