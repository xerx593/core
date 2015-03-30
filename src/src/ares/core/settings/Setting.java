package src.ares.core.settings;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import src.ares.core.client.Rank;
import src.ares.core.common.item.CraftedItemStack;
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
		return new CraftedItemStack(Material.WOOL, ChatColor.GREEN + "" + ChatColor.BOLD + "Enabled").setLore(new String[] { ChatColor.GRAY + name, ChatColor.WHITE + "Click to disable." }).setData((byte) 5).pack();
	}

	public ItemStack getTurnedOff()
	{
		return new CraftedItemStack(Material.WOOL, ChatColor.RED + "" + ChatColor.BOLD + "Disabled").setLore(new String[] { ChatColor.GRAY + name, ChatColor.WHITE + "Click to enable." }).setData((byte) 14).pack();
	}

	public ItemStack getLocked()
	{
		return new CraftedItemStack(Material.WOOL, ChatColor.BLUE + "" + ChatColor.BOLD + "Locked").setLore(new String[] { ChatColor.GRAY + name, ChatColor.WHITE + "Only for " + Chat.rank(rank.getName() + "+") + " Rank" }).setData((byte) 7).pack();
	}
}
