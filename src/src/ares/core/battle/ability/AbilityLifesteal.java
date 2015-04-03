package src.ares.core.battle.ability;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import src.ares.core.Main;
import src.ares.core.battle.kit.Kit;
import src.ares.core.client.Client;
import src.ares.core.common.util.Chat;

public class AbilityLifesteal extends Ability
{
	private static int DURATION = 5;

	public AbilityLifesteal(Kit kit)
	{
		super(kit, "Lifesteal");
	}

	@Override
	public String getTip(Client client)
	{
		int level = level(client.getPlayer());
		return "Melee weapon gains " + Chat.tool("Fire Aspect I") + " and your player " + Chat.tool("Regeneration II") + " for " + Chat.time((DURATION + level) + " seconds") + " on player kill.";
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event)
	{
		Player killer = event.getEntity().getKiller();

		if (validate(killer))
		{
			if (killer.getItemInHand().getItemMeta().getDisplayName().contains("Hades Soultaker"))
				useAbility(killer);
		}
	}

	@Override
	public void useAbility(final Player player)
	{
		int level = level(player);

		for (ItemStack item : player.getInventory().getContents())
		{
			if (item.getType() == Material.STONE_AXE)
			{
				item.addEnchantment(Enchantment.FIRE_ASPECT, level);
			}
		}

		player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * (DURATION + level), 1));

		getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable()
		{
			public void run()
			{
				if (validate(player))
				{
					for (ItemStack item : player.getInventory().getContents())
					{
						if (item.getType() == Material.STONE_AXE)
						{
							item.removeEnchantment(Enchantment.FIRE_ASPECT);
						}
					}
				}
			}
		}, 20 * (DURATION + level));
	}
}
