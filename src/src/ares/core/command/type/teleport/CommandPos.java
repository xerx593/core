package src.ares.core.command.type.teleport;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;

public class CommandPos extends CoreCommand
{
	public CommandPos()
	{
		super("pos", new String[] {}, 3, Rank.MOD, "<x> <y> <z>");
	}

	@Override
	public void execute()
	{
		Double x = new Double(getArgs()[0]);
		Double y = new Double(getArgs()[1]);
		Double z = new Double(getArgs()[2]);

		Location location = new Location(getClient().getPlayer().getWorld(), x, y, z);

		if (location == null || x == null || y == null || z == null)
		{
			getClient().sendMessage(getModuleName(), "Please enter valid coordinates.");
			return;
		}

		getClient().getPlayer().teleport(location);

		if (getClient().getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR)
			getClient().getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).setType(Material.STONE);
	}
}
