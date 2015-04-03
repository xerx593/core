package src.ares.core.battle.upgrade;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import src.ares.core.currency.type.GoldCurrency;

public class UpgradeArmorProtection extends Upgrade
{
	public UpgradeArmorProtection()
	{
		super("Armor Protection", new String[]
		{
		ChatColor.GRAY + "Make your armor stronger", ChatColor.GRAY + "against any sort of damage."
		}, new GoldCurrency(2000), Material.BOOK, 4);
	}
}
