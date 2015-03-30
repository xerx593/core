package src.ares.core.battle.ability;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import src.ares.core.Main;
import src.ares.core.battle.kit.Kit;

public abstract class HotkeyAbility extends Ability
{
	// The material to be used in the event //
	private Material handItem;

	public HotkeyAbility(Kit storedKit, String kitName, Material kitHandItem)
	{
		super(storedKit, kitName);
		handItem = kitHandItem;
	}

	/**
	 * Gets the hand material.
	 * 
	 * @return Material
	 */
	public Material getHandItem()
	{
		return handItem;
	}

	/**
	 * We are triggering the event here to prevent copy-paste in each Kit.
	 * Checking if the player holds the handItem specified in the constructor
	 * and if true, we are triggering the use of the ability.
	 */
	@EventHandler
	public void onAbilityToggle(PlayerDropItemEvent event)
	{
		Player player = event.getPlayer();
		ItemStack itemDrop = event.getItemDrop().getItemStack();

		if (validate(player) && itemDrop.getType().equals(handItem))
		{
			event.setCancelled(true);
			toggleUse(player);
			Main.debug("Triggering " + getName() + " on " + getKit().getName() + " for " + player.getName() + ".");
		}
	}
}
