package src.ares.core.menu.type;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import src.ares.core.common.crafted.CraftedItemStack;
import src.ares.core.menu.Menu;
import src.ares.core.server.BungeeCord;

public class MenuMinigames extends Menu
{
	public MenuMinigames()
	{
		super(Material.COMPASS, "Minigame Servers", 27);
	}

	@Override
	protected void InventoryClick(InventoryAction action, ItemStack item, Player player)
	{
		if (item.getItemMeta().getDisplayName().contains("Enderstrike 1"))
		{
			BungeeCord.send(player, "ES-1");
		}
		else if (item.getItemMeta().getDisplayName().contains("Enderstrike 2"))
		{
			BungeeCord.send(player, "ES-2");
		}
		else if (item.getItemMeta().getDisplayName().contains("Gemhunt-1"))
		{
			BungeeCord.send(player, "GH-1");
		}
	}

	@Override
	protected void InventoryConstruct(Player player)
	{
		AddDisplay(11, new CraftedItemStack(Material.ENDER_PEARL, "Enderstrike 1").build());
		AddDisplay(12, new CraftedItemStack(Material.ENDER_PEARL, "Enderstrike 2").build());
		AddDisplay(14, new CraftedItemStack(Material.EMERALD, "Gemhunt-1").build());
	}
}
