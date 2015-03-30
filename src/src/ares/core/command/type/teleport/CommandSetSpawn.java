package src.ares.core.command.type.teleport;

import org.bukkit.Location;

import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;

public class CommandSetSpawn extends CoreCommand
{
	public CommandSetSpawn()
	{
		super("setspawn", new String[] {}, 0, Rank.ADMIN);
	}

	@Override
	public void execute()
	{
		Location spawn = getClient().getPlayer().getLocation().setDirection(getClient().getPlayer().getVelocity());

		getClient().getPlayer().getWorld().setSpawnLocation(spawn.getBlockX(), spawn.getBlockY(), spawn.getBlockZ());

		getClient().sendMessage(getModuleName(), "Spawn has been set on your location.");
	}
}
