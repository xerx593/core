package src.ares.core.battle.kit;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import src.ares.core.battle.ability.AbilityArrowEscape;
import src.ares.core.battle.ability.AbilityFireCharge;
import src.ares.core.common.crafted.CraftedEnchantment;
import src.ares.core.common.crafted.CraftedItemStack;

public class KitApollo extends Kit
{
	public KitApollo()
	{
		super("Apollo Kit", new String[]
		{
		ChatColor.WHITE + "He's faster than the speed of wind.", ChatColor.WHITE + "They say nobody ever saw him."
		}, 6_000, ChatColor.YELLOW, Color.YELLOW);

		addAbility(new AbilityArrowEscape(this));
		addAbility(new AbilityFireCharge(this));
	}

	@Override
	public void addEffects(Player player)
	{
		player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 1));
	}

	@Override
	public void addItems()
	{
		CraftedItemStack sword = new CraftedItemStack(Material.BLAZE_ROD, ChatColor.YELLOW + "Apollo Stick");
		addItemStack(sword.build());

		CraftedItemStack bow = new CraftedItemStack(Material.BOW);
		bow.unbreakable(true);
		bow.enchantment(new CraftedEnchantment(Enchantment.ARROW_INFINITE, 1, false));
		addItemStack(bow.build());

		CraftedItemStack arrow = new CraftedItemStack(Material.ARROW);
		addItemStack(arrow.build());

		addSoups(3);
	}

	@EventHandler
	public void apolloStickDamage(EntityDamageByEntityEvent event)
	{
		if (event.getDamager() instanceof Player && event.getEntity() instanceof Player)
		{
			Player attacker = (Player) event.getDamager();
			Player victim = (Player) event.getEntity();

			if (validate(attacker))
			{
				if (attacker.getItemInHand().getType() == Material.BLAZE_ROD)
				{
					attacker.getWorld().playSound(victim.getLocation(), Sound.GLASS, 0.5F, 1.3F);
					event.setDamage(4.0);
				}
			}
		}
	}
}
