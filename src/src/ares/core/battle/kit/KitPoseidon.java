package src.ares.core.battle.kit;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import src.ares.core.battle.ability.AbilityConfusion;
import src.ares.core.battle.ability.AbilityWaterDrift;
import src.ares.core.common.crafted.CraftedEnchantment;
import src.ares.core.common.crafted.CraftedItemStack;

public class KitPoseidon extends Kit
{
	public KitPoseidon()
	{
		super("Poseidon Kit", new String[]
		{
		ChatColor.WHITE + "Feel the power of the sea", ChatColor.WHITE + "and confuse your enemies."
		}, 7_000, ChatColor.BLUE, Color.BLUE);

		addAbility(new AbilityConfusion(this));
		addAbility(new AbilityWaterDrift(this));
	}

	@Override
	public void addEffects(Player player)
	{
		//
	}

	@Override
	public void addItems()
	{
		CraftedItemStack axe = new CraftedItemStack(Material.IRON_SPADE, ChatColor.YELLOW + "Poseidon Trident");
		axe.unbreakable(true);
		addItemStack(axe.build());

		CraftedItemStack bow = new CraftedItemStack(Material.BOW);
		bow.enchantment(new CraftedEnchantment(Enchantment.DAMAGE_ALL, 1, false));
		bow.unbreakable(true);
		addItemStack(bow.build());

		CraftedItemStack arrow = new CraftedItemStack(Material.ARROW);
		arrow.amount(30);
		addItemStack(arrow.build());

		addSoups(3);
	}
}
