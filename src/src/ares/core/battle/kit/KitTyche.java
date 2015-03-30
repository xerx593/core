package src.ares.core.battle.kit;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import src.ares.core.battle.ability.AbilityRandomLuck;
import src.ares.core.common.item.CraftedItemStack;

public class KitTyche extends Kit
{
	private static ChatColor C;

	public KitTyche()
	{
		super("Tyche Kit", new String[] { C.WHITE + "Some people never believed his luck", C.WHITE + "because they were afraid." }, 9_000, C.AQUA, Color.AQUA);

		addAbility(new AbilityRandomLuck(this));
	}

	@Override
	public void addEffects(Player player)
	{
		//
	}

	@Override
	public void addItems()
	{
		CraftedItemStack ironSword = new CraftedItemStack(Material.IRON_SWORD, "Lucky Sword");
		ironSword.setUnbreakable(true);
		addItemStack(ironSword.pack());

		CraftedItemStack luckySpin = new CraftedItemStack(Material.NETHER_STAR, "Roll Dice");
		addItemStack(luckySpin.pack());

		addSoups(3);
	}
}
