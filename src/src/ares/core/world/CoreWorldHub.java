package src.ares.core.world;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import src.ares.core.chat.CommunityLinks;
import src.ares.core.client.Client;
import src.ares.core.common.crafted.CraftedItemStack;
import src.ares.core.common.util.Chat;
import src.ares.core.common.util.UtilPlayer;
import src.ares.core.gadget.Gadget;
import src.ares.core.gadget.GadgetManager;
import src.ares.core.gadget.HandGadget;
import src.ares.core.menu.MenuManager;

public class CoreWorldHub extends CoreWorld
{
	private MenuManager menuManager = MenuManager.getInstance();
	private GadgetManager gadgetManager = GadgetManager.getInstance();

	public CoreWorldHub(String name)
	{
		super(name, WorldType.HUB);

		setPvE(true);
		setPvE(true);

		setBlockBreak(true);
		setBlockPlace(true);
		setBlockDecay(true);
		setBlockIgnite(false);

		setEntityExplosion(true);
		setEntityGrief(true);
		setEntitySpawn(true);
		setEntityTarget(true);

		setInteract(true);
		setInventory(true);
		setItemDrop(true);
		setItemPickup(true);

		setDeathDrops(true);
		lockTime(TimeState.DAY);
		setLockWeather(true);
	}

	public void addHubItems(Player player)
	{
		Client client = new Client(player);

		player.getInventory().setItem(0, menuManager.getWorldSelectorMenu().getDisplay());
		player.getInventory().setItem(1, menuManager.getMinigameMenu().getDisplay());
		player.getInventory().setItem(2, menuManager.getBattleInventoryMenu().getDisplay());
		player.getInventory().setItem(4, menuManager.getHubInventoryMenu().getDisplay());
		player.getInventory().setItem(6, new CraftedItemStack(Material.NAME_TAG, "Community Links").build());
		player.getInventory().setItem(7, menuManager.getSettingsMenu().getDisplay());
		player.getInventory().setItem(8, menuManager.getProgressMenu().getDisplay(client));

		if (gadgetManager.getToggledGadgets().containsKey(player))
		{
			Gadget gadget = gadgetManager.getToggledGadgets().get(player);

			if (gadget instanceof HandGadget)
			{
				HandGadget handGadget = (HandGadget) gadget;
				handGadget.reEnable(player);
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if (!onWorld(event.getPlayer()))
			return;

		Player player = event.getPlayer();
		ItemStack item = player.getItemInHand();

		if (item == null)
			return;

		if (!item.hasItemMeta())
			return;

		String name = item.getItemMeta().getDisplayName();

		if (name.contains("Community Links") && item.getType() == Material.NAME_TAG)
		{
			CommunityLinks.getInstance().printCollection(player);
		}
		// else if (name.contains("Minigame Server") && item.getType() == Material.COMPASS)
		// {
		// BungeeCord.send(player, "AG-1");
		// }
	}

	private void addInventoryItems(Player player)
	{
		if (onWorld(player))
		{
			UtilPlayer.reset(player);
			addHubItems(player);
			player.updateInventory();
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		double voidDistance = player.getLocation().getY();

		if (onWorld(player) && voidDistance < -10.0)
		{
			blockVoidDamage(player);
			player.sendMessage(Chat.format("Hub", "Be careful, you might get rekt."));
		}
	}

	private void blockVoidDamage(Player player)
	{
		player.teleport(getWorld().getSpawnLocation().add(0, 2, 0));
		player.playSound(player.getLocation(), Sound.CAT_MEOW, 0.5F, 1.0F);
	}

	@EventHandler
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event)
	{
		addInventoryItems(event.getPlayer());
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event)
	{
		addInventoryItems(event.getPlayer());
	}
}
