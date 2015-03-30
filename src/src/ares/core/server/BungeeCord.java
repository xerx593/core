package src.ares.core.server;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.entity.Player;

import src.ares.core.Main;
import src.ares.core.client.Client;

public class BungeeCord
{
	public static void send(Player player, String server)
	{
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		Client client = new Client(player);

		try
		{
			out.writeUTF("Connect");
			out.writeUTF(server);

			client.sendRaw("&c>>&7 Connecting with " + server + "...");
		}
		catch (IOException e)
		{
			client.sendRaw("&c>>&7 There was a problem, please try again later.");
			e.printStackTrace();
		}

		player.sendPluginMessage(Main.getPlugin(), "BungeeCord", b.toByteArray());
	}
}
