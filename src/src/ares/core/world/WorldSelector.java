package src.ares.core.world;

import java.util.Random;

import org.bukkit.entity.Player;

import src.ares.core.common.util.Chat;

public class WorldSelector
{
	private static WorldManager manager = WorldManager.getInstance();
	private static Random random = new Random();

	public static CoreWorldHub selectHub(Player player)
	{
		int randomHub = random.nextInt(manager.getHubWorlds().size());
		CoreWorldHub selectedHub = manager.getHubWorlds().get(randomHub);

		// player.sendMessage(Chat.format("World", "You have been sent to " + Chat.tool(selectedHub.getRealName()) + "."));

		selectedHub.gotoWorld(player);
		return selectedHub;
	}

	public static CoreWorldBattle selectPvP(Player player)
	{
		int randomPvP = random.nextInt(manager.getPvPWorlds().size());
		CoreWorldBattle selectedPvP = manager.getPvPWorlds().get(randomPvP);

		player.sendMessage(Chat.format("World", "You have been sent to " + Chat.tool(selectedPvP.getRealName()) + "."));

		selectedPvP.gotoWorld(player);
		return selectedPvP;
	}
}
