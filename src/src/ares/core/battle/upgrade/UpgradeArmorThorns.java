package src.ares.core.battle.upgrade;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import src.ares.core.currency.type.GoldCurrency;

public class UpgradeArmorThorns extends Upgrade
{
	public UpgradeArmorThorns()
	{
		super("Armor Thorns", new String[]
		{
		ChatColor.GRAY + "Your armor is stronger", ChatColor.GRAY + "and able to descrese damage."
		}, new GoldCurrency(2000), Material.BOOK, 3);
	}
}
