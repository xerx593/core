package src.ares.core.battle.kit;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import src.ares.core.battle.ability.AbilityAmbrosia;
import src.ares.core.battle.ability.AbilityThunderstorm;
import src.ares.core.common.crafted.CraftedItemStack;

public class KitZeus extends Kit
{
	public KitZeus()
	{
		super("Zeus Kit", new String[]
		{
		ChatColor.WHITE + "One of the glorious Greek", ChatColor.WHITE + "Gods, skilled and powerful."
		}, 9000, ChatColor.WHITE, Color.BLACK);

		addAbility(new AbilityThunderstorm(this));
		addAbility(new AbilityAmbrosia(this));
	}

	@Override
	public void addEffects(Player player)
	{
		//
	}

	@Override
	public void addItems()
	{
		CraftedItemStack axe = new CraftedItemStack(Material.IRON_AXE, ChatColor.YELLOW + "Zeus Axe");
		axe.unbreakable(true);
		addItemStack(axe.build());

		addSoups(3);
	}
}
