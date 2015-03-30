package src.ares.core.command.type.donor;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import src.ares.core.battle.listener.CombatLogListener;
import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;
import src.ares.core.common.util.Chat;
import src.ares.core.condition.type.BattleCondition;
import src.ares.core.condition.type.SpectateCondition;
import src.ares.core.world.CoreWorld;
import src.ares.core.world.WorldType;

public class CommandSpectate extends CoreCommand
{
	private SpectateCondition condition = SpectateCondition.getCondition();
	private BattleCondition battle = BattleCondition.getCondition();

	public CommandSpectate()
	{
		super("spectate", new String[] {}, 0, Rank.ELITE);
	}

	@Override
	public void execute()
	{
		if (getClient().isStaff())
		{
			getClient().sendMessage(getModuleName(), "Staff members can only use " + Chat.command("/vanish") + " command.");
			return;
		}

		if (battle.hasItem(getClient().getPlayer()))
		{
			CombatLogListener.getInstance().kick(getClient());
			return;
		}

		Player player = getClient().getPlayer();
		CoreWorld world = getClient().getCoreWorld();

		if (world.getWorldType() == WorldType.PVP)
		{
			if (condition.hasItem(player))
			{
				condition.removeSpectator(player);
			}
			else
			{
				condition.addSpectator(player);
			}
		}
		else
		{
			getClient().sendMessage(getModuleName(), ChatColor.RED + "You can only toggle that mode in PVP worlds.");
		}
	}
}
