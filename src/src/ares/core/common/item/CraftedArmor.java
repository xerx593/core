package src.ares.core.common.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class CraftedArmor
{
	private Material material;
	private String displayName;
	private String[] lore;
	private Color color;

	private int amount = 1;
	private short data;
	private short durability;
	private boolean unbreakable;

	private List<CraftedEnchantment> enchantments;

	public CraftedArmor(Material armor, String displayName, String[] lore, Color color)
	{
		enchantments = new ArrayList<>();

		this.material = armor;
		this.displayName = displayName;
		this.lore = lore;
		this.color = color;
	}

	public void addEnchantment(Enchantment enchantment, int level, boolean bypassLimit)
	{
		this.enchantments.add(new CraftedEnchantment(enchantment, level, bypassLimit));
	}

	public int getAmount()
	{
		return amount;
	}

	public Color getColor()
	{
		return color;
	}

	public short getData()
	{
		return data;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public short getDurability()
	{
		return durability;
	}

	public List<CraftedEnchantment> getEnchantments()
	{
		return enchantments;
	}

	public String[] getLore()
	{
		return lore;
	}

	public Material getMaterial()
	{
		return material;
	}

	public ItemStack pack()
	{
		ItemStack itemStack = new ItemStack(material, amount);
		LeatherArmorMeta itemMeta = (LeatherArmorMeta) itemStack.getItemMeta();

		itemMeta.setDisplayName(ChatColor.WHITE + displayName);
		itemMeta.setLore(Arrays.asList(lore));
		itemMeta.setColor(color);

		if (unbreakable)
		{
			itemMeta.spigot().setUnbreakable(true);
		}

		for (CraftedEnchantment e : enchantments)
		{
			itemMeta.addEnchant(e.getEnchantment(), e.getLevel(), e.canBypassLimit());
		}

		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	/**
	 * Sets the amount of ItemStacks. If the amount is under 64 or below 1, it will be automatically set to 64.
	 * 
	 * @param amount The amount.
	 */
	public CraftedArmor setAmount(int amount)
	{
		if (amount > 64 || amount < 1)
			this.amount = 1;
		else this.amount = amount;

		return this;
	}

	/**
	 * The data of the ItemStack. For example, a full side piston (22:6) can be created by passing in 6.
	 * 
	 * @param data
	 */
	public CraftedArmor setData(short data)
	{
		this.data = data;
		return this;
	}

	/**
	 * Set the durability of the ItemStack.
	 * 
	 * @param durability The durability of the ItemStack.
	 * @return CraftedArmor
	 */
	public CraftedArmor setDurability(short durability)
	{
		this.durability = durability;
		return this;
	}

	/**
	 * Set the lore of the ItemStack.
	 * 
	 * @param lore The lore of the ItemStack.
	 * @return CraftedArmor
	 */
	public CraftedArmor setLore(String[] lore)
	{
		this.lore = lore;
		return this;
	}

	/**
	 * Set the ItemStack so it will never ran out of durability.
	 * 
	 * @param unbreakable Should the item be unbreakable.
	 * @return CraftedArmor
	 */
	public CraftedArmor setUnbreakable(boolean unbreakable)
	{
		this.unbreakable = unbreakable;
		return this;
	}
}
