package src.ares.core.npc;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import src.ares.core.Main;
import src.ares.core.common.Module;
import src.ares.core.common.util.UtilString;

public class CraftedEntity extends Module
{
	private NPCManager manager = NPCManager.getInstance();

	private LivingEntity wrapper;
	private String name;
	private World world;
	private Location location;

	private boolean freeze;
	private boolean target;

	public CraftedEntity(EntityType type, String name, World world, Location location)
	{
		super("NPC");

		manager.addEntity(this);
		this.wrapper = (LivingEntity) world.spawnEntity(location, type);
		this.name = name;
		this.world = world;
		this.location = location;

		setupProperties();
		Main.debug("Creating " + UtilString.format(type.name()) + " on " + world.getName() + ".");
	}

	private void setupProperties()
	{
		wrapper.setCustomNameVisible(true);
		wrapper.setCustomName(ChatColor.GREEN + name);
	}

	public void setCanWalk(boolean freeze)
	{
		this.freeze = freeze;
	}

	public boolean canWalk()
	{
		return freeze;
	}

	public void setTarget(boolean target)
	{
		this.target = target;
	}

	public boolean canTarget()
	{
		return target;
	}

	public LivingEntity getEntity()
	{
		return wrapper;
	}

	public World getWorld()
	{
		return world;
	}

	public Location getLocation()
	{
		return location;
	}
}
