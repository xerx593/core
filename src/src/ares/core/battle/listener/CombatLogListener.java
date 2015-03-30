package src.ares.core.battle.listener;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import src.ares.core.Main;
import src.ares.core.client.Client;
import src.ares.core.client.OfflineClient;
import src.ares.core.common.Module;
import src.ares.core.condition.type.BattleCondition;
import src.ares.core.condition.type.BattleProtectionCondition;
import src.ares.core.punish.type.KickPunishment;
import src.ares.core.world.CoreWorld;
import src.ares.core.world.WorldManager;
import src.ares.core.world.WorldType;

public class CombatLogListener extends Module
{
	private static CombatLogListener instance = new CombatLogListener();

	public static CombatLogListener getInstance()
	{
		return instance;
	}

	private BattleCondition condition = BattleCondition.getCondition();
	private static BattleProtectionCondition protection = BattleProtectionCondition.getCondition();
	private WorldManager worldManager = WorldManager.getInstance();

	public CombatLogListener()
	{
		super("Battle");
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event)
	{
		if (event.getEntity() instanceof Player && event.getDamager() instanceof Player)
		{
			final Player victim = (Player) event.getEntity();
			final Player attacker = (Player) event.getDamager();

			CoreWorld world = WorldManager.getInstance().getWorld(attacker.getWorld().getName());

			if (world.getWorldType() != WorldType.PVP)
				return;
			if (attacker.getGameMode() == GameMode.CREATIVE || victim.getGameMode() == GameMode.CREATIVE)
				return;
			if (protection.hasItem(attacker) || protection.hasItem(victim))
				return;

			if (!condition.hasItem(attacker) || !condition.hasItem(victim))
			{
				condition.addItem(attacker);
				condition.addItem(victim);

				attacker.getInventory().setItem(BattleConst.FEATHER_SLOT, new ItemStack(Material.AIR));
				victim.getInventory().setItem(BattleConst.FEATHER_SLOT, new ItemStack(Material.AIR));

				getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable()
				{
					@Override
					public void run()
					{
						attacker.getInventory().setItem(BattleConst.FEATHER_SLOT, BattleConst.FEATHER);
						victim.getInventory().setItem(BattleConst.FEATHER_SLOT, BattleConst.FEATHER);

						condition.removeItem(attacker);
						condition.removeItem(victim);
					}
				}, 20 * BattleConst.COMBAT_TIME);
			}
		}
	}

	public void kick(Client client)
	{
		client.sendRaw(ChatColor.YELLOW + "" + ChatColor.BOLD + "Coward...!");
		client.getManager().setCombatLog(client.getManager().getCombatLog() + 1);
		int times = client.getManager().getCombatLog();

		new KickPunishment("Avoiding a PVP battle may result in a punishment.\nYou have combat logged " + times + " times.", new OfflineClient(client.getName()), null).apply();
	}

	@EventHandler
	public void onPlayerChangeWorld(PlayerChangedWorldEvent event)
	{
		Player player = event.getPlayer();
		Client client = new Client(player);
		CoreWorld previous = WorldManager.getInstance().getWorld(event.getFrom().getName());

		if (!player.isDead() && condition.hasItem(player) && previous.getWorldType() == WorldType.PVP)
		{
			kick(client);
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerDeath(PlayerDeathEvent event)
	{
		Player player = event.getEntity();
		World world = player.getWorld();
		CoreWorld coreWorld = worldManager.getWorld(world.getName());

		if (coreWorld.getWorldType() == WorldType.PVP)
		{
			condition.removeItem(player);
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		if (condition.hasItem(event.getPlayer()))
		{
			condition.removeItem(event.getPlayer());
		}
	}
}
