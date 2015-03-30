package src.ares.core.server;

import java.util.UUID;

import org.bukkit.entity.Player;

public class TimeCounter
{

	private Player player;
	private long joinTime;
	private UUID uuid;
	private ServerDatabase sql;

	public TimeCounter(Player player)
	{
		this.player = player;
		this.joinTime = System.currentTimeMillis();
		this.uuid = player.getUniqueId();
		this.sql = ServerDatabase.getInstance();

		sql.getPlayTimers().add(this);
	}

	public Player getPlayer()
	{
		return player;
	}

	public long getJoinTime()
	{
		return joinTime;
	}

	public long getTimeDifference()
	{
		return System.currentTimeMillis() - joinTime;
	}

	public void updateData()
	{
		long time = (sql.getPlayerTime(uuid) + getTimeDifference());
		sql.queryUpdate("UPDATE player_data SET time='" + time + "' WHERE uuid='" + this.uuid + "'");
		sql.queryUpdate("UPDATE player_data SET name='" + this.player.getName() + "' WHERE uuid='" + this.uuid + "'");
	}

}
