package src.ares.core.panel;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import src.ares.core.battle.BattleManager;
import src.ares.core.battle.kit.Kit;
import src.ares.core.client.Client;
import src.ares.core.client.Rank;
import src.ares.core.client.storage.ClientManager;
import src.ares.core.currency.CurrencyType;

public class PlayerPanel extends Panel
{
	private ChatColor C;

	private Client client;
	private ClientManager storage;
	private int gold;
	private int ambrosia;
	private int count;
	private String world;
	private String rank;
	private String secondRank;

	public PlayerPanel(Player player)
	{
		super(player, "   Ares Network   ", "Player Data");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void update()
	{
		this.client = new Client(getPlayer());
		this.storage = client.getManager();
		this.gold = storage.getCurrency(CurrencyType.GOLD);
		this.ambrosia = storage.getCurrency(CurrencyType.AMBROSIA);
		this.count = Bukkit.getOnlinePlayers().length;
		this.world = client.getWorld().getName();
		this.rank = storage.getRank().getName();
		this.secondRank = storage.getSecondRank().getName();

		addEntry("");

		addGold();
		addEntry(" ");

		addAmbrosia();
		addEntry("  ");

		addWorld();
		addEntry("   ");

		addRank();
		addEntry("    ");

		addOnlineNumber();
	}

	private void addAmbrosia()
	{
		addEntry(C.DARK_PURPLE + "" + C.BOLD + CurrencyType.AMBROSIA.toString());

		if (ambrosia > 0)
			addEntry("§7§f" + Integer.toString(ambrosia));
		else
			addEntry("§7§f" + "None");
	}

	private void addGold()
	{
		addEntry(C.GOLD + "" + C.BOLD + CurrencyType.GOLD.toString());

		if (gold > 0)
			addEntry("§6§f" + Integer.toString(gold));
		else
			addEntry("§6§f" + "None");
	}

	private void addKitSelection()
	{
		Kit kitPreference = BattleManager.getInstance().getKitPreference(getPlayer());

		if (kitPreference != null)
		{
			addEntry(C.RED + "" + C.BOLD + "Battle Kit");
			addEntry(C.WHITE + BattleManager.getInstance().getKitPreference(getPlayer()).getName());
		}
	}

	private void addOnlineNumber()
	{
		addEntry(C.GRAY + "" + count + "/" + Bukkit.getMaxPlayers() + " online.");
	}

	private void addWebsite()
	{
		addEntry(C.GRAY + "ares-network.net");
	}

	private void addRank()
	{
		addEntry(C.GREEN + "" + C.BOLD + "Rank");
		addEntry(C.WHITE + rank);

		if (secondRank != Rank.PLAYER.getName())
		{
			addEntry(C.WHITE + secondRank);
		}
	}

	private void addWorld()
	{
		addEntry(C.DARK_GREEN + "" + C.BOLD + "World");
		addEntry(C.WHITE + world);
	}

	@SuppressWarnings("deprecation")
	public void assignPrefix()
	{
		for (Rank rank : Rank.values())
		{
			Team team = getScoreboard().registerNewTeam(rank.getName());

			if (getScoreboard().getTeam(rank.getName()).getName().contains(Rank.PLAYER.getName()))
			{
				team.setPrefix("");
				team.setDisplayName("");
				continue;
			}

			team.setPrefix(rank.getColor() + "");
			team.setDisplayName(rank.getColor() + "");
		}

		Client joined = new Client(getPlayer());

		for (Player players : Bukkit.getOnlinePlayers())
		{
			Client client = new Client(players);
			Rank rank = client.getManager().getRank();

			getScoreboard().getTeam(rank.getName()).addPlayer(players);
			players.getScoreboard().getTeam(joined.getManager().getRank().getName()).addPlayer(getPlayer());
		}
	}
}
