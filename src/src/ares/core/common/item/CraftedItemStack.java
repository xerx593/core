package src.ares.core.common.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CraftedItemStack
{
	private ItemStack itemStack;
	private ItemMeta itemMeta;

	private Material material;
	private String displayName;
	private String[] lore;

	private int amount = 1;
	private byte data;
	private short durability;
	private boolean unbreakable;

	private List<CraftedEnchantment> enchantments;

	public CraftedItemStack(Material material, String displayName, String lore[])
	{
		this.material = material;
		this.displayName = displayName;
		this.lore = lore;

		this.itemStack = null;
		this.itemMeta = null;
		this.enchantments = new ArrayList<>();
	}

	public CraftedItemStack(Material material)
	{
		this(material, null, new String[] {});
	}

	public CraftedItemStack(Material material, String displayName)
	{
		this(material, displayName, new String[] {});
	}

	public CraftedItemStack addEnchantment(Enchantment enchantment, int level, boolean bypassLimit)
	{
		this.enchantments.add(new CraftedEnchantment(enchantment, level, bypassLimit));
		return this;
	}
	
	public CraftedItemStack glow()
	{
		enchantments.add(new CraftedEnchantment(Enchantment.DURABILITY, 1, false));
		return this;
	}

	public ItemStack pack()
	{
		this.itemStack = new ItemStack(material, amount, (byte) data);
		this.itemMeta = itemStack.getItemMeta();

		if (displayName != null)
			itemMeta.setDisplayName(ChatColor.WHITE + displayName);

		if (lore != null)
			itemMeta.setLore(Arrays.asList(lore));

		if (unbreakable)
			itemMeta.spigot().setUnbreakable(true);

		// itemStack.setDurability(durability);

		for (CraftedEnchantment e : enchantments)
		{
			itemMeta.addEnchant(e.getEnchantment(), e.getLevel(), e.canBypassLimit());
		}

		itemStack.setItemMeta(itemMeta);

		return itemStack;
	}

	public CraftedItemStack setAmount(int amount)
	{
		if (amount > 64 || amount < 1)
			this.amount = 1;
		else this.amount = amount;

		return this;
	}

	public CraftedItemStack setData(byte data)
	{
		this.data = data;
		return this;
	}

	public CraftedItemStack setDurability(short durability)
	{
		this.durability = durability;
		return this;
	}

	public CraftedItemStack setLore(String[] lore)
	{
		this.lore = lore;
		return this;
	}

	public CraftedItemStack setType(Material type)
	{
		material = type;
		return this;
	}

	public CraftedItemStack setUnbreakable(boolean unbreakable)
	{
		this.unbreakable = unbreakable;
		return this;
	}

	public int getAmount()
	{
		return amount;
	}

	public byte getData()
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
}
