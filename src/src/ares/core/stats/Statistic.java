package src.ares.core.stats;

import src.ares.core.common.Module;

public class Statistic extends Module
{
	private String title;

	/**
	 * Default Constructor
	 * 
	 * @param title The title of the statistic.
	 */
	public Statistic(String title)
	{
		super("Statistic");

		this.title = title;
		registerEvents();
	}

	public String getTitle()
	{
		return title;
	}
}
