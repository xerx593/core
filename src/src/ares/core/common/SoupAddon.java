package src.ares.core.common;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import src.ares.core.common.crafted.CraftedItemStack;
import src.ares.core.common.util.ItemStackFactory;
import src.ares.core.world.WorldManager;
import src.ares.core.world.WorldType;

public class SoupAddon extends Module
{
	private static SoupAddon instance = new SoupAddon();

	public static SoupAddon getInstance()
	{
		return instance;
	}

	private CraftedItemStack soup = new CraftedItemStack(Material.MUSHROOM_SOUP, ChatColor.WHITE + "Regeneration Soup");

	public SoupAddon()
	{
		super("Soup");
	}

	private void applySoupEffect(Player player)
	{
		player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1));
	}

	public ItemStack getDisplay()
	{
		return soup.build();
	}

	@EventHandler
	public void onEatSoup(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		boolean isPvP = WorldManager.getInstance().getWorld(player.getWorld().getName()).getWorldType() == WorldType.PVP;
		boolean hasSurvival = player.getGameMode() != GameMode.CREATIVE;

		if (isPvP && hasSurvival)
		{
			if ((player.getItemInHand().getType() == Material.MUSHROOM_SOUP) && player.getItemInHand().getItemMeta().getDisplayName().equals(getDisplay().getItemMeta().getDisplayName()))
			{
				applySoupEffect(player);
				playSoupEffect(player);
				subtractOneSoup(player);
				event.setCancelled(true);
			}
		}
	}

	private void playSoupEffect(Player player)
	{
		Location eat = player.getLocation();
		player.playSound(player.getLocation(), Sound.EAT, 1.0F, 1.0F);
		player.getWorld().playEffect(eat, Effect.STEP_SOUND, 39);
		player.getWorld().playEffect(eat, Effect.STEP_SOUND, 40);
	}

	private void subtractOneSoup(Player player)
	{
		if (player.getItemInHand().getAmount() > 1)
		{
			player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
		}
		else
		{
			player.setItemInHand(ItemStackFactory.getFactory().create(Material.AIR));
		}

		player.updateInventory();
	}
}
