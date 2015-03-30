package src.ares.core.currency;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public interface ICurrency
{
	public int getAmount();

	public void setAmount(int amount);

	public ItemStack getDisplay();

	public String getFormatted();

	public Material getMaterial();

	public String getName();
}
