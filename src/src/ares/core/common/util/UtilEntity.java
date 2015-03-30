package src.ares.core.common.util;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import src.ares.core.common.item.CraftedItemStack;

public class UtilEntity
{
	private static Random random = new Random();

	private static ItemStack[] weapons =
	{
	new ItemStack(Material.WOOD_SWORD), new ItemStack(Material.STONE_SWORD), new ItemStack(Material.IRON_SWORD), new ItemStack(Material.GOLD_SWORD), new ItemStack(Material.DIAMOND_SWORD)
	};

	private static ItemStack[] helmets =
	{
		new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.CHAINMAIL_HELMET), new ItemStack(Material.IRON_HELMET), new ItemStack(Material.GOLD_HELMET), new ItemStack(Material.DIAMOND_HELMET)
	};

	private static ItemStack[] chestplates =
	{
		new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.CHAINMAIL_CHESTPLATE), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.GOLD_CHESTPLATE), new ItemStack(Material.DIAMOND_CHESTPLATE)
	};

	private static ItemStack[] leggings =
	{
		new ItemStack(Material.LEATHER_LEGGINGS), new ItemStack(Material.CHAINMAIL_LEGGINGS), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.GOLD_LEGGINGS), new ItemStack(Material.DIAMOND_LEGGINGS)
	};

	private static ItemStack[] boots =
	{
		new ItemStack(Material.LEATHER_BOOTS), new ItemStack(Material.CHAINMAIL_BOOTS), new ItemStack(Material.IRON_BOOTS), new ItemStack(Material.GOLD_BOOTS), new ItemStack(Material.DIAMOND_BOOTS)
	};

	@SuppressWarnings("deprecation")
	public static void randomArmor(LivingEntity entity)
	{
		ItemStack helmet = helmets[random.nextInt(helmets.length)];
		ItemStack chestplate = chestplates[random.nextInt(chestplates.length)];
		ItemStack legging = leggings[random.nextInt(leggings.length)];
		ItemStack boot = boots[random.nextInt(boots.length)];

		entity.getEquipment().setHelmet(helmet);
		entity.getEquipment().setChestplate(chestplate);
		entity.getEquipment().setLeggings(legging);
		entity.getEquipment().setBoots(boot);

		entity.setHealth(random.nextInt(12) + 8);
	}

	public static void randomWeapon(LivingEntity entity)
	{
		ItemStack weapon = weapons[random.nextInt(weapons.length)];
		entity.getEquipment().setItemInHand(weapon);
	}

	public static void equipChainArmor(LivingEntity entity)
	{
		entity.getEquipment().setHelmet(new CraftedItemStack(Material.CHAINMAIL_HELMET).pack());
		entity.getEquipment().setChestplate(new CraftedItemStack(Material.CHAINMAIL_CHESTPLATE).pack());
		entity.getEquipment().setLeggings(new CraftedItemStack(Material.CHAINMAIL_LEGGINGS).pack());
		entity.getEquipment().setBoots(new CraftedItemStack(Material.CHAINMAIL_BOOTS).pack());
	}

	public static void equipDiamondArmor(LivingEntity entity)
	{
		entity.getEquipment().setHelmet(new CraftedItemStack(Material.DIAMOND_HELMET).pack());
		entity.getEquipment().setChestplate(new CraftedItemStack(Material.DIAMOND_CHESTPLATE).pack());
		entity.getEquipment().setLeggings(new CraftedItemStack(Material.DIAMOND_LEGGINGS).pack());
		entity.getEquipment().setBoots(new CraftedItemStack(Material.DIAMOND_BOOTS).pack());
	}

	public static void equipGoldArmor(LivingEntity entity)
	{
		entity.getEquipment().setHelmet(new CraftedItemStack(Material.GOLD_HELMET).pack());
		entity.getEquipment().setChestplate(new CraftedItemStack(Material.GOLD_CHESTPLATE).pack());
		entity.getEquipment().setLeggings(new CraftedItemStack(Material.GOLD_LEGGINGS).pack());
		entity.getEquipment().setBoots(new CraftedItemStack(Material.GOLD_BOOTS).pack());
	}

	public static void equipIronArmor(LivingEntity entity)
	{
		entity.getEquipment().setHelmet(new CraftedItemStack(Material.IRON_HELMET).pack());
		entity.getEquipment().setChestplate(new CraftedItemStack(Material.IRON_CHESTPLATE).pack());
		entity.getEquipment().setLeggings(new CraftedItemStack(Material.IRON_LEGGINGS).pack());
		entity.getEquipment().setBoots(new CraftedItemStack(Material.IRON_BOOTS).pack());
	}

	public static void equipLeatherArmor(LivingEntity entity)
	{
		entity.getEquipment().setHelmet(new CraftedItemStack(Material.LEATHER_HELMET).pack());
		entity.getEquipment().setChestplate(new CraftedItemStack(Material.LEATHER_CHESTPLATE).pack());
		entity.getEquipment().setLeggings(new CraftedItemStack(Material.LEATHER_LEGGINGS).pack());
		entity.getEquipment().setBoots(new CraftedItemStack(Material.LEATHER_BOOTS).pack());
	}
}
