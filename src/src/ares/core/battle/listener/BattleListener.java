package src.ares.core.battle.listener;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import src.ares.core.battle.BattleManager;
import src.ares.core.battle.kit.Kit;
import src.ares.core.chat.Notification;
import src.ares.core.client.Client;
import src.ares.core.common.Module;
import src.ares.core.common.util.Chat;
import src.ares.core.common.util.UtilPlayer;
import src.ares.core.common.util.UtilString;
import src.ares.core.world.CoreWorld;
import src.ares.core.world.WorldManager;
import src.ares.core.world.WorldSelector;
import src.ares.core.world.WorldType;

public class BattleListener extends Module
{
	private static BattleListener instance = new BattleListener();

	public static BattleListener getInstance()
	{
		return instance;
	}

	public BattleListener()
	{
		super("Battle");
	}

	private BattleManager battleManager = BattleManager.getInstance();
	private Notification notification = new Notification();

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerChangeWorld(PlayerChangedWorldEvent event)
	{
		Player player = event.getPlayer();
		Client client = new Client(player);
		CoreWorld world = WorldManager.getInstance().getWorld(player.getWorld().getName());

		// Checking if the player chose a PvP world and checking if he's on survival.

		if (world.getWorldType() == WorldType.PVP && client.getGameMode() != GameMode.CREATIVE)
		{
			// Getting information about the player Kit and converting the players list to array.

			Kit kit = battleManager.getKitPreference(player);
			Player[] players = world.getWorld().getPlayers().toArray(new Player[world.getWorld().getPlayers().size()]);

			if (kit == null)
				return;

			// Resetting the player and sending a notification to all other players.

			int level = client.getManager().getKitLevel(kit);

			UtilPlayer.reset(player);
			notification.sendToPlayers(false, players, getModuleName(), Chat.player(player.getName()) + " joined the battle with " + Chat.kit(kit.getName() + " " + level) + ".");

			BattleHelper.battleProtectionStart(player, kit);
			client.unload();
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDeath(PlayerDeathEvent event)
	{
		Player victim = event.getEntity();
		Player killer = event.getEntity().getKiller();

		CoreWorld coreWorld = new Client(victim).getCoreWorld();
		World world = victim.getWorld();
		Location location = victim.getLocation();

		event.setDeathMessage(null);
		BattleHelper.respawn(victim);

		if (killer == null)
		{
			DamageCause cause = victim.getLastDamageCause().getCause();
			String attacker = UtilString.format(victim.getLastDamageCause().getEntityType().name());

			if (victim.getLastDamageCause() instanceof EntityDamageByEntityEvent)
			{
				EntityDamageByEntityEvent otherEvent = (EntityDamageByEntityEvent) victim.getLastDamageCause();
				cause = otherEvent.getCause();
				attacker = UtilString.format(otherEvent.getDamager().getType().name());

				switch (cause)
				{
				case ENTITY_ATTACK:
					BattleHelper.broadcast(world, Chat.format("Death", Chat.player(victim.getName()) + " was killed by " + Chat.player(attacker) + "."));
					break;
				case ENTITY_EXPLOSION:
					BattleHelper.broadcast(world, Chat.format("Death", Chat.player(victim.getName()) + " was exploded by " + Chat.player(attacker) + "."));
					break;
				case FALLING_BLOCK:
					BattleHelper.broadcast(world, Chat.format("Death", Chat.player(victim.getName()) + " was suffocated by a " + Chat.player(attacker) + "."));
					break;
				case MAGIC:
					BattleHelper.broadcast(world, Chat.format("Death", Chat.player(victim.getName()) + " was harry pottered by " + Chat.player(attacker) + "."));
					break;
				default:
					BattleHelper.broadcast(world, Chat.format("Death", Chat.player(victim.getName()) + " has died."));
				}

				return;
			}

			switch (cause)
			{
			case BLOCK_EXPLOSION:
				BattleHelper.broadcast(world, Chat.format("Death", Chat.player(victim.getName()) + " was exploded in million pieces."));
				break;
			case DROWNING:
				BattleHelper.broadcast(world, Chat.format("Death", Chat.player(victim.getName()) + " drank too much water."));
				break;
			case FALL:
				BattleHelper.broadcast(world, Chat.format("Death", Chat.player(victim.getName()) + " tried to touch the sky."));
				break;
			case FIRE:
				BattleHelper.broadcast(world, Chat.format("Death", Chat.player(victim.getName()) + " burned to death."));
				break;
			case LAVA:
				BattleHelper.broadcast(world, Chat.format("Death", Chat.player(victim.getName()) + " had a bath with Lava."));
				break;
			case LIGHTNING:
				BattleHelper.broadcast(world, Chat.format("Death", Chat.player(victim.getName()) + " was thunderbolted by the mad gods."));
				break;
			case POISON:
				BattleHelper.broadcast(world, Chat.format("Death", Chat.player(victim.getName()) + " tried to drink poison."));
				break;
			case STARVATION:
				BattleHelper.broadcast(world, Chat.format("Death", Chat.player(victim.getName()) + " starved to death."));
				break;
			case PROJECTILE:
				BattleHelper.broadcast(world, Chat.format("Death", Chat.player(victim.getName()) + " was shot in the head."));
				break;
			case MELTING:
				BattleHelper.broadcast(world, Chat.format("Death", Chat.player(victim.getName()) + " melted in the ice."));
				break;
			case SUFFOCATION:
				BattleHelper.broadcast(world, Chat.format("Death", Chat.player(victim.getName()) + " tried to hug the wall."));
				break;
			case SUICIDE:
				BattleHelper.broadcast(world, Chat.format("Death", Chat.player(victim.getName()) + " couldn't lift all this pressure."));
				break;
			case VOID:
				BattleHelper.broadcast(world, Chat.format("Death", Chat.player(victim.getName()) + " tried to visit the nether."));
				break;
			default:
				BattleHelper.broadcast(world, Chat.format("Death", Chat.player(victim.getName()) + " had a tragic death."));
				break;
			}
		}
		else
		{
			if (coreWorld.getWorldType() != WorldType.PVP)
				return;

			String weapon = UtilString.format(killer.getInventory().getItemInHand().getType().name()).replace("Air", "Fists");
			String deathMsg = Chat.player(victim.getName()) + " was killed by " + Chat.player(killer.getName()) + " with " + Chat.tool(weapon) + ".";

			if (!killer.equals(victim))
			{
				killer.sendMessage(Chat.format("Death", deathMsg));
				BattleHelper.reward(killer);
				BattleHelper.drops(world, location);
			}
		}
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event)
	{
		LivingEntity entity = event.getEntity();
		Player killer = entity.getKiller();
		
		if (killer == null)
			return;
		
		Client client = new Client(killer);

		if (client.getCoreWorld().getWorldType() == WorldType.PVP)
		{
			BattleHelper.drops(entity.getWorld(), entity.getLocation());
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerRespawn(PlayerRespawnEvent event)
	{
		WorldSelector.selectHub(event.getPlayer());
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerPickupItem(PlayerPickupItemEvent event)
	{
		Player player = event.getPlayer();
		Client client = new Client(player);
		ItemStack item = event.getItem().getItemStack();
		CoreWorld world = client.getCoreWorld();

		if (world.getWorldType() == WorldType.PVP && client.getGameMode() != GameMode.CREATIVE)
		{
			if (item.getType() == Material.FERMENTED_SPIDER_EYE)
			{
				Damageable health = player;

				if (health.getMaxHealth() >= 40.0)
				{
					event.setCancelled(true);
					return;
				}

				health.setMaxHealth(health.getMaxHealth() + 2.0);
				player.playSound(player.getLocation(), Sound.LAVA_POP, 1.0F, 1.0F);

				event.getItem().remove();
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();
		
		if (battleManager.getKitPreferences().containsKey(player))
			battleManager.getKitPreferences().remove(player);
	}
}
