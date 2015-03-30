package src.ares.core.battle.listener;

import org.bukkit.entity.Player;

import src.ares.core.battle.BattleManager;
import src.ares.core.battle.kit.Kit;
import src.ares.core.client.Client;
import src.ares.core.common.util.Chat;
import src.ares.core.common.util.UtilPlayer;
import src.ares.core.condition.type.BattleProtectionCondition;
import src.ares.core.condition.type.SpectateCondition;
import src.ares.core.world.CoreWorld;
import src.ares.core.world.WorldType;

public class Protection implements Runnable
{
	private BattleProtectionCondition condition = BattleProtectionCondition.getCondition();
	private SpectateCondition spectate = SpectateCondition.getCondition();
	private Player player;
	private Client client;
	private Kit kit;

	public Protection(Player protPlayer)
	{
		player = protPlayer;
		client = new Client(player);
	}

	@Override
	public void run()
	{
		kit = BattleManager.getInstance().getKitPreference(player);
		condition.removeItem(player);
		CoreWorld world = client.getCoreWorld();

		if (!condition.hasItem(player) && !spectate.hasItem(player) && world.getWorldType() == WorldType.PVP)
		{
			UtilPlayer.reset(player);
			player.sendMessage(Chat.format(condition.getType(), "Your battle protection has ended."));
			kit.equip(player);
		}
	}
}
