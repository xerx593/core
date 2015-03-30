package src.ares.core.common.util;

import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class UtilFirework
{
	private static Random r = new Random();

	private static Color getColor(int i)
	{
		Color c = null;
		if (i == 1)
		{
			c = Color.AQUA;
		}
		if (i == 2)
		{
			c = Color.BLACK;
		}
		if (i == 3)
		{
			c = Color.BLUE;
		}
		if (i == 4)
		{
			c = Color.FUCHSIA;
		}
		if (i == 5)
		{
			c = Color.GRAY;
		}
		if (i == 6)
		{
			c = Color.GREEN;
		}
		if (i == 7)
		{
			c = Color.LIME;
		}
		if (i == 8)
		{
			c = Color.MAROON;
		}
		if (i == 9)
		{
			c = Color.NAVY;
		}
		if (i == 10)
		{
			c = Color.OLIVE;
		}
		if (i == 11)
		{
			c = Color.ORANGE;
		}
		if (i == 12)
		{
			c = Color.PURPLE;
		}
		if (i == 13)
		{
			c = Color.RED;
		}
		if (i == 14)
		{
			c = Color.SILVER;
		}
		if (i == 15)
		{
			c = Color.TEAL;
		}
		if (i == 16)
		{
			c = Color.WHITE;
		}
		if (i == 17)
		{
			c = Color.YELLOW;
		}

		return c;
	}

	public static Firework random(World world, Location location)
	{
		int rp = r.nextInt(2) + 1;
		return random(world, location, rp);
	}

	public static Firework random(World world, Location location, int power)
	{
		Firework firework = world.spawn(location.add(0, 1, 0), Firework.class);
		FireworkMeta fireworkMeta = firework.getFireworkMeta();

		int rt = r.nextInt(4) + 1;
		Type type = Type.BALL;

		if (rt == 1)
			type = Type.BALL;
		if (rt == 2)
			type = Type.BALL_LARGE;
		if (rt == 3)
			type = Type.BURST;
		if (rt == 4)
			type = Type.CREEPER;
		if (rt == 5)
			type = Type.STAR;

		int r1i = r.nextInt(17) + 1;
		int r2i = r.nextInt(17) + 1;
		Color c1 = getColor(r1i);
		Color c2 = getColor(r2i);

		FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();

		fireworkMeta.addEffect(effect);
		fireworkMeta.setPower(power);
		firework.setFireworkMeta(fireworkMeta);

		return firework;
	}
}
