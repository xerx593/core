package src.ares.core.menu.type;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import src.ares.core.client.OfflineClient;
import src.ares.core.client.storage.ClientSection;
import src.ares.core.common.crafted.CraftedItemStack;
import src.ares.core.common.util.UtilString;
import src.ares.core.currency.CurrencyType;
import src.ares.core.menu.Menu;

public class MenuPunish extends Menu
{
	private OfflineClient punisher;
	private OfflineClient offender;
	private double duration;
	private String reason;
	private int historySlot = 36;

	public MenuPunish(OfflineClient punisher, OfflineClient offender, double duration, String reason)
	{
		super("Punishment Panel", 54);

		this.punisher = punisher;
		this.offender = offender;
		this.duration = duration;
		this.reason = reason;

		DependOnEvents(true);
	}

	@Override
	protected void InventoryClick(InventoryAction action, ItemStack item, Player player)
	{
		PlaySuccessSound(player);
	}

	@Override
	protected void InventoryConstruct(Player player)
	{
		AddDisplay(4, new CraftedItemStack(Material.PAPER, offender.getName()).lore(new String[]
		{
		ChatColor.YELLOW + "" + ChatColor.BOLD + "Rank", ChatColor.GRAY + offender.getRank().getName(), "", ChatColor.YELLOW + "" + ChatColor.BOLD + CurrencyType.GOLD.toString(), ChatColor.GRAY + "" + offender.getManager().getCurrency(CurrencyType.GOLD), "", ChatColor.YELLOW + "" + ChatColor.BOLD + CurrencyType.AMBROSIA.toString(), ChatColor.GRAY + "" + offender.getManager().getCurrency(CurrencyType.AMBROSIA)
		}).build());

		AddDisplay(19, new CraftedItemStack(Material.ENDER_CHEST, "Ban " + offender.getName()).lore(new String[]
		{
		ChatColor.YELLOW + "The player won't be able to login", ChatColor.YELLOW + "for a specific amount of time.", "", ChatColor.RED + "" + ChatColor.BOLD + "Punish with valid evidence."
		}).build());

		AddDisplay(21, new CraftedItemStack(Material.CHEST, "Mute " + offender.getName()).lore(new String[]
		{
		ChatColor.YELLOW + "The player won't be able to chat", ChatColor.YELLOW + "for a specific amount of time.", "", ChatColor.RED + "" + ChatColor.BOLD + "Punish with valid evidence."
		}).build());

		AddDisplay(23, new CraftedItemStack(Material.ANVIL, "Warn " + offender.getName()).lore(new String[]
		{
		ChatColor.YELLOW + "The player will recieve a warning", ChatColor.YELLOW + "in special format, along with a scary sound.", "", ChatColor.RED + "" + ChatColor.BOLD + "Punish with valid evidence."
		}).build());

		AddDisplay(25, new CraftedItemStack(Material.ICE, "Freeze " + offender.getName()).lore(new String[]
		{
		ChatColor.GOLD + "The player won't be able to move,", ChatColor.GOLD + "click here again to un-freeze.", "", ChatColor.RED + "" + ChatColor.BOLD + "Punish with valid evidence."
		}).build());

		ConfigurationSection history = offender.getManager().getConfig().getConfigurationSection(ClientSection.HISTORY.toString());

		for (String string : offender.getManager().getPunishmentHistory())
		{
			ConfigurationSection specific = history.getConfigurationSection(string);
			CraftedItemStack book = new CraftedItemStack(Material.BOOK, ChatColor.BOLD + UtilString.format(string));
			int slot = historySlot++;
			AddDisplay(slot, book.build());
		}
	}
}
