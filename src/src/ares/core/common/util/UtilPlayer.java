package src.ares.core.common.util;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class UtilPlayer
{
	@SuppressWarnings("deprecation")
	public static void makeVisible(Player player)
	{
		for (Player players : Bukkit.getOnlinePlayers())
		{
			players.showPlayer(player);
		}

		removeActiveEffects(player);
	}

	@SuppressWarnings("deprecation")
	public static void makeInvisible(Player player)
	{
		for (Player players : Bukkit.getOnlinePlayers())
		{
			players.hidePlayer(player);
		}

		player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 99999, 0));
	}

	public static boolean hasActiveEffects(Player player)
	{
		if (!player.getActivePotionEffects().isEmpty())
			return true;
		return false;
	}

	public static boolean hasCreative(Player player)
	{
		if (player.getGameMode().equals(GameMode.CREATIVE))
			return true;
		return false;
	}

	public static boolean isHoldingItemOffensive(Player player)
	{
		Material weapon = player.getItemInHand().getType();

		if (weapon == Material.WOOD_SWORD || weapon == Material.STONE_SWORD || weapon == Material.GOLD_SWORD || weapon == Material.DIAMOND_SWORD || weapon == Material.WOOD_AXE || weapon == Material.STONE_AXE || weapon == Material.GOLD_AXE || weapon == Material.DIAMOND_AXE)
			return true;
		return false;
	}

	public static boolean isOnFire(Player player)
	{
		if (player.getFireTicks() > 0)
			return true;
		return false;
	}

	public static boolean isOnLava(Player player)
	{
		Material standingBlock = player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType();

		if (standingBlock == Material.LAVA || standingBlock == Material.STATIONARY_LAVA)
			return true;
		return false;
	}

	public static boolean isOnWater(Player player)
	{
		Material standingBlock = player.getLocation().getBlock().getType();

		if (standingBlock == Material.WATER || standingBlock == Material.STATIONARY_WATER)
			return true;

		return false;
	}

	public static void removeActiveEffects(Player player)
	{
		for (PotionEffect effect : player.getActivePotionEffects())
		{
			player.removePotionEffect(effect.getType());
		}
	}

	@SuppressWarnings("deprecation")
	public static void reset(Player player)
	{
		CraftPlayer craftPlayer = (CraftPlayer) player;

		player.setGameMode(GameMode.SURVIVAL);
		player.setFlying(false);
		player.setAllowFlight(false);
		player.setLevel(0);
		player.setExp(0);
		player.setMaxHealth(20.0);
		player.setHealth(20.0);
		player.setFoodLevel(20);
		player.setSneaking(false);
		player.setSprinting(false);
		player.setFireTicks(0);
		craftPlayer.getHandle().getDataWatcher().watch(9, (byte) 0); // Removes Stuck Arrows
		removeActiveEffects(player);
		player.closeInventory();
		player.getInventory().clear();
		player.getInventory().setHelmet(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setBoots(null);
		player.getInventory().setHeldItemSlot(0);

		for (Player others : Bukkit.getOnlinePlayers())
		{
			others.showPlayer(player);
		}
	}
}
