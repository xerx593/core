package src.ares.core.common.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class UtilLocation
{
	public static boolean inside(Location loc, Location loc1, Location loc2)
	{
		double maxX = (loc1.getBlockX() > loc2.getBlockX() ? loc1.getBlockX() : loc2.getBlockX());
		double minX = (loc1.getBlockX() < loc2.getBlockX() ? loc1.getBlockX() : loc2.getBlockX());

		double maxY = (loc1.getBlockY() > loc2.getBlockY() ? loc1.getBlockY() : loc2.getBlockY());
		double minY = (loc1.getBlockY() < loc2.getBlockY() ? loc1.getBlockY() : loc2.getBlockY());

		double maxZ = (loc1.getBlockZ() > loc2.getBlockZ() ? loc1.getBlockZ() : loc2.getBlockZ());
		double minZ = (loc1.getBlockZ() < loc2.getBlockZ() ? loc1.getBlockZ() : loc2.getBlockZ());

		return (loc.getX() >= minX) && (loc.getX() < maxX + 1.0D) && (loc.getY() >= minY) && (loc.getY() < maxY + 1.0D) && (loc.getZ() >= minZ) && (loc.getZ() < maxZ + 1.0D);
	}

	public static boolean isInsideParkour(Player player)
	{
		return UtilLocation.inside(player.getLocation(), new Location(Bukkit.getWorld("hub-1"), -23, 23, -37), new Location(player.getWorld(), 15, 89, -164));
	}

	public List<Block> blocks(Location loc1, Location loc2)
	{
		ArrayList<Block> blockBag = new ArrayList<>();

		int maxx = loc1.getBlockX() > loc2.getBlockX() ? loc1.getBlockX() : loc2.getBlockX();
		int minx = loc1.getBlockX() < loc2.getBlockX() ? loc1.getBlockX() : loc2.getBlockX();

		int maxy = loc1.getBlockY() > loc2.getBlockY() ? loc1.getBlockY() : loc2.getBlockY();
		int miny = loc1.getBlockY() < loc2.getBlockY() ? loc1.getBlockY() : loc2.getBlockY();

		int maxz = loc1.getBlockZ() > loc2.getBlockZ() ? loc1.getBlockZ() : loc2.getBlockZ();
		int minz = loc1.getBlockZ() < loc2.getBlockZ() ? loc1.getBlockZ() : loc2.getBlockZ();

		for (int fy = miny; fy <= maxy; fy++)
		{
			for (int fx = minx; fx <= maxx; fx++)
			{
				for (int fz = minz; fz <= maxz; fz++)
				{
					blockBag.add(new Location(loc1.getWorld(), fx, fy, fz).getBlock());
				}
			}
		}

		return blockBag;
	}
}
