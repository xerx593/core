package src.ares.core.menu.type;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import src.ares.core.client.Client;
import src.ares.core.client.Rank;
import src.ares.core.common.crafted.CraftedItemStack;
import src.ares.core.common.util.UtilString;
import src.ares.core.menu.Menu;
import src.ares.core.world.CoreWorld;
import src.ares.core.world.WorldManager;
import src.ares.core.world.WorldType;

public class MenuWorldSelector extends Menu
{
	private WorldManager manager = WorldManager.getInstance();

	public MenuWorldSelector()
	{
		super(Material.WATCH, "Select a World", 36);
	}

	@Override
	protected void InventoryClick(InventoryAction action, ItemStack item, Player player)
	{
		for (CoreWorld world : manager.getWorldBag())
		{
			if (item.getItemMeta().getDisplayName().contains(UtilString.format(world.getRealName())))
			{
				world.gotoWorld(player);
			}
		}
	}

	@Override
	protected void InventoryConstruct(Player player)
	{
		int slot1 = 12;
		int slot2 = 21;
		int slot3 = 18;
		int slot5 = 36;

		Client client = new Client(player);
		Rank rank = client.getManager().getRank();
		Rank secondRank = client.getManager().getSecondRank();

		for (CoreWorld world : manager.getWorldBag())
		{
			// Adding Hub Worlds

			if (world.getWorldType() == WorldType.HUB)
				AddDisplay(slot1++, new CraftedItemStack(Material.COMPASS, UtilString.format(world.getRealName()), new String[]
				{
				ChatColor.RED + "" + world.onlineStaff() + " Staff", ChatColor.GRAY + "" + world.onlinePlayers() + " Players"
				}).amount(world.onlinePlayers()).build());

			// Adding PvP Worlds

			else if (world.getWorldType() == WorldType.PVP)
				AddDisplay(slot2++, new CraftedItemStack(Material.LAVA_BUCKET, UtilString.format(world.getRealName()), new String[]
				{
				ChatColor.RED + "" + world.onlineStaff() + " Staff", ChatColor.GRAY + "" + world.onlinePlayers() + " Players"
				}).amount(world.onlinePlayers()).build());

			// Adding Creative Worlds

			else if (world.getWorldType() == WorldType.SURVIVAL)
				AddDisplay(slot3++, new CraftedItemStack(Material.APPLE, UtilString.format(world.getRealName()), new String[]
				{
				ChatColor.RED + "" + world.onlineStaff() + " Staff", ChatColor.GRAY + "" + world.onlinePlayers() + " Players"
				}).amount(world.onlinePlayers()).build());

			// Adding Build Worlds

			else if ((world.getWorldType() == WorldType.BUILD))
			{
				if (rank == Rank.PLAYER)
				{
					RemoveDisplay(slot5++);
				}
				else if (secondRank == Rank.BUILDER || client.compareSecondWith(Rank.ADMIN) || rank == Rank.BUILDER || client.compareWith(Rank.ADMIN))
				{
					AddDisplay(slot5++, new CraftedItemStack(Material.APPLE, UtilString.format(world.getRealName()), new String[]
					{
						ChatColor.RED + "Builders Only"
					}).amount(world.onlinePlayers()).build());
				}
			}
		}
	}
}
