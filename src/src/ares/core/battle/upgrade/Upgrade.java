package src.ares.core.battle.upgrade;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import src.ares.core.client.Client;
import src.ares.core.common.item.CraftedItemStack;
import src.ares.core.currency.type.GoldCurrency;

import com.google.common.collect.ObjectArrays;

public class Upgrade
{
	private String name;
	private String[] desc;
	private GoldCurrency startCost;
	private GoldCurrency cost;
	private Material type;
	private ItemStack display;
	private int level;

	/**
	 * Default Constructor
	 * 
	 * @param upgradeName The display name of the upgrade.
	 * @param upgradeDesc The display description of the upgrade.
	 * @param upgradeCost The cost of the upgrade, added by the level.
	 * @param upgradeDisplay The material type of the upgrade.
	 * @param upgradeLevel The maximum level of the upgrade.
	 */
	public Upgrade(String upgradeName, String[] upgradeDesc, GoldCurrency upgradeCost, Material upgradeDisplay, int upgradeLevel)
	{
		name = upgradeName;
		desc = upgradeDesc;
		startCost = upgradeCost;
		cost = upgradeCost;
		type = upgradeDisplay;
		level = upgradeLevel;

		display = new CraftedItemStack(type, ChatColor.GRAY + name, desc).pack();
	}

	public GoldCurrency getCost()
	{
		return cost;
	}

	public String[] getDesc()
	{
		return desc;
	}

	public ItemStack getDetailedDisplay(Client client)
	{
		int level = client.getManager().getUpgradeLevel(this);
		String[] detailedDesc = null;

		if (client.getManager().getUpgradeLevel(this) < getLevel())
		{
			detailedDesc = ObjectArrays.concat(desc, new String[] { "", ChatColor.GOLD + "" + ChatColor.BOLD + "Level " + level, ChatColor.RED + "Click to Level Up" }, String.class);
		}
		else
		{
			detailedDesc = ObjectArrays.concat(desc, new String[] { "", ChatColor.GOLD + "" + ChatColor.BOLD + "Level " + level, ChatColor.RED + "Finished" }, String.class);
		}

		CraftedItemStack display = new CraftedItemStack(type, ChatColor.WHITE + name, detailedDesc);
		return display.pack();
	}

	public ItemStack getDisplay()
	{
		return display;
	}

	public int getLevel()
	{
		return level;
	}

	public GoldCurrency getLevelCost(Client client)
	{
		for (int i = 0; i <= client.getManager().getUpgradeLevel(this); i++)
		{
			cost.setAmount(startCost.getAmount() + cost.getAmount());
		}

		return cost;
	}

	public String getName()
	{
		return name;
	}

	public Material getType()
	{
		return type;
	}
}
