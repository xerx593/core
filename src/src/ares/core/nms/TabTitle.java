package src.ares.core.nms;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.EnumTitleAction;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PacketPlayOutChat;
import net.minecraft.server.v1_8_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R1.PlayerConnection;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class TabTitle
{
	private static boolean ClassListEqual(Class<?>[] l1, Class<?>[] l2)
	{
		boolean equal = true;
		if (l1.length != l2.length)
		{
			return false;
		}
		for (int i = 0; i < l1.length; i++)
		{
			if (l1[i] != l2[i])
			{
				equal = false;
				break;
			}
		}
		return equal;
	}

	public static void clear(Player p)
	{
		PacketPlayOutTitle packet = new PacketPlayOutTitle(EnumTitleAction.CLEAR, null);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}

	private static Field getField(Class<?> clazz, String name)
	{
		try
		{
			Field f = clazz.getDeclaredField(name);
			f.setAccessible(true);
			return f;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private static Object getHandle(Object obj)
	{
		try
		{
			return getMethod(obj.getClass(), "getHandle", new Class[0]).invoke(obj, new Object[0]);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private static Method getMethod(Class<?> clazz, String name, Class<?>... args)
	{
		for (Method m : clazz.getMethods())
		{
			if ((m.getName().equals(name)) && ((args.length == 0) || (ClassListEqual(args, m.getParameterTypes()))))
			{
				m.setAccessible(true);
				return m;
			}
		}
		return null;
	}

	public static void reset(Player p)
	{
		PacketPlayOutTitle packet = new PacketPlayOutTitle(EnumTitleAction.RESET, null);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}

	public static void sendActionBar(Player p, String message)
	{
		if (message == null)
		{
			message = "";
		}
		message = ChatColor.translateAlternateColorCodes('&', message);
		message = message.replaceAll("%PLAYER%", p.getDisplayName());

		PlayerConnection con = ((CraftPlayer) p).getHandle().playerConnection;

		IChatBaseComponent chat = ChatSerializer.a("{\"text\": \"" + message + "\"}");
		PacketPlayOutChat packet = new PacketPlayOutChat(chat, (byte) 2);

		con.sendPacket(packet);
	}

	public static void sendSubTitle(Player p, String subtitle)
	{
		IChatBaseComponent message = ChatSerializer.a("{\"text\": \"\"}").a(subtitle);
		PacketPlayOutTitle packet = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, message);

		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}

	public static void sendTablist(Player p, String header, String footer) // Here. This is all
	{
		if (header == null)
		{
			header = "";
		}
		if (footer == null)
		{
			footer = "";
		}
		header = ChatColor.translateAlternateColorCodes('&', header);
		footer = ChatColor.translateAlternateColorCodes('&', footer);

		header = header.replaceAll("%PLAYER%", p.getDisplayName());
		footer = footer.replaceAll("%PLAYER%", p.getDisplayName());

		PlayerConnection con = ((CraftPlayer) p).getHandle().playerConnection;

		IChatBaseComponent tabheader = ChatSerializer.a("{\"text\": \"" + header + "\"}");
		IChatBaseComponent tabfooter = ChatSerializer.a("{\"text\": \"" + footer + "\"}");

		PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter(tabheader);
		try
		{
			Field f = packet.getClass().getDeclaredField("b");
			f.setAccessible(true);
			f.set(packet, tabfooter);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.sendPacket(packet);
		}
	}

	public static void sendTiming(Player p, int fadeIn, int stay, int fadeOut)
	{
		try
		{
			Object h = getHandle(p);
			Object c = getField(h.getClass(), "playerConnection").get(h);

			Object packet = PacketPlayOutTitle.class.getConstructor(new Class[]
			{
			PacketPlayOutTitle.class, Integer.TYPE, Integer.TYPE, Integer.TYPE
			}).newInstance(new Object[]
			{
			EnumTitleAction.TIMES, Integer.valueOf(fadeIn), Integer.valueOf(stay), Integer.valueOf(fadeOut)
			});
			getMethod(h.getClass(), "sendPacket", new Class[0]).invoke(c, new Object[]
			{
				packet
			});
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void sendTitle(Player p, String title)
	{
		IChatBaseComponent message = ChatSerializer.a("{\"text\": \"\"}").a(title);
		PacketPlayOutTitle packet = new PacketPlayOutTitle(EnumTitleAction.TITLE, message);

		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}

	public Server SERVER = Bukkit.getServer();

	public ConsoleCommandSender CONSOLE = this.SERVER.getConsoleSender();
}
