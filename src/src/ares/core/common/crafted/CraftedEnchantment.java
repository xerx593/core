package src.ares.core.common.crafted;

import org.bukkit.enchantments.Enchantment;

public class CraftedEnchantment
{
	private Enchantment enchantment;
	private int level = 1;
	private boolean bypassLimit = false;

	public CraftedEnchantment(Enchantment enchantment, int level, boolean bypassLimit)
	{
		this.enchantment = enchantment;
		this.level = level;
		this.bypassLimit = bypassLimit;
	}

	public boolean canBypassLimit()
	{
		return bypassLimit;
	}

	public Enchantment getEnchantment()
	{
		return enchantment;
	}

	public int getLevel()
	{
		return level;
	}
}
