package src.ares.core.battle.ability;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import src.ares.core.common.Cooldown;
import src.ares.core.common.util.Chat;
import src.ares.core.common.util.UtilString;

public class AbilityRandomLuck extends HotkeyAbility
{
	private List<PotionEffectType> types;
	private Random randomPotion = new Random();
	private Random randomSeconds = new Random();
	private Random randomAmplifier = new Random();

	public AbilityRandomLuck(Kit kit)
	{
		super(kit, "Lucky", Material.NETHER_STAR);

		InitializePotions();
	}

	@Override
	public String getTip(Client client)
	{
		int level = level(client.getPlayer());
		return "Activate with " + Chat.tool("Drop Key") + ", press " + Chat.tool("Right-Click") + " with " + Chat.tool("Nether Star") + " to gain a potion effect. Duration limit is set to " + Chat.time((10 + level) + " seconds") + ".";
	}

	private void ChoosePotionEffect(Player player)
	{
		int level = level(player);
		int potion = randomPotion.nextInt(types.size());
		int seconds = randomSeconds.nextInt(10) + 1;
		int amplifier = randomAmplifier.nextInt(3);

		player.addPotionEffect(new PotionEffect(types.get(potion), 20 * (seconds + level), amplifier));
		player.sendMessage(Chat.format(getModuleName(), "You got " + Chat.tool(UtilString.format(types.get(potion).getName()) + " " + (amplifier + 1)) + " added for " + Chat.time(seconds + " seconds") + "."));
	}

	private void InitializePotions()
	{
		types = new ArrayList<>();
		types.add(PotionEffectType.ABSORPTION);
		types.add(PotionEffectType.DAMAGE_RESISTANCE);
		types.add(PotionEffectType.SLOW);
		types.add(PotionEffectType.INCREASE_DAMAGE);
		types.add(PotionEffectType.JUMP);
		types.add(PotionEffectType.SPEED);
		types.add(PotionEffectType.REGENERATION);
		types.add(PotionEffectType.FIRE_RESISTANCE);
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
		if (!Cooldown.create(player, 20, getName(), true))
		{
			ChoosePotionEffect(player);
			Client client = new Client(player);
			client.playLocationSound(Sound.CLICK, 0.5F, 1.5F);
			client.unload();
		}
	}
}
