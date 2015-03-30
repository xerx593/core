package src.ares.core.command;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import src.ares.core.Main;
import src.ares.core.client.Client;
import src.ares.core.client.Rank;
import src.ares.core.common.Module;

public class RankPermissions extends Module
{
	private static RankPermissions instance = new RankPermissions();

	public static RankPermissions getInstance()
	{
		return instance;
	}

	public RankPermissions()
	{
		super("Builder Permissions");
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		Client client = new Client(player);

		if ((client.getManager().getRank().equals(Rank.BUILDER)) || (client.getManager().getSecondRank().equals(Rank.BUILDER)))
		{
			player.addAttachment(Main.getPlugin(), "worldedit.*", true);
			player.addAttachment(Main.getPlugin(), "voxelsniper.sniper", true);
			player.addAttachment(Main.getPlugin(), "voxelsniper.brush.*", true);
			player.addAttachment(Main.getPlugin(), "nocheatplus.checks.inventory.drop", true);
		}

		if (client.isStaff())
		{
			player.addAttachment(Main.getPlugin(), "nocheatplus.notify", true);
			player.addAttachment(Main.getPlugin(), "nocheatplus.command.notify", true);
			player.addAttachment(Main.getPlugin(), "nocheatplus.checks.chat.text", true);
		}

		if (client.compareWith(Rank.ADMIN))
		{
			player.setOp(true);
			player.addAttachment(Main.getPlugin(), "nocheatplus.admin", true);
			player.addAttachment(Main.getPlugin(), "nocheatplus.checks", true);
		}
	}
}
