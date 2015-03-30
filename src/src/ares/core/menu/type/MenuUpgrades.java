package src.ares.core.menu.type;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import src.ares.core.battle.upgrade.Upgrade;
import src.ares.core.battle.upgrade.UpgradeArmorType;
import src.ares.core.battle.upgrade.UpgradeManager;
import src.ares.core.client.Client;
import src.ares.core.currency.type.GoldCurrency;
import src.ares.core.menu.Menu;

public class MenuUpgrades extends Menu
{
	private UpgradeManager manager = UpgradeManager.getInstance();

	public MenuUpgrades()
	{
		super(Material.SADDLE, "Battle Upgrades", 45);

		DependOnEvents(true);
	}

	@Override
	protected void InventoryClick(InventoryAction action, ItemStack item, Player player)
	{
		Client client = new Client(player);

		for (Upgrade upgrade : manager.getUpgrades())
		{
			if (upgrade.getName().contains("Armor Type Upgrade"))
			{
				UpgradeArmorType armor = (UpgradeArmorType) upgrade;

				if (item.getType() == armor.getLevelDependDisplay(client).getType())
				{
					OpenPurchaseMenu(client, upgrade);
				}
			}

			if (item.equals(upgrade.getDetailedDisplay(client)))
			{
				if (client.getManager().getUpgradeLevel(upgrade) != upgrade.getLevel())
				{
					OpenPurchaseMenu(client, upgrade);
				}
				else
				{
					client.sendMessage("Upgrade", "You have reached the maximum level.");
				}
			}
		}
	}

	@Override
	protected void InventoryConstruct(Player player)
	{
		Client client = new Client(player);
		int slot = 20;

		for (Upgrade upgrade : manager.getUpgrades())
		{
			if (upgrade.getName().contains("Armor Type Upgrade"))
			{
				UpgradeArmorType armorUpgrade = (UpgradeArmorType) upgrade;
				AddDisplay(13, armorUpgrade.getLevelDependDisplay(client));
				continue;
			}

			AddDisplay(slot++, upgrade.getDetailedDisplay(client));
		}

	}

	private void OpenPurchaseMenu(Client client, Upgrade upgrade)
	{
		int finalCost = 0;

		for (int i = 0; i <= client.getManager().getUpgradeLevel(upgrade); i++)
		{
			finalCost += upgrade.getCost().getAmount();
		}

		PurchaseMenuUpgrade menu = new PurchaseMenuUpgrade(upgrade, new GoldCurrency(finalCost));
		menu.registerEvents();
		menu.ShowPage(client.getPlayer());
	}
}
