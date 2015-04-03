package src.ares.core.battle.upgrade;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import src.ares.core.currency.type.GoldCurrency;

public class UpgradeArmorFireProtection extends Upgrade
{
	public UpgradeArmorFireProtection()
	{
		super("Armor Fire Protection", new String[]
		{
		ChatColor.GRAY + "Equip your armor with", ChatColor.GRAY + "protection against fire damage."
		}, new GoldCurrency(3000), Material.BOOK, 4);
	}
}
