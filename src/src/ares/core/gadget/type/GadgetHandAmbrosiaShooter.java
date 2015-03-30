package src.ares.core.gadget.type;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import src.ares.core.client.Rank;
import src.ares.core.currency.type.AmbrosiaCurrency;
import src.ares.core.gadget.HandGadget;

public class GadgetHandAmbrosiaShooter extends HandGadget
{
	public GadgetHandAmbrosiaShooter()
	{
		super("Ambrosia Shooter Gadget", Material.IRON_BARDING, Rank.PLAYER, new AmbrosiaCurrency(5));
	}

	@Override
	public void useHandGadget(Player player)
	{
		Vector dropsite = player.getLocation().getDirection().normalize().multiply(1.3);
		Item item = player.getWorld().dropItem(player.getLocation().add(0, 1.5, 0), new AmbrosiaCurrency().getDisplay());
		item.setVelocity(dropsite);
		player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 1.5F);
	}
}
