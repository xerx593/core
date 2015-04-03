package src.ares.core.battle.upgrade;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import src.ares.core.client.Client;
import src.ares.core.common.crafted.CraftedItemStack;
import src.ares.core.currency.type.GoldCurrency;

import com.google.common.collect.ObjectArrays;

public class UpgradeArmorType extends Upgrade
{
	public UpgradeArmorType()
	{
		super("Armor Type Upgrade", new String[]
		{
		ChatColor.GRAY + "Get better armor,", ChatColor.GRAY + "with efficient protection."
		}, new GoldCurrency(2000), Material.GOLD_BARDING, 5);
	}

	public ItemStack getLevelDependDisplay(Client client)
	{
		int level = client.getManager().getUpgradeLevel(this);

		String[] detailedDesc = null;

		if (client.getManager().getUpgradeLevel(this) < getLevel())
		{
			detailedDesc = ObjectArrays.concat(getDesc(), new String[]
			{
			"", ChatColor.GOLD + "" + ChatColor.BOLD + "Level " + level, ChatColor.RED + "Click to Level Up"
			}, String.class);
		}
		else
		{
			detailedDesc = ObjectArrays.concat(getDesc(), new String[]
			{
			"", ChatColor.GOLD + "" + ChatColor.BOLD + "Level " + level, ChatColor.RED + "Finished"
			}, String.class);
		}

		CraftedItemStack chestplate = new CraftedItemStack(Material.STONE, ChatColor.WHITE + getName(), detailedDesc);

		if (level == 1 || level == 0)
		{
			return chestplate.material(Material.LEATHER_CHESTPLATE).build();
		}
		else if (level == 2)
		{
			return chestplate.material(Material.CHAINMAIL_CHESTPLATE).build();
		}
		else if (level == 3)
		{
			return chestplate.material(Material.IRON_CHESTPLATE).build();
		}
		else if (level == 4)
		{
			return chestplate.material(Material.GOLD_CHESTPLATE).build();
		}
		else if (level == 5)
		{
			return chestplate.material(Material.DIAMOND_CHESTPLATE).build();
		}

		return chestplate.build();
	}
}
