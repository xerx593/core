package src.ares.core.battle.upgrade;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import src.ares.core.currency.type.GoldCurrency;

public class UpgradeMysteriousOrb extends Upgrade
{
	public UpgradeMysteriousOrb()
	{
		super("Mysterious Orb", new String[]
		{
			ChatColor.GRAY + "Coming soon..."
		}, new GoldCurrency(1000), Material.DOUBLE_PLANT, 3);
	}
}
