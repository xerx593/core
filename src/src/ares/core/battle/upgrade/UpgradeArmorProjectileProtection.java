package src.ares.core.battle.upgrade;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import src.ares.core.currency.type.GoldCurrency;

public class UpgradeArmorProjectileProtection extends Upgrade
{
	public UpgradeArmorProjectileProtection()
	{
		super("Armor Projectile Protection", new String[]
		{
		ChatColor.GRAY + "Equip your armor with", ChatColor.GRAY + "protection against projectiles."
		}, new GoldCurrency(3000), Material.BOOK, 4);
	}
}
