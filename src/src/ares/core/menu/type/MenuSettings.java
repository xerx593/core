package src.ares.core.menu.type;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import src.ares.core.Main;
import src.ares.core.client.Client;
import src.ares.core.common.item.CraftedItemStack;
import src.ares.core.menu.Menu;
import src.ares.core.settings.Setting;
import src.ares.core.settings.SettingsManager;

public class MenuSettings extends Menu
{
	public MenuSettings()
	{
		super(Material.REDSTONE_TORCH_ON, "Settings", 36);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void InventoryClick(InventoryAction action, ItemStack item, Player player)
	{
		Client client = new Client(player);
		ItemMeta meta = item.getItemMeta();

		if (item.getType() == Material.WOOL)
		{
			for (Setting setting : SettingsManager.getInstance().getSettingBag())
			{
				for (String line : meta.getLore())
				{
					if (line.contains(setting.getName()))
					{
						if (item.getData().getData() == 5)
							client.getManager().modifySetting(setting, false);
						else if (item.getData().getData() == 14)
							client.getManager().modifySetting(setting, true);
						break;
					}
				}
			}
		}
		else
		{
			for (Setting setting : SettingsManager.getInstance().getSettingBag())
			{
				if (client.compareWith(setting.getRank()) || client.compareSecondWith(setting.getRank()))
				{
					if (meta.getDisplayName().contains(setting.getName()))
					{
						if (client.getManager().hasEnabledSetting(setting))
							client.getManager().modifySetting(setting, false);
						else
							client.getManager().modifySetting(setting, true);
					}
				}
			}
		}
	}

	@Override
	protected void InventoryConstruct(Player player)
	{
		Client client = new Client(player);

		int displays = 10;
		int statuses = 19;

		for (Setting setting : SettingsManager.getInstance().getSettingBag())
		{
			Main.debug("Adding " + setting.getName() + ".");
			AddDisplay(displays++, new CraftedItemStack(setting.getDisplay(), setting.getName()).pack());
			displays++;

			if (client.compareWith(setting.getRank()) || client.compareSecondWith(setting.getRank()))
			{
				if (client.getManager().hasEnabledSetting(setting))
				{
					AddDisplay(statuses++, setting.getTurnedOn());
					statuses++;
				}
				else
				{
					AddDisplay(statuses++, setting.getTurnedOff());
					statuses++;
				}
			}
			else
			{
				AddDisplay(statuses++, setting.getLocked());
				statuses++;
			}
		}
	}

	private void underDevelopment()
	{
		for (int i = 0; i < getSlots(); i++)
		{
			AddDisplay(i, new CraftedItemStack(Material.STAINED_GLASS_PANE, "Under Development").pack());
		}
	}
}
