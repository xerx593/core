package src.ares.core.panel;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import src.ares.core.Main;
import src.ares.core.common.Module;

public abstract class Panel extends Module
{
	private String name;
	private Scoreboard scoreboard;
	private Objective objective;
	private Player player;
	private final int lines;
	private int scoreboardLines;

	private ArrayList<Score> scores;

	public Panel(final Player player, String name, String objectiveName)
	{
		super("Panel");

		this.name = name;
		this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		this.objective = scoreboard.registerNewObjective(objectiveName, "dummy");
		this.player = player;
		this.lines = 30;
		this.scores = new ArrayList<>();

		assignAndClearScoreboard();
		positionAndDisplayObjective();

		BukkitRunnable updater = new BukkitRunnable()
		{
			@Override
			public void run()
			{
				if (player == null)
				{
					cancel();
				}

				if (player.getScoreboard().equals(scoreboard))
				{
					scoreboardLines = lines;
					resetScoreboardScores();
					update();
				}
			}
		};

		updater.runTaskTimer(Main.getPlugin(), 0L, 20 * 2L);
	}

	/**
	 * Adds a new Score to the player scoreboard objective.
	 * 
	 * @param display The display name of the Score.
	 */
	protected void addEntry(String display)
	{
		Score score = objective.getScore(display);
		objective.getScore(display).setScore(scoreboardLines--);
		scores.add(score);
	}

	/**
	 * Clearing the scoreboard of the player, and assigning the new one.
	 */
	private void assignAndClearScoreboard()
	{
		player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		player.setScoreboard(scoreboard);
	}

	public String getName()
	{
		return name;
	}

	public Objective getObjective()
	{
		return objective;
	}

	public Player getPlayer()
	{
		return player;
	}

	public Scoreboard getScoreboard()
	{
		return scoreboard;
	}

	public ArrayList<Score> getScores()
	{
		return scores;
	}

	/**
	 * Positioning the scoreboard from the player at the side of the screen.
	 */
	private void positionAndDisplayObjective()
	{
		objective.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + name);
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	}

	private void resetScoreboardScores()
	{
		for (Score score : getScores())
		{
			getPlayer().getScoreboard().resetScores(score.getEntry());
		}
	}

	public abstract void update();
}
