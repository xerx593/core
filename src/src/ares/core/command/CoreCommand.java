package src.ares.core.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import src.ares.core.Main;
import src.ares.core.client.Client;
import src.ares.core.client.Rank;
import src.ares.core.common.Module;
import src.ares.core.common.util.Chat;

/**
 * Represents a command wrapper to create commands efficiently.
 */
public abstract class CoreCommand extends Module implements CommandExecutor
{
	private CommandSender sender;
	private Client client;
	private String name;
	private String[] aliases;
	private String[] args;
	private int length;
	private Rank rank;
	private String usage;
	private boolean disabled;
	private long time;

	/**
	 * Default Constructor
	 * 
	 * @param name The command name.
	 * @param aliases The command aliases.
	 * @param args The command argument length.
	 * @param rank The required rank to run it.
	 */
	public CoreCommand(String name, String[] aliases, int length, Rank rank, String usage)
	{
		super("Command");
		this.name = name;
		this.aliases = aliases;
		this.length = length;
		this.rank = rank;
		this.usage = usage;
	}

	/**
	 * Alternative Constructor
	 * 
	 * @param name The name of the command.
	 * @param aliases The aliases of the command.
	 * @param length The command arguments.
	 * @param rank The rank required to run the command.
	 * @param console Can this command be ran in the console?
	 */
	public CoreCommand(String name, String[] aliases, int length, Rank rank)
	{
		this(name, aliases, length, rank, null);
	}

	/**
	 * Called when the command is executed.
	 */
	public abstract void execute();

	/**
	 * Called before the command is executed.
	 */
	protected void prepare()
	{};

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		time = System.currentTimeMillis();

		try
		{
			this.sender = sender;

			if (args.length < length)
			{
				if (this.usage == null)
					sender.sendMessage(Chat.format(getModuleName(), "Please provide enough arguments."));
				else
					sender.sendMessage(Chat.format("Usage", Chat.command("/" + this.name + " " + this.usage)));
				return true;
			}

			this.args = args;

			prepare();

			if (!(sender instanceof Player))
				return true;

			this.client = new Client((Player) sender);

			if (disabled)
			{
				client.sendMessage(getModuleName(), "That command is disabled.");
				return true;
			}

			if (client.compareWith(rank) || client.compareSecondWith(rank) || client.getPlayer().isOp())
			{
				if (label.equalsIgnoreCase(name.toLowerCase()))
				{
					Main.debug("Successfully called /" + name + " for " + client.getName() + ".");
					execute();
					return false;
				}
				else
				{
					for (String alias : aliases)
					{
						if (label.equalsIgnoreCase(alias))
						{
							execute();
							return false;
						}
					}
				}

				return true;
			}
			else
			{
				client.sendMessage(getModuleName(), "Only players with " + Chat.rank(rank.getName() + "+") + " can do that.");
				return false;
			}
		}
		catch (Exception e)
		{
			sender.sendMessage(Chat.format(getModuleName(), "There was a problem, please contact a staff member."));
			sender.sendMessage(ChatColor.RED + "> " + e.getLocalizedMessage());
			e.printStackTrace();
		}

		return false;
	}

	public void setDisabled(boolean disabled)
	{
		this.disabled = disabled;
	}

	public boolean isSenderPlayer()
	{
		return sender instanceof Player;
	}

	public long getHowLong()
	{
		return time;
	}

	public void registerCommand()
	{
		Bukkit.getPluginCommand(this.name).setExecutor(this);
	}

	public String[] getAliases()
	{
		return aliases;
	}

	public String[] getArgs()
	{
		return args;
	}

	public Client getClient()
	{
		return client;
	}

	public int getLength()
	{
		return length;
	}

	public String getName()
	{
		return name;
	}

	public Rank getRank()
	{
		return rank;
	}

	public String getUsage()
	{
		return usage;
	}
}
