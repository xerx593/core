package src.ares.core.client.storage;

/**
 * Stores configuration entry name for consistency.
 */
public enum ClientSection
{
	USERNAME("Username"),
	RANK("Rank"),
	SECOND_RANK("Second-Ranks"),
	COMBAT_LOGGED("Combat-Logged"),
	KILLSTREAK("Killstreak"),
	BANNED("Banned"),
	MUTED("Muted"),
	KITS("Kits"),
	UPGRADES("Upgrades"),
	STATS("Stats"),
	SETTINGS("Settings"),
	HISTORY("History"),
	CHALLENGES("Challenges");

	private String name;

	/**
	 * Default Constructor
	 * 
	 * @param name The YAML entry inside the data file.
	 */
	ClientSection(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return name;
	}
}
