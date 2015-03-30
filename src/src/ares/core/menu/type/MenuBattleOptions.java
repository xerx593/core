package src.ares.core.menu.type;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import src.ares.core.Main;
import src.ares.core.battle.BattleManager;
import src.ares.core.battle.kit.Kit;
import src.ares.core.client.Client;
import src.ares.core.currency.type.GoldCurrency;
import src.ares.core.menu.Menu;

public class MenuBattleOptions extends Menu
{
	private BattleManager manager = BattleManager.getInstance();

	public MenuBattleOptions()
	{
		super(Material.EMERALD, "Battle Options", 45);
	}

	@Override
	protected void InventoryClick(InventoryAction action, ItemStack item, Player player)
	{
		if (item.getType() == Material.SADDLE)
		{
			OpenUpgradesMenu(item, player);
		}
		else
		{
			SelectOrBuyKit(action, item, player);
		}
	}

	@Override
	protected void InventoryConstruct(Player player)
	{
		Client client = new Client(player);

		for (Kit kit : manager.getKitBag())
		{
			if (kit.getName().contains("Ares Kit"))
				AddDisplay(10, kit.getPersonalDisplay(client));
			if (kit.getName().contains("Apollo Kit"))
				AddDisplay(12, kit.getPersonalDisplay(client));
			if (kit.getName().contains("Poseidon Kit"))
				AddDisplay(14, kit.getPersonalDisplay(client));
			if (kit.getName().contains("Thor Kit"))
				AddDisplay(16, kit.getPersonalDisplay(client));
			if (kit.getName().contains("Hypnos Kit"))
				AddDisplay(28, kit.getPersonalDisplay(client));
			if (kit.getName().contains("Tyche Kit"))
				AddDisplay(30, kit.getPersonalDisplay(client));
			if (kit.getName().contains("Zeus Kit"))
				AddDisplay(32, kit.getPersonalDisplay(client));
			if (kit.getName().contains("Hades Kit"))
				AddDisplay(34, kit.getPersonalDisplay(client));
		}
	}

	private void OpenUpgradesMenu(ItemStack item, Player player)
	{
		MenuUpgrades menu = new MenuUpgrades();
		menu.registerEvents();
		menu.ShowPage(player);
	}

	private void SelectOrBuyKit(InventoryAction action, ItemStack item, Player player)
	{
		Client client = new Client(player);
		String clicked = item.getItemMeta().getDisplayName();

		for (Kit kit : manager.getKitBag())
		{
			if (kit.getName().equals(ChatColor.stripColor(clicked)))
			{
				int level = new Client(player).getManager().getKitLevel(kit);

				if (client.getManager().isKitOwned(kit))
				{
					if (action == InventoryAction.PICKUP_HALF)
					{
						PurchaseMenuKitLevel menu = new PurchaseMenuKitLevel(kit, new GoldCurrency(calculateCost(kit, level)));
						menu.registerEvents();
						menu.ShowPage(player);
					}
					else
					{
						manager.selectKit(kit, player, true);
					}
				}
				else
				{
					openOwnershipMenu(kit, player);
				}
			}
		}
	}
	
	private int calculateCost(Kit kit, int level)
	{
		int cost = kit.getCost() + (kit.getCost() * level);
		
		if (cost == 0)
		{
			cost = 1000;
		}
		
		return cost;
	}

	private void openOwnershipMenu(Kit kit, Player player)
	{
		if (!manager.selectKit(kit, player, true))
		{
			PurchaseMenuKit menu = new PurchaseMenuKit(kit, new GoldCurrency(kit.getCost()));
			menu.registerEvents();
			menu.ShowPage(player);
		}
	}
}
