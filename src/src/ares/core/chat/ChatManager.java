package src.ares.core.chat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import src.ares.core.Main;
import src.ares.core.client.Client;
import src.ares.core.client.FileManager;

public class ChatManager
{
	private static ChatManager instance = new ChatManager();

	public static ChatManager getInstance()
	{
		return instance;
	}

	FileManager manager = new FileManager(Main.getPlugin().getDataFolder(), "chat", "txt");

	public void logMessage(Client client, String message) throws IOException
	{
		String content = timestamp() + client.getManager().getRank().getName().toUpperCase() + " " + client.getName() + ": " + message + "\n";
		manager.writeContent(content);
	}

	public void logMessage(String raw) throws IOException
	{
		String content = timestamp() + raw;
		manager.writeContent(content);
	}

	private String timestamp()
	{
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/d/MM HH:mm:ss");

		return "[" + formatter.format(date) + "] ";
	}
}
