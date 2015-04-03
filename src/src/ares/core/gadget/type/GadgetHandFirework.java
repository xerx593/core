package src.ares.core.gadget.type;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import src.ares.core.client.Rank;
import src.ares.core.common.util.UtilFirework;
import src.ares.core.currency.type.AmbrosiaCurrency;
import src.ares.core.gadget.HandGadget;

public class GadgetHandFirework extends HandGadget
{
	public GadgetHandFirework()
	{
		super("Firework Gadget", Material.FIREWORK, Rank.PLAYER, new AmbrosiaCurrency(5));
	}

	@SuppressWarnings("deprecation")
	@Override
	public void useHandGadget(Player player)
	{
		UtilFirework.random(player.getWorld(), player.getTargetBlock(null, 2).getLocation());
	}
}
