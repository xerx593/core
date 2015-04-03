package src.ares.core.menu.type;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import src.ares.core.client.Client;
import src.ares.core.common.crafted.CraftedItemStack;
import src.ares.core.currency.CurrencyType;
import src.ares.core.currency.type.AmbrosiaCurrency;
import src.ares.core.currency.type.GoldCurrency;
import src.ares.core.menu.Menu;
import src.ares.core.stats.Statistic;
import src.ares.core.stats.StatsManager;

public class MenuProgress extends Menu
{
	public MenuProgress()
	{
		super(Material.SKULL_ITEM, "Progress Menu", 45);
	}

	public ItemStack getDisplay(Client client)
	{
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
		SkullMeta meta = (SkullMeta) skull.getItemMeta();

		meta.setDisplayName(ChatColor.WHITE + getName());
		meta.setOwner(client.getName());
		skull.setItemMeta(meta);

		return skull;
	}

	@Override
	protected void InventoryClick(InventoryAction action, ItemStack item, Player player)
	{
		PlayErrorSound(player);
	}

	@Override
	protected void InventoryConstruct(Player player)
	{
		Client client = new Client(player);

		showBalance(client);

		showBattleInfo(client);

		showMinutesPlayed(client);

		showHighestKillstreak(client);
	}

	private void showBalance(Client client)
	{
		GoldCurrency gold = new GoldCurrency(client.getManager().getCurrency(CurrencyType.GOLD));
		AmbrosiaCurrency ambrosia = new AmbrosiaCurrency(client.getManager().getCurrency(CurrencyType.AMBROSIA));

		AddDisplay(13, new CraftedItemStack(Material.COOKIE, "Account Balance", new String[]
		{
		ChatColor.YELLOW + "" + gold.getFormatted(), ChatColor.YELLOW + "" + ambrosia.getFormatted()
		}).build());
	}

	private void showBattleInfo(Client client)
	{
		for (Statistic stats : StatsManager.getInstance().getStatisticBag())
		{
			if (stats.getTitle() == "Total Deaths")
			{
				CraftedItemStack deaths = new CraftedItemStack(Material.ROTTEN_FLESH, stats.getTitle());
				deaths.lore(new String[]
				{
					ChatColor.YELLOW + "" + client.getManager().getStatistic(stats.getTitle()) + " " + stats.getTitle()
				});

				AddDisplay(28, deaths.build());
			}
			else if (stats.getTitle() == "Total Kills")
			{
				CraftedItemStack kills = new CraftedItemStack(Material.FLINT_AND_STEEL, stats.getTitle());
				kills.lore(new String[]
				{
					ChatColor.YELLOW + "" + client.getManager().getStatistic(stats.getTitle()) + " " + stats.getTitle()
				});

				AddDisplay(30, kills.build());
			}
			else if (stats.getTitle() == "Total Bow Hits")
			{
				CraftedItemStack kills = new CraftedItemStack(Material.BOW, stats.getTitle());
				kills.lore(new String[]
				{
					ChatColor.YELLOW + "" + client.getManager().getStatistic(stats.getTitle()) + " " + stats.getTitle()
				});

				AddDisplay(32, kills.build());
			}
		}
	}

	private void showHighestKillstreak(Client client)
	{
		AddDisplay(34, new CraftedItemStack(Material.MINECART, "Highest Killstreak", new String[]
		{
			ChatColor.YELLOW + "" + Integer.toString(client.getManager().getKillstreak()) + " Killstreak"
		}).build());
	}

	private void showMinutesPlayed(Client client)
	{

	}
}
