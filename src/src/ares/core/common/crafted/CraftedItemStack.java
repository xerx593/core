package src.ares.core.common.crafted;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

/**
 * Represents an item stack wrapper for quick meta modifications.
 * 
 * @see {@link CraftedProduct} <br> {@link CraftedArmor}
 */
public class CraftedItemStack extends CraftedProduct
{
	public CraftedItemStack(Material material, String name, String... lore)
	{
		super(material, name, lore);
	}

	public CraftedItemStack(Material material, String name)
	{
		super(material, name);
	}
	
	public CraftedItemStack(Material material)
	{
		super(material, null);
	}

	public CraftedItemStack glow()
	{
		enchantment(new CraftedEnchantment(Enchantment.DURABILITY, 1, false));
		return this;
	}
}
