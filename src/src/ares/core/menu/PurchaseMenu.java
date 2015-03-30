package src.ares.core.menu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import src.ares.core.client.Client;
import src.ares.core.common.item.CraftedItemStack;
import src.ares.core.currency.type.GoldCurrency;

public abstract class PurchaseMenu extends Menu
{
	private ItemStack item;
	private GoldCurrency currency;

	public PurchaseMenu(String menuName, ItemStack sellableItem, GoldCurrency currencyType)
	{
		super(Material.STONE, menuName, 54);

		item = sellableItem;
		currency = currencyType;

		DependOnEvents(true);
	}

	private void AddCancelButton()
	{
		AddDisplay(33, new CraftedItemStack(Material.REDSTONE_BLOCK, ChatColor.RED + "" + ChatColor.BOLD + "Cancel").pack());
		AddDisplay(34, new CraftedItemStack(Material.REDSTONE_BLOCK, ChatColor.RED + "" + ChatColor.BOLD + "Cancel").pack());
		AddDisplay(35, new CraftedItemStack(Material.REDSTONE_BLOCK, ChatColor.RED + "" + ChatColor.BOLD + "Cancel").pack());
		AddDisplay(42, new CraftedItemStack(Material.REDSTONE_BLOCK, ChatColor.RED + "" + ChatColor.BOLD + "Cancel").pack());
		AddDisplay(43, new CraftedItemStack(Material.REDSTONE_BLOCK, ChatColor.RED + "" + ChatColor.BOLD + "Cancel").pack());
		AddDisplay(44, new CraftedItemStack(Material.REDSTONE_BLOCK, ChatColor.RED + "" + ChatColor.BOLD + "Cancel").pack());
		AddDisplay(51, new CraftedItemStack(Material.REDSTONE_BLOCK, ChatColor.RED + "" + ChatColor.BOLD + "Cancel").pack());
		AddDisplay(52, new CraftedItemStack(Material.REDSTONE_BLOCK, ChatColor.RED + "" + ChatColor.BOLD + "Cancel").pack());
		AddDisplay(53, new CraftedItemStack(Material.REDSTONE_BLOCK, ChatColor.RED + "" + ChatColor.BOLD + "Cancel").pack());
	}

	private void AddPurchaseButton()
	{
		AddDisplay(29, new CraftedItemStack(Material.EMERALD_BLOCK, ChatColor.GREEN + "" + ChatColor.BOLD + "Purchase " + item.getItemMeta().getDisplayName(), new String[] { ChatColor.WHITE + "" + ChatColor.BOLD + currency.getFormatted() }).pack());
		AddDisplay(28, new CraftedItemStack(Material.EMERALD_BLOCK, ChatColor.GREEN + "" + ChatColor.BOLD + "Purchase " + item.getItemMeta().getDisplayName(), new String[] { ChatColor.WHITE + "" + ChatColor.BOLD + currency.getFormatted() }).pack());
		AddDisplay(27, new CraftedItemStack(Material.EMERALD_BLOCK, ChatColor.GREEN + "" + ChatColor.BOLD + "Purchase " + item.getItemMeta().getDisplayName(), new String[] { ChatColor.WHITE + "" + ChatColor.BOLD + currency.getFormatted() }).pack());
		AddDisplay(36, new CraftedItemStack(Material.EMERALD_BLOCK, ChatColor.GREEN + "" + ChatColor.BOLD + "Purchase " + item.getItemMeta().getDisplayName(), new String[] { ChatColor.WHITE + "" + ChatColor.BOLD + currency.getFormatted() }).pack());
		AddDisplay(37, new CraftedItemStack(Material.EMERALD_BLOCK, ChatColor.GREEN + "" + ChatColor.BOLD + "Purchase " + item.getItemMeta().getDisplayName(), new String[] { ChatColor.WHITE + "" + ChatColor.BOLD + currency.getFormatted() }).pack());
		AddDisplay(38, new CraftedItemStack(Material.EMERALD_BLOCK, ChatColor.GREEN + "" + ChatColor.BOLD + "Purchase " + item.getItemMeta().getDisplayName(), new String[] { ChatColor.WHITE + "" + ChatColor.BOLD + currency.getFormatted() }).pack());
		AddDisplay(45, new CraftedItemStack(Material.EMERALD_BLOCK, ChatColor.GREEN + "" + ChatColor.BOLD + "Purchase " + item.getItemMeta().getDisplayName(), new String[] { ChatColor.WHITE + "" + ChatColor.BOLD + currency.getFormatted() }).pack());
		AddDisplay(46, new CraftedItemStack(Material.EMERALD_BLOCK, ChatColor.GREEN + "" + ChatColor.BOLD + "Purchase " + item.getItemMeta().getDisplayName(), new String[] { ChatColor.WHITE + "" + ChatColor.BOLD + currency.getFormatted() }).pack());
		AddDisplay(47, new CraftedItemStack(Material.EMERALD_BLOCK, ChatColor.GREEN + "" + ChatColor.BOLD + "Purchase " + item.getItemMeta().getDisplayName(), new String[] { ChatColor.WHITE + "" + ChatColor.BOLD + currency.getFormatted() }).pack());
	}

	@Override
	public void InventoryClick(InventoryAction action, ItemStack item, Player player)
	{
		Client client = new Client(player);

		if (item.getType() == Material.EMERALD_BLOCK)
		{
			// Purchase success

			if (PrePaymentChecks(client))
			{
				if (client.removeCurrency(currency, true))
				{
					PlaySuccessSound(player);
					PlayVillagerSuccess(player);

					PurchaseProduct(currency, client);
				}

				// Purchase failure

				else
				{
					PlayErrorSound(player);
				}
			}
		}
		else if (item.getType() == Material.REDSTONE_BLOCK)
		{
			PlayVillagerFailure(player);
		}

		client.unload();
	}

	@Override
	public void InventoryConstruct(Player player)
	{
		AddDisplay(13, item);

		AddPurchaseButton();
		AddCancelButton();
	}

	protected void PlayVillagerFailure(Player player)
	{
		player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
	}

	protected void PlayVillagerSuccess(Player player)
	{
		player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1.0F, 1.0F);
	}

	public abstract void PurchaseProduct(GoldCurrency currency, Client client);

	public abstract boolean PrePaymentChecks(Client client);
}
