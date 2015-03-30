package src.ares.core.stats;

import java.util.ArrayList;

import src.ares.core.Main;
import src.ares.core.stats.type.TotalBowHits;
import src.ares.core.stats.type.TotalDeathsStatistic;
import src.ares.core.stats.type.TotalKillsStatistic;

public class StatsManager
{
	private static StatsManager instance = new StatsManager();

	public static StatsManager getInstance()
	{
		return instance;
	}

	private ArrayList<Statistic> statsBag;

	public void createStats()
	{
		this.statsBag = new ArrayList<>();

		statsBag.add(new TotalBowHits());
		statsBag.add(new TotalKillsStatistic());
		statsBag.add(new TotalDeathsStatistic());

		for (Statistic stat : statsBag)
		{
			Main.debug("Creating " + stat.getTitle() + ".");
		}
	}

	public ArrayList<Statistic> getStatisticBag()
	{
		return statsBag;
	}
}
