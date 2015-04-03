package src.ares.core.battle.kit;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import src.ares.core.battle.ability.AbilityCloak;
import src.ares.core.battle.ability.AbilityLeap;
import src.ares.core.common.crafted.CraftedItemStack;

public class KitHypnos extends Kit
{
	public KitHypnos()
	{
		super("Hypnos Kit", new String[]
		{
		ChatColor.WHITE + "He always gets away easily", ChatColor.WHITE + "but you have to watch your back."
		}, 9_000, ChatColor.RED, Color.RED);

		addAbility(new AbilityLeap(this));
		addAbility(new AbilityCloak(this));
	}

	@Override
	public void addEffects(Player player)
	{
		//
	}

	@Override
	public void addItems()
	{
		CraftedItemStack axe = new CraftedItemStack(Material.STONE_AXE, ChatColor.YELLOW + "Hypnos Blade");
		axe.unbreakable(true);
		addItemStack(axe.build());

		CraftedItemStack cloak = new CraftedItemStack(Material.SULPHUR, ChatColor.WHITE + "Cloak");
		cloak.amount(3);
		addItemStack(cloak.build());

		addSoups(3);
	}
}
