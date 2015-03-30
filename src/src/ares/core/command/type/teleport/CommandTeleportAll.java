package src.ares.core.command.type.teleport;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;

public class CommandTeleportAll extends CoreCommand
{
	public CommandTeleportAll()
	{
		super("tpall", new String[] {}, 0, Rank.ADMIN);
	}

    @SuppressWarnings("deprecation")
    public void execute()
    {
    	for (Player players : Bukkit.getOnlinePlayers())
    	{
    		if (players.equals(getClient().getPlayer()))
    			continue;
    		
    		players.teleport(getClient().getLocation());
    	}
    	
    	getClient().sendMessage(getModuleName(), "All players teleported to your location.");
    }
}
