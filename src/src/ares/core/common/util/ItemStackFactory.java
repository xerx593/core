package src.ares.core.common.util;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ItemStackFactory
{
	private static ItemStackFactory instance = new ItemStackFactory();

	public static ItemStackFactory getFactory()
	{
		return instance;
	}

	/**
	 * TODO: Add unbreakable functionality.
	 */
	private ArrayList<ItemStack> unbreakable = new ArrayList<>();

	public ItemStack create(ItemStack existing, int level, Enchantment... enchantments)
	{
		ItemMeta meta = existing.getItemMeta();

		for (Enchantment enchantment : enchantments)
		{
			meta.addEnchant(enchantment, level, true);
		}

		existing.setItemMeta(meta);

		return existing;
	}

	public ItemStack create(Material material)
	{
		return create(material, 1, (short) 0, null, null);
	}

	public ItemStack create(Material material, Color color)
	{
		ItemStack item = new ItemStack(material);
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();

		meta.setColor(color);

		item.setItemMeta(meta);

		return item;
	}

	public ItemStack create(Material material, int amount)
	{
		return create(material, amount, (short) 0, null, null);
	}

	/**
	 * Creates a new ItemStack packed with ItemMeta.
	 * 
	 * @param material The Material to craft.
	 * @param amount The ItemStack amount.
	 * @param name The Display Name of the Meta.
	 * @param lore The Lore of the Meta.
	 * 
	 * @return ItemStack
	 */
	public ItemStack create(Material material, int amount, short data, String name, String[] lore, Enchantment... enchantments)
	{
		ItemStack item = null;

		if (data != 0)
			item = new ItemStack(material, amount, data);
		else item = new ItemStack(material, amount);

		ItemMeta meta = item.getItemMeta();

		if (name != null)
			meta.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + name);

		if (lore != null)
			meta.setLore(Arrays.asList(lore));

		if (enchantments != null)
		{
			for (Enchantment enchantment : enchantments)
			{
				meta.addEnchant(enchantment, 1, true);
			}
		}

		if (lore != null && name != null)
			item.setItemMeta(meta);

		return item;
	}

	public ItemStack create(Material material, int amount, String name)
	{
		return create(material, amount, (short) 0, name, null);
	}

	public ItemStack create(Material material, int amount, String name, String[] lore)
	{
		return create(material, amount, (short) 0, name, lore);
	}

	public ItemStack create(Material material, String name)
	{
		return create(material, 1, (short) 0, name, new String[] {}, (Enchantment[]) null);
	}

	public ItemStack create(Material material, String name, String[] lore)
	{
		return create(material, 1, (short) 0, name, lore);
	}

	public ItemStack create(Material material, String name, String[] lore, Enchantment... enchantment)
	{
		return create(material, 1, (short) 0, name, lore, enchantment);
	}

	public ItemStack kitDisplay(Material material, String name, String[] description, Color color)
	{
		ItemStack item = new ItemStack(material);

		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();

		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(description));
		meta.setColor(color);

		item.setItemMeta(meta);

		return item;
	}
}
