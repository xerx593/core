package src.ares.core.command.type.teleport;

import org.bukkit.Bukkit;
import org.bukkit.World;

import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;
import src.ares.core.common.util.Chat;
import src.ares.core.world.CoreWorld;
import src.ares.core.world.WorldManager;

public class CommandWorld extends CoreCommand
{
	public CommandWorld()
	{
		super("world", new String[] {}, 1, Rank.PLAYER, "<world>");
	}

	@Override
	public void execute()
	{
		World targetWorld = Bukkit.getWorld(getArgs()[0]);

		if (targetWorld == null)
		{
			getClient().sendMessage("Error", Chat.tool(getArgs()[0]) + " world does not exist.");
			return;
		}

		if (targetWorld == getClient().getWorld())
		{
			getClient().sendMessage("Error", "You are already on that world.");
			return;
		}

		CoreWorld world = WorldManager.getInstance().getWorld(targetWorld.getName());
		world.gotoWorld(getClient().getPlayer());
	}
}
