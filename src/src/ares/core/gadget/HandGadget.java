package src.ares.core.gadget;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import src.ares.core.chat.FlashNotification;
import src.ares.core.client.Rank;
import src.ares.core.common.util.Chat;
import src.ares.core.common.util.UtilLocation;
import src.ares.core.currency.ICurrency;
import src.ares.core.world.CoreWorld;
import src.ares.core.world.WorldManager;
import src.ares.core.world.WorldType;

/**
 * Represents a sub-gadget form that can be clicked from a player.
 * 
 * @see {@link Gadget}
 */
public abstract class HandGadget extends Gadget
{
	private GadgetManager manager = GadgetManager.getInstance();
	private static final int SLOT = 5;

	public HandGadget(String name, Material material, Rank rank, ICurrency currency)
	{
		super(name, material, rank, currency);
	}

	public abstract void useHandGadget(Player player);

	public void onEnable(Player player)
	{
		player.getInventory().setItem(SLOT, getDisplay());
		player.getInventory().setHeldItemSlot(SLOT);
	}

	public void onDisable(Player player)
	{
		player.getInventory().setItem(SLOT, new ItemStack(Material.AIR));
	}

	public void reEnable(Player player)
	{
		if (manager.getToggledGadgets().containsKey(player))
		{
			if (manager.getToggledGadgets().get(player).equals(this))
			{
				manager.getToggledGadgets().put(player, this);
				player.getInventory().setItem(SLOT, getDisplay());
				player.getInventory().setHeldItemSlot(SLOT);
				player.sendMessage(Chat.format(getModuleName(), "You have enabled " + Chat.gadget(getName()) + "."));
			}
		}
	}

	public boolean validate(Player player)
	{
		CoreWorld world = WorldManager.getInstance().getWorld(player.getWorld().getName());

		if (!(manager.getToggledGadgets().containsKey(player)))
			return false;
		if (player.getItemInHand().getType() != getDisplay().getType())
			return false;
		if (!player.getItemInHand().hasItemMeta())
			return false;
		if (world.getWorldType() != WorldType.HUB)
			return false;
		if (player.getGameMode() == GameMode.CREATIVE)
			return false;

		if (UtilLocation.isInsideParkour(player))
		{
			FlashNotification.getInstance().send(player, Chat.format(getModuleName(), "You cannot use gadgets in parkour."));
			return false;
		}

		return true;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();

		if (validate(player))
		{
			Action action = event.getAction();

			if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)
			{
				event.setCancelled(true);

				if (!charge(player))
				{
					player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
				}
				else
				{
					useHandGadget(player);
				}
			}
		}
	}
}
