package src.ares.core.battle;

import java.util.Random;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

/**
 * Represents a wrapper class, can drop items in a world with specific properties.
 */
public class RandomDrop
{
	private Random random;
	private int chance;
	private ItemStack item;

	/**
	 * Default Constructor
	 * 
	 * @param dropItem The item display to be dropped.
	 * @param dropChance The highest drop chance of the display.
	 */
	public RandomDrop(ItemStack dropItem, int dropChance)
	{
		item = dropItem;
		random = new Random();
		chance = dropChance;
	}

	/**
	 * Drops a preset (1) amount of a certain item in a certain world.
	 * 
	 * @param world The target world.
	 * @param location The target location.
	 */
	public void dropItem(World world, Location location)
	{
		dropItem(world, location, 1);
	}

	/**
	 * Drops a certain amount of the same item in a certain world.
	 * 
	 * @param world The target world.
	 * @param location The target location.
	 * @param amount The maximum amount of that drop.
	 */
	public void dropItem(World world, Location location, int amount)
	{
		int rndAmount = random.nextInt(amount) + 1;
		int rndChance = random.nextInt(100);

		item.setAmount(rndAmount);

		if (rndChance <= chance)
		{
			world.dropItemNaturally(location, item);
			world.playEffect(location, Effect.FLAME, 1);
		}
	}

	public ItemStack getItem()
	{
		return item;
	}
}
