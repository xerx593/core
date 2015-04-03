package src.ares.core.battle.listener;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.PlayerInventory;

import src.ares.core.Main;
import src.ares.core.battle.BattleManager;
import src.ares.core.battle.RandomDrop;
import src.ares.core.battle.kit.Kit;
import src.ares.core.client.Client;
import src.ares.core.common.SoupAddon;
import src.ares.core.common.crafted.CraftedEnchantment;
import src.ares.core.common.crafted.CraftedItemStack;
import src.ares.core.common.util.Chat;
import src.ares.core.condition.type.BattleProtectionCondition;
import src.ares.core.condition.type.SpectateCondition;
import src.ares.core.currency.type.AmbrosiaCurrency;
import src.ares.core.currency.type.GoldCurrency;
import src.ares.core.menu.MenuManager;
import src.ares.core.settings.Setting;
import src.ares.core.world.CoreWorld;
import src.ares.core.world.WorldType;

public class BattleHelper
{
	private static Random random = new Random();
	private static SpectateCondition spectateCondition = SpectateCondition.getCondition();
	private static BattleProtectionCondition protectionCondition = BattleProtectionCondition.getCondition();

	/**
	 * Fills in the player inventory (first 8 slots) with wool.
	 * 
	 * @param player The player to fill his inventory.
	 */
	public static void addWool(Player player)
	{
		CraftedItemStack cover = new CraftedItemStack(Material.STAINED_GLASS_PANE, ChatColor.GREEN + "" + ChatColor.BOLD + "You have battle protection");
		PlayerInventory inventory = player.getInventory();

		for (int i = 0; i <= 8; i++)
		{
			inventory.setItem(i, cover.build());
		}

		inventory.setItem(4, MenuManager.getInstance().getBattleInventoryMenu().getDisplay());
	}

	/**
	 * Sends a message to all players in that world.
	 * 
	 * @param world The core world to broadcast.
	 * @param msg The message to broadcast.
	 */
	public static void broadcast(World world, String msg)
	{
		List<Player> players = world.getPlayers();

		for (Player player : players)
		{
			player.sendMessage(msg);
		}
	}

	/**
	 * Gives a gold reward to a specific player.
	 * 
	 * @param player The player to give gold.
	 */
	public static void reward(Player player)
	{
		Client client = new Client(player);
		client.addCurrency(new GoldCurrency(random.nextInt(BattleConst.MAX_GOLD_REWARD) + BattleConst.MIN_GOLD_REWARD), true);
		client.unload();
	}

	/**
	 * Drops a specific amount of items in the ground.
	 * 
	 * @param world The world of the dropsite.
	 * @param location The location of the dropsite.
	 */
	public static void drops(World world, Location location)
	{
		new RandomDrop(SoupAddon.getInstance().getDisplay(), 50).dropItem(world, location);
		new RandomDrop(new AmbrosiaCurrency().getDisplay(), 20).dropItem(world, location, 5);
		new RandomDrop(new CraftedItemStack(Material.FERMENTED_SPIDER_EYE).enchantment(new CraftedEnchantment(Enchantment.LUCK, 1, false)).build(), 20).dropItem(world, location);
	}

	/**
	 * Performs respawn for a player.
	 * 
	 * @param player The player to target
	 */
	public static void respawn(final Player player)
	{
		Client client = new Client(player);
		CoreWorld world = client.getCoreWorld();
		Setting setting = client.getManager().getSetting("Instant Respawn");

		if (world.getWorldType() == WorldType.PVP)
		{
			final Kit kit = BattleManager.getInstance().getKitPreference(player);
			final CoreWorld pvp = new Client(player).getCoreWorld();

			player.setHealth(20.0);

			if (player.getLastDamageCause().getCause() == DamageCause.VOID)
			{
				pvp.gotoWorld(player);
				battleProtectionStart(player, kit);
			}
			else
			{
				spectateCondition.addSpectator(player);

				if (client.getManager().hasEnabledSetting(setting))
				{
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable()
					{
						@Override
						public void run()
						{
							doRespawn(player, kit, pvp);
						}
					}, 20 * BattleConst.MAX_DEATH_OUT_TIME);
				}
				else
				{
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable()
					{
						@Override
						public void run()
						{
							doRespawn(player, kit, pvp);
						}
					}, 20 * BattleConst.MIN_DEATH_OUT_TIME);
				}
			}

			player.updateInventory();
		}
	}

	private static void doRespawn(Player player, Kit kit, CoreWorld pvp)
	{
		spectateCondition.removeSpectator(player);
		pvp.gotoWorld(player);
		battleProtectionStart(player, kit);
	}

	/**
	 * Starts battle protection for a player for 5 seconds. After protection runs out, the player will be equipped with their kit.
	 * 
	 * @param player The player to apply protection.
	 * @param kit The kit to equip after the grace period ends.
	 */
	public static void battleProtectionStart(final Player player, final Kit kit)
	{
		protectionCondition.addItem(player);
		BattleHelper.addWool(player);
		player.sendMessage(Chat.format(protectionCondition.getType(), "You have battle protection for " + Chat.time(BattleConst.PROTECTION_TIME + " seconds") + "."));
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Protection(player), 20 * BattleConst.PROTECTION_TIME);
	}
}
