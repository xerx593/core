package src.ares.core.world;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import src.ares.core.Main;
import src.ares.core.client.Client;
import src.ares.core.common.Module;
import src.ares.core.condition.type.BuildCondition;

public abstract class CoreWorld extends Module
{
	public enum TimeState
	{
		DAY, NIGHT;
	}

	private WorldType type;

	private BuildCondition buildMode;
	private World world;
	private boolean pvp;
	private boolean pve;
	private boolean food;

	private boolean inventory;
	private boolean itemPickup;
	private boolean itemDrop;

	private boolean blockBreak;
	private boolean blockPlace;
	private boolean blockDecay;
	private boolean blockIgnite;

	private boolean entitySpawn;
	private boolean entityTarget;
	private boolean entityExplosion;
	private boolean entityGrief;

	private boolean interact;
	private boolean deathDrops;
	private static boolean lockWeather;

	public static void setLockWeather(boolean lockWeather)
	{
		CoreWorld.lockWeather = lockWeather;
	}

	/**
	 * Default Constructor
	 * 
	 * @param worldName The name of the world.
	 * @param worldType The type of the world.
	 */
	public CoreWorld(String worldName, WorldType worldType)
	{
		super("World");

		if (Bukkit.getWorld(worldName) == null)
		{
			Bukkit.createWorld(new WorldCreator(worldName));
		}

		type = worldType;
		world = Bukkit.getWorld(worldName);
		buildMode = BuildCondition.getCondition();
		registerEvents();
	}

	public CoreWorld(String worldName, WorldType worldType, org.bukkit.WorldType worldKind)
	{
		super("Protection");

		if (Bukkit.getWorld(worldName) == null)
		{
			WorldCreator wc = new WorldCreator(worldName);
			wc.generateStructures(false);
			wc.type(worldKind);
			wc.createWorld();
		}

		type = worldType;
		world = Bukkit.getWorld(worldName);
		buildMode = BuildCondition.getCondition();
		registerEvents();
	}

	public void lockTime(TimeState weatherState)
	{
		if (weatherState == TimeState.DAY)
		{
			Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable()
			{
				@Override
				public void run()
				{
					world.setTime(5000L);
				}
			}, 20L, 100L);
		}
		else if (weatherState == TimeState.NIGHT)
		{
			Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable()
			{
				@Override
				public void run()
				{
					world.setTime(18000L);
				}
			}, 0L, 100L);
		}
	}

	// =========================== //
	// ===== Getters/Setters ===== //
	// =========================== //

	protected void setBlockPlace(boolean blockPlace)
	{
		this.blockPlace = blockPlace;
	}

	protected void setBlockBreak(boolean blockBreak)
	{
		this.blockBreak = blockBreak;
	}

	protected void setBlockIgnite(boolean blockIgnite)
	{
		this.blockIgnite = blockIgnite;
	}

	public void setDifficulty(Difficulty difficulty)
	{
		world.setDifficulty(difficulty);
	}

	protected void setEntitySpawn(boolean entitySpawn)
	{
		this.entitySpawn = entitySpawn;
	}

	protected void setEntityTarget(boolean entityTarget)
	{
		this.entityTarget = entityTarget;
	}

	protected void setEntityExplosion(boolean entityExplosion)
	{
		this.entityExplosion = entityExplosion;
	}

	protected void setEntityGrief(boolean entityGrief)
	{
		this.entityGrief = entityGrief;
	}

	protected void setDeathDrops(boolean deathDrops)
	{
		this.deathDrops = deathDrops;
	}

	protected void setFood(boolean food)
	{
		this.food = food;
	}

	protected void setInteract(boolean interact)
	{
		this.interact = interact;
	}

	protected void setInventory(boolean inventory)
	{
		this.inventory = inventory;
	}

	protected void setItemDrop(boolean itemDrop)
	{
		this.itemDrop = itemDrop;
	}

	protected void setPvE(boolean pve)
	{
		this.pve = pve;
	}

	protected void setPvP(boolean pvp)
	{
		this.pvp = pvp;
	}

	public static boolean isLockWeather()
	{
		return lockWeather;
	}

	public String getName()
	{
		String worldName = world.getName().toLowerCase();
		String formattedName = StringUtils.capitalize(worldName);

		return formattedName + " World";
	}

	public String getRealName()
	{
		return world.getName();
	}

	public Location getSpawnLocation()
	{
		return world.getSpawnLocation().add(0, 2, 0);
	}

	public World getWorld()
	{
		return world;
	}

	public WorldType getWorldType()
	{
		return type;
	}

	public void gotoWorld(Player player)
	{
		player.teleport(getSpawnLocation());
	}
	
	protected void setBlockDecay(boolean blockDecay)
	{
		this.blockDecay = blockDecay;
	}
	
	protected boolean isBlockDecay()
	{
		return blockDecay;
	}
	
	protected void setItemPickup(boolean itemPickup)
	{
		this.itemPickup = itemPickup;
	}
	
	public boolean canPickupItem()
	{
		return itemPickup;
	}

	protected boolean isBlockBreak()
	{
		return blockBreak;
	}

	protected boolean isBlockIgnite()
	{
		return blockIgnite;
	}

	protected boolean isBlockPlace()
	{
		return blockPlace;
	}

	protected boolean isDeathDrops()
	{
		return deathDrops;
	}

	protected boolean isEntityExplosion()
	{
		return entityExplosion;
	}

	protected boolean isEntityGrief()
	{
		return entityGrief;
	}

	protected boolean isEntitySpawn()
	{
		return entitySpawn;
	}

	protected boolean isEntityTarget()
	{
		return entityTarget;
	}

	protected boolean isFood()
	{
		return food;
	}

	protected boolean isInteract()
	{
		return interact;
	}

	protected boolean isInventory()
	{
		return inventory;
	}

	protected boolean isItemDrop()
	{
		return itemDrop;
	}

	protected boolean isPvE()
	{
		return pve;
	}

	protected boolean isPvP()
	{
		return pvp;
	}

	public int onlinePlayers()
	{
		return world.getPlayers().size();
	}

	public int onlineStaff()
	{
		int count = 0;

		for (Player player : world.getPlayers())
		{
			Client client = new Client(player);

			if (client.isStaff())
				count++;
		}

		return count;
	}

	public boolean onWorld(Entity entity)
	{
		return entity.getWorld().equals(world);
	}

	// ================== //
	// ===== Events ===== //
	// ================== //

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e)
	{
		if (onWorld(e.getPlayer()))
		{
			if ((buildMode.hasItem(e.getPlayer()) || e.getPlayer().isOp()))
				return;
			e.setCancelled(blockPlace);
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e)
	{
		if (onWorld(e.getPlayer()))
		{
			if ((buildMode.hasItem(e.getPlayer()) || e.getPlayer().isOp()))
				return;
			e.setCancelled(blockBreak);
		}
	}

	@EventHandler
	public void onLeavesDecay(LeavesDecayEvent e)
	{
		if (e.getBlock() == null)
			return;
		
		if (e.getBlock().getWorld().equals(getWorld()))
		{
			if (blockDecay)
				e.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockIgnite(BlockIgniteEvent event)
	{
		if (event.getIgnitingEntity() == null)
			return;

		if (onWorld(event.getIgnitingEntity()))
		{
			if (blockIgnite)
			{
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent e)
	{
		if (e.getEntity() instanceof Entity)
		{
			if (onWorld(e.getEntity()))
				e.setCancelled(pve);
		}
		else if (e.getEntity() instanceof Player)
		{
			if (onWorld(e.getEntity()))
				e.setCancelled(pvp);
		}
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent e)
	{
		if (onWorld(e.getEntity()))
		{
			if (deathDrops)
			{
				e.getDrops().clear();
				e.setDroppedExp(0);
			}
		}
	}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent e)
	{
		try
		{
			if (onWorld(e.getEntity()))
				e.setCancelled(entityExplosion);
		}
		catch (NullPointerException npe)
		{
			//
		}
	}

	@EventHandler
	public void onEntityChangeBlock(EntityChangeBlockEvent e)
	{
		if (onWorld(e.getEntity()))
		{
			if (e.getEntity() instanceof LivingEntity)
			{
				e.setCancelled(entityGrief);
			}
		}
	}

	@EventHandler
	public void onEntityTarget(EntityTargetEvent e)
	{
		if (onWorld(e.getEntity()))
			e.setCancelled(entityTarget);
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent e)
	{
		if (onWorld(e.getPlayer()))
		{
			if ((buildMode.hasItem(e.getPlayer()) || e.getPlayer().isOp()))
				return;
			e.setCancelled(itemDrop);
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e)
	{
		if (onWorld(e.getPlayer()) && !e.getPlayer().isOp() && !buildMode.hasItem(e.getPlayer()))
			e.setCancelled(interact);
	}

	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent e)
	{
		Player player = e.getPlayer();

		if (onWorld(player))
		{
			if ((buildMode.hasItem(player) || player.isOp()))
				return;
			
			if (itemPickup)
				e.setCancelled(true);
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e)
	{
		if (!(e.getWhoClicked() instanceof Player))
			return;

		if (onWorld(e.getWhoClicked()) && !e.getWhoClicked().isOp() && !buildMode.hasItem((Player) e.getWhoClicked()))
			e.setCancelled(inventory);
	}

	@EventHandler
	public void onInventoryInteract(InventoryInteractEvent e)
	{
		if (!(e.getWhoClicked() instanceof Player))
			return;

		if (onWorld(e.getWhoClicked()) && !e.getWhoClicked().isOp() && !buildMode.hasItem((Player) e.getWhoClicked()))
			e.setCancelled(inventory);
	}

	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent e)
	{
		if (onWorld(e.getEntity()))
		{
			e.setFoodLevel(20);
			e.setCancelled(food);
		}
	}

	@EventHandler
	public void onWeatherChange(WeatherChangeEvent event)
	{
		if (event.getWorld().getName().equals(this.world.getName()))
		{
			if (lockWeather)
			{
				event.setCancelled(true);
			}
		}
	}
}
