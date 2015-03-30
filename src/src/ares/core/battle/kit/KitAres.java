package src.ares.core.battle.kit;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import src.ares.core.battle.ability.AbilityEnlightment;
import src.ares.core.battle.ability.AbilityForcefield;
import src.ares.core.common.item.CraftedItemStack;

public class KitAres extends Kit
{
	public KitAres()
	{
		super("Ares Kit", new String[] { ChatColor.WHITE + "The god amoung warriors,", ChatColor.WHITE + "never lost a swordfight." }, 0, ChatColor.GOLD, Color.ORANGE);

		addAbility(new AbilityEnlightment(this));
		addAbility(new AbilityForcefield(this));
	}

	@Override
	public void addEffects(Player player)
	{
		//
	}

	@Override
	public void addItems()
	{
		CraftedItemStack sword = new CraftedItemStack(Material.IRON_SWORD, "Ares Sword");
		sword.setUnbreakable(true);
		addItemStack(sword.pack());

		addSoups(3);
	}
}
