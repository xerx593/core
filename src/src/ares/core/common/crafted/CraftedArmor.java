package src.ares.core.common.crafted;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

/**
 * Represents an armor wrapper for quick meta modifications.
 * 
 * @see {@link CraftedProduct} <br> {@link CraftedItemStack}
 */
public class CraftedArmor extends CraftedProduct
{
	private Color color;

	public CraftedArmor(Material material, Color color, String name, String... lore)
	{
		super(material, name, lore);

		this.color = color;
	}

	public CraftedArmor(Material material, Color color, String name)
	{
		super(material, name);

		this.color = color;
	}

	protected void onBuild(ItemStack item, ItemMeta currentMeta)
	{
		LeatherArmorMeta meta = (LeatherArmorMeta) currentMeta;
		meta.setColor(color);
	}
	
	public CraftedArmor glow()
	{
		enchantment(new CraftedEnchantment(Enchantment.DURABILITY, 1, false));
		return this;
	}

	public CraftedArmor color(Color color)
	{
		this.color = color;
		return this;
	}

	public Color getColor()
	{
		return color;
	}
}
