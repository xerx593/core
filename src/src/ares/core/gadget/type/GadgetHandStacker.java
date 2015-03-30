package src.ares.core.gadget.type;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import src.ares.core.client.Rank;
import src.ares.core.common.util.Chat;
import src.ares.core.currency.type.AmbrosiaCurrency;
import src.ares.core.gadget.HandGadget;

public class GadgetHandStacker extends HandGadget
{
	private Player target;

	public GadgetHandStacker()
	{
		super("Stacker Gadget", Material.SADDLE, Rank.PLAYER, new AmbrosiaCurrency(10));
	}

	@EventHandler
	public void onPlayerToggleSneak(PlayerToggleSneakEvent event)
	{
		if (event.getPlayer().getPassenger() != null)
		{
			event.getPlayer().setPassenger(null);
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEntityEvent event)
	{
		if (validate(event.getPlayer()) && event.getRightClicked() instanceof Player)
		{
			Player player = event.getPlayer();
			target = (Player) event.getRightClicked();

			event.setCancelled(true);
			useHandGadget(player);
		}
	}

	@Override
	public void useHandGadget(Player player)
	{
		if (!charge(player))
		{
			player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
			return;
		}

		player.sendMessage(Chat.format(getModuleName(), "Press " + Chat.tool("F5") + " to toggle third person view."));
		target.sendMessage(Chat.format(getModuleName(), "Press " + Chat.tool("Crouch") + " to stop being stacked."));
		player.setPassenger(target);
	}
}
