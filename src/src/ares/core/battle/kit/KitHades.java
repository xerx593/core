package src.ares.core.battle.kit;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import src.ares.core.battle.ability.AbilityLifesteal;
import src.ares.core.battle.ability.AbilityMysteriousSpell;
import src.ares.core.common.crafted.CraftedEnchantment;
import src.ares.core.common.crafted.CraftedItemStack;

public class KitHades extends Kit
{
	public KitHades()
	{
		super("Hades Kit", new String[]
		{
		ChatColor.WHITE + "Came directly from hell", ChatColor.WHITE + "nobody knows what he's up to."
		}, 14_000, ChatColor.DARK_PURPLE, Color.PURPLE);

		addAbility(new AbilityMysteriousSpell(this));
		addAbility(new AbilityLifesteal(this));
	}

	@Override
	public void addEffects(Player player)
	{
		//
	}

	@Override
	public void addItems()
	{
		CraftedItemStack axe = new CraftedItemStack(Material.STONE_AXE, "Hades Soultaker");
		axe.unbreakable(true);
		addItemStack(axe.build());

		CraftedItemStack spell = new CraftedItemStack(Material.EYE_OF_ENDER, "Mysterious Eye");
		spell.enchantment(new CraftedEnchantment(Enchantment.DURABILITY, 0, false));
		addItemStack(spell.build());

		addSoups(2);
	}
}
