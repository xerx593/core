package src.ares.core.gadget.type;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import src.ares.core.Main;
import src.ares.core.client.Rank;
import src.ares.core.common.item.CraftedItemStack;
import src.ares.core.currency.type.AmbrosiaCurrency;
import src.ares.core.gadget.HandGadget;

public class GadgetHandOreoPocket extends HandGadget
{
	private int taskID;

	public GadgetHandOreoPocket()
	{
		super("Oreo Pocket Gadget", Material.COOKIE, Rank.PLAYER, new AmbrosiaCurrency(10));
	}

	@Override
	public void useHandGadget(Player player)
	{
		Vector dropsite = player.getLocation().getDirection().normalize().multiply(0.8);

		final Item item = player.getWorld().dropItem(player.getLocation().add(0, 1.5, 0), new CraftedItemStack(Material.COOKIE, "Oreo").addEnchantment(Enchantment.LUCK, 1, false).pack());
		item.setVelocity(dropsite);

		player.getWorld().playSound(player.getLocation(), Sound.DIG_SAND, 1.0F, 1.3F);
		playEffect(item);
	}

	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event)
	{
		if (event.getItem() == null)
			return;

		Player player = event.getPlayer();
		ItemStack item = event.getItem().getItemStack();

		if (item.getType() == Material.COOKIE && item.getItemMeta().getDisplayName().contains("Oreo"))
		{
			event.setCancelled(true);
			event.getItem().remove();

			player.getWorld().playSound(player.getLocation(), Sound.EAT, 1.0F, 1.0F);

			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 5, 1));
			player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 5, 2));

			if (taskID != 0)
			{
				getScheduler().cancelTask(taskID);
			}
		}
	}

	private void playEffect(final Item item)
	{
		taskID = getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable()
		{
			@Override
			public void run()
			{
				if (item.isOnGround() && !item.isDead())
				{
					Location location = item.getLocation();
					World world = item.getWorld();

					world.playEffect(location.add(0, 0.3, 0), Effect.HAPPY_VILLAGER, 5);
					world.playEffect(location.add(0, 0.3, 0), Effect.HAPPY_VILLAGER, 5);
				}
			}
		}, 0L, 40L);
	}
}
