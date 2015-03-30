package src.ares.core.client.premium;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import src.ares.core.client.Client;
import src.ares.core.client.Rank;
import src.ares.core.common.Module;
import src.ares.core.common.util.UtilLocation;
import src.ares.core.condition.type.FlyCondition;
import src.ares.core.world.WorldType;

public class EliteListener extends Module
{
	private static EliteListener instance = new EliteListener();
	private FlyCondition flyCondition = FlyCondition.getCondition();
	
	public static EliteListener getInstance()
	{
		return instance;
	}

	public EliteListener()
	{
		super("Premium");
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		
		if (parkourJumpCheck(player))
		{
			player.setFlying(false);
			player.setAllowFlight(false);
		}

		if (canDoubleJump(player, true))
		{
			player.setAllowFlight(true);
			player.setFlying(false);
		}
	}

	@EventHandler
	public void onPlayerToggleFlight(PlayerToggleFlightEvent event)
	{
		Player player = event.getPlayer();

		if (canDoubleJump(player, false))
		{
			event.setCancelled(true);
			player.setAllowFlight(false);
			player.setFlying(false);
			player.setVelocity(player.getLocation().getDirection().multiply(1.3D).setY(1.0D));
			player.getLocation().getWorld().playSound(player.getLocation(), Sound.BAT_TAKEOFF, 1.0F, 1.5F);
		}
	}
	
	private boolean parkourJumpCheck(Player player)
	{
		boolean isSurvival = player.getGameMode() == GameMode.SURVIVAL;
		boolean isOnParkour = UtilLocation.isInsideParkour(player);
		boolean isFlying = player.isFlying();
		
		return isSurvival && isOnParkour && isFlying;
	}

	private boolean canDoubleJump(Player player, boolean airCheck)
	{
		Client client = new Client(player);

		boolean isPremium = client.compareWith(Rank.SUPPORTER) || client.compareSecondWith(Rank.SUPPORTER);
		boolean isOnHub = client.getCoreWorld().getWorldType() == WorldType.HUB;
		boolean isFlying = flyCondition.hasItem(player);
		boolean isSurvival = player.getGameMode() == GameMode.SURVIVAL;
		boolean isOnAir = player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR;
		boolean isOnParkour = UtilLocation.isInsideParkour(player);

		if (airCheck)
			return isPremium && isOnHub && isSurvival && !isOnAir && !isFlying && !isOnParkour;
		else
			return isPremium && isOnHub && isSurvival && !isFlying && !isOnParkour;
	}
}
