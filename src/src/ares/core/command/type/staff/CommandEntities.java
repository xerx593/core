package src.ares.core.command.type.staff;

import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;
import src.ares.core.world.CoreWorld;
import src.ares.core.world.WorldManager;

public class CommandEntities extends CoreCommand
{

	public CommandEntities()
	{
		super("entities", new String[] {}, 0, Rank.ADMIN);
	}

	public void execute()
	{
		for (CoreWorld worlds : WorldManager.getInstance().getWorldBag())
		{
			World world = worlds.getWorld();

			for (LivingEntity entities : world.getLivingEntities())
			{
				if (entities instanceof Player)
					continue;

				entities.remove();
			}
		}

		getClient().sendMessage(getModuleName(), "All living entities cleared.");
	}
}
