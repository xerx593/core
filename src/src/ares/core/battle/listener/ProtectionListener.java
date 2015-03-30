package src.ares.core.battle.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import src.ares.core.client.Client;
import src.ares.core.common.Module;
import src.ares.core.condition.type.BattleProtectionCondition;
import src.ares.core.world.WorldType;

public class ProtectionListener extends Module
{
	private static ProtectionListener instance = new ProtectionListener();

	public static ProtectionListener getInstance()
	{
		return instance;
	}
	
	public ProtectionListener()
	{
		super("Protection");
	}

	private BattleProtectionCondition protectionCondition = BattleProtectionCondition.getCondition();

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event)
	{
		if (event.getEntity() instanceof Player)
		{
			Player victim = (Player) event.getEntity();
			Client cVictim = new Client(victim);

			if (cVictim.getCoreWorld().getWorldType() != WorldType.PVP)
				return;

			if (protectionCondition.hasItem(victim))
			{
				event.setCancelled(true);
			}

			if (event.getDamager() instanceof Player)
			{
				Player attacker = (Player) event.getDamager();

				if (protectionCondition.hasItem(victim) || protectionCondition.hasItem(attacker))
				{
					event.setCancelled(true);

					Client client = new Client(victim);
					client.playLocationSound(BattleConst.PROTECTION_SOUND, 1.0F, 0.8F);
					client.unload();
				}
			}
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();

		if (protectionCondition.hasItem(player))
		{
			protectionCondition.removeItem(player);
		}
	}
}
