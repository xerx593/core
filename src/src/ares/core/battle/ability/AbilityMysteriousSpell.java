package src.ares.core.battle.ability;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import src.ares.core.Main;
import src.ares.core.battle.kit.Kit;
import src.ares.core.client.Client;
import src.ares.core.common.Cooldown;
import src.ares.core.common.item.CraftedItemStack;
import src.ares.core.common.util.Chat;
import src.ares.core.world.WorldType;

public class AbilityMysteriousSpell extends Ability
{
	private Player thrower;
	private int DURATION = 5;
	private double HEART_DAMAGE = 3.5;
	private int taskID = 0;

	public AbilityMysteriousSpell(Kit kit)
	{
		super(kit, "Mysterious Spell");
	}

	@Override
	public String getTip(Client client)
	{
		int level = level(client.getPlayer());
		return Chat.tool("Right-Click") + " to throw a mysterious spell. Player recieves " + Chat.tool("Slow IV") + ", " + Chat.tool("Blindness II") + " for " + Chat.time((DURATION + level) + " seconds") + " and " + Chat.tool(((HEART_DAMAGE / 2) + level) + " heart") + " damage.";
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();

		if (validate(player))
		{
			if (player.getItemInHand().getType() == Material.EYE_OF_ENDER)
			{
				if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
				{
					event.setCancelled(true);
					useAbility(player);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerPickup(PlayerPickupItemEvent event)
	{
		Player player = event.getPlayer();
		ItemStack item = event.getItem().getItemStack();

		Client client = new Client(player);

		if (client.getCoreWorld().getWorldType() == WorldType.PVP)
		{
			if (item.getType() == Material.EYE_OF_ENDER && item.getItemMeta().getDisplayName().contains(getName()))
			{
				event.setCancelled(true);

				if (thrower != null)
				{
					int level = level(thrower);
					player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * (DURATION + level), 4));
					player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * (DURATION + level), 2));
					player.damage(HEART_DAMAGE + level);
				}
				else
				{
					player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * DURATION, 4));
					player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * DURATION, 2));
					player.damage(HEART_DAMAGE);
				}

				World world = player.getWorld();
				Location location = player.getLocation();

				world.playSound(location, Sound.ARROW_HIT, 1.0F, 1.0F);

				world.playEffect(location.add(0, 0.2, 0), Effect.LAVA_POP, 0);
				world.playEffect(location.add(0, 0.3, 0), Effect.LAVA_POP, 0);
				world.playEffect(location.add(0, 0.5, 0), Effect.LAVA_POP, 0);
				world.playEffect(location.add(0, 0.4, 0), Effect.LAVA_POP, 0);

				event.getItem().remove();

				if (taskID != 0)
				{
					getScheduler().cancelTask(taskID);
				}
			}
		}
	}

	@Override
	public void useAbility(Player player)
	{
		thrower = player;

		if (!Cooldown.create(player, 10, getName(), true))
		{
			Vector dropsite = player.getLocation().getDirection().normalize().multiply(0.8);
			Item item = player.getWorld().dropItem(player.getLocation().add(0, 1.5, 0), new CraftedItemStack(Material.EYE_OF_ENDER, getName()).addEnchantment(Enchantment.LUCK, 0, false).pack());
			item.setVelocity(dropsite);

			playEffect(item);
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

					world.playEffect(location.add(0, 0.2, 0), Effect.LAVA_POP, 0);
					world.playEffect(location.add(0, 0.3, 0), Effect.LAVA_POP, 0);
					world.playEffect(location.add(0, 0.5, 0), Effect.LAVA_POP, 0);
					world.playEffect(location.add(0, 0.4, 0), Effect.LAVA_POP, 0);
				}
			}
		}, 0L, 40L);
	}
}
