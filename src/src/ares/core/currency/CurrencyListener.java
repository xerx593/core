package src.ares.core.currency;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import src.ares.core.client.Client;
import src.ares.core.common.Module;
import src.ares.core.condition.type.SpectateCondition;
import src.ares.core.currency.type.AmbrosiaCurrency;
import src.ares.core.currency.type.GoldCurrency;

public class CurrencyListener extends Module
{
	private static CurrencyListener instance = new CurrencyListener();

	public static CurrencyListener getInstance()
	{
		return instance;
	}

	public CurrencyListener()
	{
		super("Currency");
	}

	@EventHandler
	public void goldPickup(PlayerPickupItemEvent event)
	{
		Player player = event.getPlayer();
		Client client = new Client(player);

		ItemStack pickup = event.getItem().getItemStack();
		ItemMeta meta = pickup.getItemMeta();

		if (SpectateCondition.getCondition().hasItem(player))
		{
			event.setCancelled(true);
			return;
		}

		try
		{
			if (pickup != null && pickup.hasItemMeta())
			{
				for (CurrencyType type : CurrencyType.values())
				{
					if (meta.getDisplayName().contains(type.toString()))
					{
						event.setCancelled(true);
						event.getItem().remove();

						int amount = pickup.getAmount();
						client.addCurrency(findCurrencyByMaterial(pickup.getType(), amount), true);
						client.playLocationSound(Sound.DIG_GRASS, 1.0f, 1.3f);
					}
				}
			}
		}
		catch (Exception e)
		{
			;
		}

	}

	private ICurrency findCurrencyByName(String name, int amount)
	{
		if (name == CurrencyType.GOLD.toString())
			return new GoldCurrency(amount);
		else if (name == CurrencyType.AMBROSIA.toString())
			return new AmbrosiaCurrency(amount);
		else
			return null;
	}

	private ICurrency findCurrencyByMaterial(Material material, int amount)
	{
		if (material == CurrencyMaterial.GOLD)
			return new GoldCurrency(amount);
		else if (material == CurrencyMaterial.AMBROSIA)
			return new AmbrosiaCurrency(amount);
		else
			return null;
	}
}
