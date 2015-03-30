package src.ares.core.gadget;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import src.ares.core.client.Rank;
import src.ares.core.currency.type.AmbrosiaCurrency;
import src.ares.core.world.CoreWorld;
import src.ares.core.world.WorldManager;
import src.ares.core.world.WorldType;

/**
 * Represents a sub-gadget form with particles that can be equipped by a player.
 * 
 * @see {@link Gadget}
 */
public abstract class ParticleGadget extends Gadget
{
	private GadgetManager manager = GadgetManager.getInstance();

	public ParticleGadget(String name, Material material, Rank rank)
	{
		super(name, material, rank, new AmbrosiaCurrency(0));
	}

	public void useParticleGadget(Player player)
	{};

	public void stopParticleGadget(Player player)
	{};

	public void cancelTask(int taskId)
	{
		getScheduler().cancelTask(taskId);
	}

	public void onEnable(Player player)
	{
		useParticleGadget(player);
	}

	@Override
	public void onDisable(Player player)
	{
		if (manager.getParticleTasks().containsKey(player))
			cancelTask(manager.getParticleTasks().get(player));
	}

	public boolean validate(Player player)
	{
		CoreWorld world = WorldManager.getInstance().getWorld(player.getWorld().getName());

		if (!(manager.getToggledGadgets().containsKey(player)))
			return false;
		if (manager.getToggledGadgets().get(player) != this)
			return false;
		if (world.getWorldType() != WorldType.HUB)
			return false;
		if (player.getGameMode() == GameMode.CREATIVE)
			return false;

		return true;
	}
}
