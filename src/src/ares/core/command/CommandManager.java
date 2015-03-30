package src.ares.core.command;

import java.util.ArrayList;

import src.ares.core.Main;
import src.ares.core.command.type.currency.CommandAmbrosia;
import src.ares.core.command.type.currency.CommandAmbrosiaAdd;
import src.ares.core.command.type.currency.CommandAmbrosiaRemove;
import src.ares.core.command.type.currency.CommandAmbrosiaSet;
import src.ares.core.command.type.currency.CommandGold;
import src.ares.core.command.type.currency.CommandGoldAdd;
import src.ares.core.command.type.currency.CommandGoldRemove;
import src.ares.core.command.type.currency.CommandGoldSet;
import src.ares.core.command.type.donor.CommandSpectate;
import src.ares.core.command.type.general.CommandAway;
import src.ares.core.command.type.general.CommandBroadcast;
import src.ares.core.command.type.general.CommandBuildMode;
import src.ares.core.command.type.general.CommandHead;
import src.ares.core.command.type.general.CommandHub;
import src.ares.core.command.type.general.CommandMOTD;
import src.ares.core.command.type.general.CommandMessage;
import src.ares.core.command.type.general.CommandMessageReply;
import src.ares.core.command.type.general.CommandPing;
import src.ares.core.command.type.punishment.CommandBan;
import src.ares.core.command.type.punishment.CommandFreeze;
import src.ares.core.command.type.punishment.CommandKick;
import src.ares.core.command.type.punishment.CommandLookup;
import src.ares.core.command.type.punishment.CommandMute;
import src.ares.core.command.type.punishment.CommandPunish;
import src.ares.core.command.type.punishment.CommandUnban;
import src.ares.core.command.type.punishment.CommandUnmute;
import src.ares.core.command.type.punishment.CommandWarn;
import src.ares.core.command.type.server.CommandConnect;
import src.ares.core.command.type.server.CommandPurchase;
import src.ares.core.command.type.server.CommandSetMode;
import src.ares.core.command.type.server.CommandSetVersion;
import src.ares.core.command.type.staff.CommandAnnounce;
import src.ares.core.command.type.staff.CommandEntities;
import src.ares.core.command.type.staff.CommandEvent;
import src.ares.core.command.type.staff.CommandFake;
import src.ares.core.command.type.staff.CommandFly;
import src.ares.core.command.type.staff.CommandForcefield;
import src.ares.core.command.type.staff.CommandGameMode;
import src.ares.core.command.type.staff.CommandKickAll;
import src.ares.core.command.type.staff.CommandRank;
import src.ares.core.command.type.staff.CommandRankSecond;
import src.ares.core.command.type.staff.CommandReward;
import src.ares.core.command.type.staff.CommandUUID;
import src.ares.core.command.type.staff.CommandVanish;
import src.ares.core.command.type.teleport.CommandPos;
import src.ares.core.command.type.teleport.CommandSetSpawn;
import src.ares.core.command.type.teleport.CommandTeleport;
import src.ares.core.command.type.teleport.CommandTeleportHere;
import src.ares.core.command.type.teleport.CommandWorld;

public class CommandManager
{
	private static CommandManager instance = new CommandManager();

	public static CommandManager getInstance()
	{
		return instance;
	}

	private ArrayList<CoreCommand> commandBag;

	public void createCommands()
	{
		commandBag = new ArrayList<>();

		commandBag.add(new CommandEntities());
		commandBag.add(new CommandReward());
		commandBag.add(new CommandEvent());
		commandBag.add(new CommandPurchase());
		commandBag.add(new CommandSpectate());
		commandBag.add(new CommandFake());
		commandBag.add(new CommandLookup());
		commandBag.add(new CommandBroadcast());
		commandBag.add(new CommandUUID());
		commandBag.add(new CommandPing());
		commandBag.add(new CommandAnnounce());
		commandBag.add(new CommandAway());
		commandBag.add(new CommandMessage());
		commandBag.add(new CommandMessageReply());
		commandBag.add(new CommandMOTD());
		commandBag.add(new CommandPos());
		commandBag.add(new CommandPunish());
		commandBag.add(new CommandRank());
		commandBag.add(new CommandRankSecond());
		commandBag.add(new CommandSetSpawn());
		commandBag.add(new CommandTeleport());
		commandBag.add(new CommandTeleportHere());
		commandBag.add(new CommandTeleportHere());
		commandBag.add(new CommandVanish());
		commandBag.add(new CommandWorld());
		commandBag.add(new CommandGameMode());
		commandBag.add(new CommandFly());
		commandBag.add(new CommandForcefield());
		commandBag.add(new CommandBuildMode());
		commandBag.add(new CommandHub());
		commandBag.add(new CommandHead());
		commandBag.add(new CommandSetVersion());
		commandBag.add(new CommandSetMode());
		commandBag.add(new CommandConnect());

		// Currency

		commandBag.add(new CommandAmbrosia());
		commandBag.add(new CommandAmbrosiaSet());
		commandBag.add(new CommandAmbrosiaAdd());
		commandBag.add(new CommandAmbrosiaRemove());

		commandBag.add(new CommandGold());
		commandBag.add(new CommandGoldSet());
		commandBag.add(new CommandGoldAdd());
		commandBag.add(new CommandGoldRemove());

		// Punishment & Management

		commandBag.add(new CommandKickAll());
		commandBag.add(new CommandBan());
		commandBag.add(new CommandKick());
		commandBag.add(new CommandUnban());
		commandBag.add(new CommandMute());
		commandBag.add(new CommandUnmute());
		commandBag.add(new CommandWarn());
		commandBag.add(new CommandFreeze());

		for (CoreCommand command : commandBag)
		{
			Main.debug("Creating /" + command.getName() + " Command.");
			command.registerCommand();
			command.registerEvents();
		}
	}

	public CoreCommand getCommand(String name)
	{
		for (CoreCommand command : commandBag)
		{
			if (command.getName().contains(name) || command.getName().startsWith(name))
				return command;
		}

		return null;
	}

	public ArrayList<CoreCommand> getCommandBag()
	{
		return commandBag;
	}
}
