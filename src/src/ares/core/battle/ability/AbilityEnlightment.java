package src.ares.core.battle.ability;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import src.ares.core.battle.kit.Kit;
import src.ares.core.client.Client;
import src.ares.core.common.cooldown.Cooldown;
import src.ares.core.common.util.Chat;

public class AbilityEnlightment extends HotkeyAbility
{
	private static int DURATION = 5;

	public AbilityEnlightment(Kit kit)
	{
		super(kit, "Enlightment", Material.IRON_SWORD);
	}

	@Override
	public String getTip(Client client)
	{
		int level = level(client.getPlayer());
		return "Activate with " + Chat.tool("Drop Key") + " to gain " + Chat.tool("Absorption I") + " for " + Chat.time((DURATION + level) + " seconds") + ".";
	}

	@EventHandler
	public void toggleAbility(PlayerInteractEvent event)
	{
		if (validate(event.getPlayer()))
		{
			Player player = event.getPlayer();

			if (player.getItemInHand().getType() != getHandItem())
				return;

			if (!hasToggledUse(player))
				return;

			if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
			{
				event.setCancelled(true);
				useAbility(player);
			}
		}
	}

	@Override
	public void useAbility(Player player)
	{
		Client client = new Client(player);
		int level = level(player);

		if (!Cooldown.create(player, 20, getName(), true))
		{
			client.playLocationSound(Sound.AMBIENCE_CAVE, 1.0F, 1.0F);
			client.playLocationSound(Sound.AMBIENCE_CAVE, 1.0F, 1.0F);
			client.playLocationSound(Sound.AMBIENCE_CAVE, 1.0F, 1.0F);
			client.unload();
			player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * (DURATION + level), 0));
		}
	}
}
