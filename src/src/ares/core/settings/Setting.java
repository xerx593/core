package src.ares.core.settings;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import src.ares.core.client.Rank;
import src.ares.core.common.crafted.CraftedItemStack;
import src.ares.core.common.util.Chat;

public class Setting
{
	private String name;
	private Material display;
	private Rank rank;

	public Setting(String name, Material display, Rank rank)
	{
		this.name = name;
		this.display = display;
		this.rank = rank;
	}

	public Setting(String name, Material display)
	{
		this(name, display, Rank.PLAYER);
	}

	public String getName()
	{
		return name;
	}

	public Material getDisplay()
	{
		return display;
	}

	public Rank getRank()
	{
		return rank;
	}

	public ItemStack getTurnedOn()
	{
		return new CraftedItemStack(Material.WOOL, ChatColor.GREEN + "" + ChatColor.BOLD + "Enabled").lore(new String[]
		{
		ChatColor.GRAY + name, ChatColor.WHITE + "Click to disable."
		}).data((byte) 5).build();
	}

	public ItemStack getTurnedOff()
	{
		return new CraftedItemStack(Material.WOOL, ChatColor.RED + "" + ChatColor.BOLD + "Disabled").lore(new String[]
		{
		ChatColor.GRAY + name, ChatColor.WHITE + "Click to enable."
		}).data((byte) 14).build();
	}

	public ItemStack getLocked()
	{
		return new CraftedItemStack(Material.WOOL, ChatColor.BLUE + "" + ChatColor.BOLD + "Locked").lore(new String[]
		{
		ChatColor.GRAY + name, ChatColor.WHITE + "Only for " + Chat.rank(rank.getName() + "+") + " Rank"
		}).data((byte) 7).build();
	}
}
