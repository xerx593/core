package src.ares.core.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import src.ares.core.Main;
import src.ares.core.client.Client;
import src.ares.core.client.OfflineClient;
import src.ares.core.common.Module;
import src.ares.core.currency.CurrencyType;
import src.ares.core.currency.type.AmbrosiaCurrency;
import src.ares.core.currency.type.GoldCurrency;
import src.ares.core.server.data.ServerStorage;

public class ServerDatabase extends Module
{
	private static ServerDatabase instance = new ServerDatabase();

	public static ServerDatabase getInstance()
	{
		return instance;
	}

	private List<TimeCounter> playTimers = new ArrayList<>();
	private static Connection conn;
	private ServerStorage storage = ServerStorage.getInstance();
	private int port = 3306;
	private String host = storage.getDatabaseURL();
	private String user = storage.getDatabaseUser();
	private String password = storage.getDatabasePassword();
	private String database = storage.getDatabaseName();

	public ServerDatabase()
	{
		super("Server Database");
	}

	public void createDatabase()
	{
		try
		{
			queryUpdate("CREATE TABLE IF NOT EXISTS player_data (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100), uuid VARCHAR(100), rank VARCHAR(100), second_rank VARCHAR(100), gold VARCHAR(100), ambrosia VARCHAR(100), time VARCHAR(100))");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public synchronized void openConnection()
	{
		try
		{
			conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, user, password);
		}
		catch (Exception e)
		{
			Main.log("Database Error: Invalid Credentials", Level.WARNING);
			Main.log("Make sure to properly configure server.yml properties.", Level.WARNING);
		}
	}

	public void closeConnection()
	{
		try
		{
			conn.close();
		}
		catch (Exception e)
		{
			Main.log("Database Error: Could not close database connection.", Level.SEVERE);
			Main.log("Connection may have not initialized properly.", Level.SEVERE);
		}
	}

	public void closeResources(ResultSet rs, PreparedStatement st)
	{
		if (rs != null)
		{
			try
			{
				rs.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}

		if (st != null)
		{
			try
			{
				st.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	@EventHandler
	public void retrievePlayerData(PlayerLoginEvent e)
	{
		Player player = e.getPlayer();
		Client client = new Client(e.getPlayer());
		String name = client.getManager().getUsername();
		String uuid = e.getPlayer().getUniqueId().toString();
		String rank = client.getManager().getRank().getName();

		new TimeCounter(player);

		try
		{
			if (!playerDataContainsPlayer(player))
			{
				queryUpdate("INSERT INTO player_data (uuid, name, rank, gold, ambrosia) VALUES ( '" + uuid + "', '" + name + "', '" + rank + "', 0 ,0)");
			}
			else
			{
				retrieve(player);
			}
		}
		catch (Exception ex)
		{
			Main.log("Database Error: " + ex.getMessage(), Level.SEVERE);
		}
	}

	@EventHandler
	public void updatePlayerData(PlayerQuitEvent e)
	{
		Player player = e.getPlayer();

		for (TimeCounter timeCounter : playTimers)
		{
			if (timeCounter.getPlayer() == player)
			{
				timeCounter.updateData();
				playTimers.remove(timeCounter);
				break;
			}
		}

		try
		{
			if (playerDataContainsPlayer(player))
			{
				update(player);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public synchronized boolean playerDataContainsPlayer(Player player)
	{
		//openConnection();

		try
		{
			PreparedStatement sql = conn.prepareStatement("SELECT * FROM `player_data` WHERE uuid=?;");
			sql.setString(1, player.getUniqueId().toString());

			ResultSet resultSet = sql.executeQuery();
			boolean containsPlayer = resultSet.next();

			sql.close();
			resultSet.close();

			return containsPlayer;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
			//closeConnection();
		}
	}

	public void queryUpdate(String query)
	{
		//openConnection();

		PreparedStatement st = null;

		try
		{
			st = conn.prepareStatement(query);
			st.executeUpdate();
		}
		catch (SQLException e)
		{
			System.out.println("Failed to send update " + query + ".");
			e.printStackTrace();
		}
		finally
		{
			closeResources(null, st);
			// closeConnection();
		}
	}

	/**
	 * Updates the player database tables, retrieves data from the configuration files.
	 * 
	 * @param player The player to update.
	 */
	public void retrieve(OfflinePlayer player)
	{
		// openConnection();

		OfflineClient client = new OfflineClient(player.getName());
		ResultSet rs = null;
		PreparedStatement st = null;

		try
		{
			st = conn.prepareStatement("SELECT * FROM player_data WHERE uuid=?");
			st.setString(1, player.getUniqueId().toString());

			rs = st.executeQuery();
			rs.last();

			if (rs.getRow() != 0)
			{
				rs.first();
				// String rank = rs.getString("rank");
				int gold = rs.getInt("gold");
				int ambrosia = rs.getInt("ambrosia");

				// client.getManager().setRank(Rank.valueOf(UtilString.enumerator(rank)));
				client.getManager().updateCurrency(new GoldCurrency(gold), false);
				client.getManager().updateCurrency(new AmbrosiaCurrency(ambrosia), false);
			}

			st.close();
			rs.close();
		}
		catch (SQLException e)
		{
			Main.log("Could not update credentials for " + player.getName() + ".", Level.SEVERE);
		}
		finally
		{
			closeResources(rs, st);
			// closeConnection();
		}
	}

	/**
	 * Updates the player configuration files, retrieves data from the database.
	 * 
	 * @param player The player to update.
	 */
	public void update(OfflinePlayer player)
	{
		//openConnection();

		OfflineClient client = new OfflineClient(player.getName());
		String uuid = player.getUniqueId().toString();
		String rank = client.getManager().getRank().getName();
		String secondRank = client.getManager().getSecondRank().getName();
		int gold = client.getManager().getCurrency(CurrencyType.GOLD);
		int ambrosia = client.getManager().getCurrency(CurrencyType.AMBROSIA);

		try
		{
			queryUpdate("UPDATE player_data SET rank='" + rank + "' WHERE uuid='" + uuid + "'");
			queryUpdate("UPDATE player_data SET second_rank='" + secondRank + "' WHERE uuid='" + uuid + "'");
			queryUpdate("UPDATE player_data SET gold='" + gold + "' WHERE uuid='" + uuid + "'");
			queryUpdate("UPDATE player_data SET ambrosia='" + ambrosia + "' WHERE uuid='" + uuid + "'");
		}
		catch (Exception e)
		{
			Main.log("Could not update credentials for " + player.getName() + ".", Level.SEVERE);
		}
		finally
		{
			//closeConnection();
		}
	}

	public List<TimeCounter> getPlayTimers()
	{
		return playTimers;
	}

	public long getPlayerTime(UUID uuid)
	{
		// openConnection();

		ResultSet rs = null;
		PreparedStatement st = null;
		long time = 0;

		try
		{
			st = conn.prepareStatement("SELECT * FROM player_data WHERE uuid=?");
			st.setString(1, uuid.toString());
			rs = st.executeQuery();
			rs.last();

			if (rs.getRow() != 0)
			{
				rs.first();
				time = rs.getLong("time");
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeResources(rs, st);
			// closeConnection();
		}

		return time;
	}
}
