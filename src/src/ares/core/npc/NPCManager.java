package src.ares.core.npc;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityTargetEvent;

import src.ares.core.Main;
import src.ares.core.common.Module;
import src.ares.core.updater.UpdateEvent;
import src.ares.core.world.CoreWorld;
import src.ares.core.world.WorldManager;

public class NPCManager extends Module
{
	private static NPCManager instance = new NPCManager();

	public static NPCManager getInstance()
	{
		return instance;
	}

	private List<CraftedEntity> craftedEntityBag = new ArrayList<>();
	private List<LivingEntity> entityBag = new ArrayList<>();

	@EventHandler
	public void updatePositions(UpdateEvent event)
	{
		for (CraftedEntity entity : craftedEntityBag)
		{
			if (!entity.canWalk())
			{
				entity.getEntity().teleport(entity.getLocation());
			}
		}
	}

	@EventHandler
	public void updateTarget(EntityTargetEvent event)
	{
		if (event.getEntity() instanceof LivingEntity)
		{
			for (CraftedEntity entity : craftedEntityBag)
			{
				if (!entity.canTarget())
				{
					event.setCancelled(true);
				}
			}
		}
	}

	public void clearEntities()
	{
		for (CoreWorld world : WorldManager.getInstance().getWorldBag())
		{
			for (Entity entity : world.getWorld().getEntities())
			{
				if (entity instanceof LivingEntity)
				{
					LivingEntity living = (LivingEntity) entity;

					if (entityBag.contains(living))
					{
						living.remove();
						Main.debug("Removing some entities...");
					}
				}
			}
		}
	}

	public void addEntity(CraftedEntity entity)
	{
		craftedEntityBag.add(entity);
		entityBag.add(entity.getEntity());
	}

	public void removeEntity(CraftedEntity entity)
	{
		if (craftedEntityBag.contains(entity) && entityBag.contains(entity.getEntity()))
		{
			craftedEntityBag.remove(entity);
			entityBag.remove(entity.getEntity());
		}
	}

	public List<CraftedEntity> getEntityBag()
	{
		return craftedEntityBag;
	}

	public List<LivingEntity> getOriginalEntities()
	{
		return entityBag;
	}
}
