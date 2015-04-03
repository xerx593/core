package src.ares.core.command.type.staff;

import java.util.ArrayList;

import org.bukkit.GameMode;

import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;

public class CommandGameMode extends CoreCommand
{
	public static ArrayList<String> gamemodes = new ArrayList<String>();

	public CommandGameMode()
	{
		super("gamemode", new String[]
		{
			"gm"
		}, 1, Rank.ADMIN, "<survival | creative | adventure> OR <0 | 1 | 2>");
	}

	@Override
	public void execute()
	{
		if (getArgs()[0].equals("survival") || getArgs()[0].equals("0"))
		{
			getClient().setGameMode(GameMode.SURVIVAL);
		}
		else if (getArgs()[0].equals("creative") || getArgs()[0].equals("1"))
		{
			getClient().setGameMode(GameMode.CREATIVE);
		}
		else if (getArgs()[0].equals("adventure") || getArgs()[0].equals("2"))
		{
			getClient().setGameMode(GameMode.ADVENTURE);
		}
		else
		{
			getClient().sendMessage("Gamemode", "That type of gamemode is invalid.");
		}
	}
}
