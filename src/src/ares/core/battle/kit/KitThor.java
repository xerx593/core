package src.ares.core.battle.kit;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import src.ares.core.battle.ability.AbilityEarthquake;
import src.ares.core.battle.ability.AbilityRevenge;
import src.ares.core.common.crafted.CraftedArmor;
import src.ares.core.common.crafted.CraftedItemStack;

public class KitThor extends Kit
{
	public KitThor()
	{
		super("Thor Kit", new String[]
		{
		ChatColor.WHITE + "No enemy will stand upon his", ChatColor.WHITE + "endless rage and skills."
		}, 8_500, ChatColor.GRAY, Color.SILVER);

		addAbility(new AbilityEarthquake(this));
		addAbility(new AbilityRevenge(this));
	}

	@Override
	public void addEffects(Player player)
	{
		player.getInventory().setHelmet(new CraftedItemStack(Material.IRON_HELMET, "Olympus Helmet").unbreakable(true).build());
		player.getInventory().setChestplate(new CraftedArmor(Material.LEATHER_CHESTPLATE, getColor(), "Olympus Chestplate").unbreakable(true).build());
		player.getInventory().setLeggings(new CraftedItemStack(Material.DIAMOND_LEGGINGS, "Olympus Leggings").unbreakable(true).build());
		player.getInventory().setBoots(new CraftedItemStack(Material.DIAMOND_BOOTS, "Olympus Boots").unbreakable(true).build());
	}

	@Override
	public void addItems()
	{
		CraftedItemStack hoe = new CraftedItemStack(Material.IRON_HOE, ChatColor.YELLOW + "Thor Hammer");
		hoe.unbreakable(true);
		addItemStack(hoe.build());

		addSoups(3);
	}

	@EventHandler
	public void thorHammerDamage(EntityDamageByEntityEvent event)
	{
		if (event.getDamager() instanceof Player)
		{
			Player attacker = (Player) event.getDamager();

			if (validate(attacker))
			{
				if (attacker.getItemInHand().getType() == Material.IRON_HOE)
				{
					event.setDamage(4.0);
				}
			}
		}
	}
}
