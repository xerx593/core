package src.ares.core.battle.killstreak;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import src.ares.core.chat.Notification;
import src.ares.core.client.Client;
import src.ares.core.common.Module;
import src.ares.core.common.util.Chat;
import src.ares.core.world.WorldType;

public class KillstreakListener extends Module
{
	private static KillstreakListener instance = new KillstreakListener();

	public static KillstreakListener getInstance()
	{
		return instance;
	}

	private Killstreak killstreak = Killstreak.getInstance();
	private Notification notification = new Notification();

	public KillstreakListener()
	{
		super("Killstreak");
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void IncreaseKillstreak(PlayerDeathEvent event)
	{
		Player victim = event.getEntity();
		Player killer = victim.getKiller();

		if (killer == null)
			return;

		Client client = new Client(killer);

		if (client.getCoreWorld().getWorldType() != WorldType.PVP)
			return;
		if (client.getGameMode() == GameMode.CREATIVE)
			return;

		if (killstreak.getKillstreaks().containsKey(victim))
		{
			if (killstreak.getCurrent(victim) >= 10)
				notification.sendToPlayers(false, getModuleName(), Chat.player(killer.getName()) + " shut down " + Chat.player(victim.getName()) + " killstreak of " + Chat.killstreak(killstreak.getCurrent(victim) + "") + "!");
		}

		killstreak.reset(victim);
		int kills = killstreak.increase(killer);

		if (kills == 3 || kills == 5 || kills == 10 || kills == 15 || kills == 20 || kills == 30 || kills == 40 || kills == 50)
		{
			for (Player player : killer.getWorld().getPlayers())
			{
				player.playSound(player.getLocation(), Sound.CLICK, 1.0F, 1.5F);
			}

			notification.sendToPlayers(false, getModuleName(), Chat.player(killer.getName()) + " has a killstreak of " + Chat.killstreak(Integer.toString(kills)) + "!");
		}
	}

	@EventHandler
	public void RemoveKillstreak(PlayerQuitEvent event)
	{
		killstreak.remove(event.getPlayer());
	}
}
