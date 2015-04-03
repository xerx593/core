package src.ares.core.server.data;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;

import src.ares.core.Main;
import src.ares.core.common.ExceptionManager;
import src.ares.core.common.util.UtilString;

public class ServerStorage
{
	private static ServerStorage instance = new ServerStorage();

	public static ServerStorage getInstance()
	{
		return instance;
	}

	// The folder of the Core plugin. //
	private File folder = Main.getPlugin().getDataFolder();

	// The server configuration file location. //
	private File file = new File(folder, "server.yml");

	// The server configuration yml version. //
	private FileConfiguration config = YamlConfiguration.loadConfiguration(file);

	// The default plugin configuration from Bukkit. //
	private PluginDescriptionFile defaults = Main.getPlugin().getDescription();

	// The header of the server description. //
	private String header = getServerName() + " v." + getServerVersion() + "\n";

	// The color of the server description. //
	private ChatColor color = getServerMode().getColor();

	// The tagline of the server description. //
	private String description = getServerMode().getDescription();

	/**
	 * Adds default configuration keys to the server.yml server 
	 * 
	 * @throws IOException To be thrown if the file could not be saved.
	 */
	public void addConfigData()
	{
		// Server Properties

		config.addDefault("name", defaults.getName());
		config.addDefault("mode", ServerMode.NORMAL.toString());
		config.addDefault("version", defaults.getVersion());

		// Server Database

		config.addDefault("database.url", "None");
		config.addDefault("database.user", "None");
		config.addDefault("database.name", "None");
		config.addDefault("database.password", "None");

		config.options().copyDefaults(true);
		save();
	}

	/**
	 * Creates the server.yml file for the server 
	 */
	public void createFile()
	{
		try
		{
			if (!file.exists())
				file.createNewFile();
		}
		catch (Exception e)
		{
			ExceptionManager.handle(e);
		}
	}

	/**
	 * Gets the configuration of the server.yml file.
	 * 
	 * @return FileConfiguration
	 */
	public FileConfiguration getConfig()
	{
		return config;
	}

	public String getDatabaseName()
	{
		return config.getString("database.name");
	}

	public String getDatabasePassword()
	{
		return config.getString("database.password");
	}

	public String getDatabaseURL()
	{
		return config.getString("database.url");
	}

	public String getDatabaseUser()
	{
		return config.getString("database.user");
	}

	public String getDefaultMotd()
	{
		return header + color + "" + ChatColor.BOLD + description;
	}

	/**
	 * Gets the file version of the server.yml file.
	 * 
	 * @return
	 */
	public File getFile()
	{
		return file;
	}

	public String getMotdHeader()
	{
		return header;
	}

	/**
	 * Gets the server mode.
	 * 
	 * @return ServerMode
	 */
	public ServerMode getServerMode()
	{
		String modeValue = config.getString("mode");
		ServerMode mode = ServerMode.valueOf(UtilString.enumerator(modeValue));

		return mode;
	}

	/**
	 * Gets the <b>formatted</b> name of the server.
	 * 
	 * @return String
	 */
	public String getServerName()
	{
		return ChatColor.translateAlternateColorCodes('&', config.getString("name"));
	}

	/**
	 * Gets the server version.
	 * 
	 * @return String
	 */
	public String getServerVersion()
	{
		return ChatColor.translateAlternateColorCodes('&', config.getString("version"));
	}

	public boolean isChristmasMode()
	{
		return getServerMode() == ServerMode.CHRISTMAS;
	}

	public boolean isClosedMode()
	{
		return getServerMode() == ServerMode.CLOSED;
	}

	public boolean isDevelopmentMode()
	{
		return getServerMode() == ServerMode.DEVELOPMENT;
	}

	public boolean isEasterMode()
	{
		return getServerMode() == ServerMode.EASTER;
	}

	public boolean isHalloweenMode()
	{
		return getServerMode() == ServerMode.HALLOWEEN;
	}

	public boolean isMaintenanceMode()
	{
		return getServerMode() == ServerMode.MAINTENANCE;
	}

	public boolean isNormalMode()
	{
		return getServerMode() == ServerMode.NORMAL;
	}

	public boolean isSummerMode()
	{
		return getServerMode() == ServerMode.SUMMER;
	}
	
	public boolean isAprilFools()
	{
		return getServerMode() == ServerMode.APRIL_FOOLS;
	}

	public void save()
	{
		try
		{
			config.save(file);
		}
		catch (IOException e)
		{
			Main.error(getClass().getSimpleName(), e.getMessage());
		}
	}

	public void setServerMode(ServerMode mode)
	{
		config.set("mode", mode.toString());
		save();
	}

	public void setServerName(String name)
	{
		config.set("name", name);
		save();
	}

	public void setServerVersion(String version)
	{
		config.set("version", version);
		save();
	}
}
