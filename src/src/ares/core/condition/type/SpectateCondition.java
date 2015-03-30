package src.ares.core.condition.type;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import src.ares.core.common.util.Chat;
import src.ares.core.common.util.UtilPlayer;
import src.ares.core.condition.Condition;

public class SpectateCondition extends Condition<Player>
{
	private static SpectateCondition instance = new SpectateCondition();

	public static SpectateCondition getCondition()
	{
		return instance;
	}

	public void addSpectator(Player player)
	{
		addItem(player);
		UtilPlayer.reset(player);
		player.setGameMode(GameMode.CREATIVE);
		UtilPlayer.makeInvisible(player);
		player.sendMessage(Chat.format(getType(), "You are now hidden from other players."));

	}

	public void removeSpectator(Player player)
	{
		removeItem(player);
		UtilPlayer.reset(player);
		UtilPlayer.makeVisible(player);
		player.sendMessage(Chat.format(getType(), "You are now visible to other players."));

	}
}
