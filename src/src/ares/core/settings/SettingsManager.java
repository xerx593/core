package src.ares.core.settings;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

import src.ares.core.Main;
import src.ares.core.client.Rank;

public class SettingsManager
{
	private static SettingsManager instance = new SettingsManager();

	public static SettingsManager getInstance()
	{
		return instance;
	}

	private List<Setting> settingBag;

	public void createSettings()
	{
		settingBag = new ArrayList<>();
		settingBag.add(new Setting("Instant Respawn", Material.MINECART, Rank.PLAYER));
		settingBag.add(new Setting("Private Messages", Material.CHEST, Rank.PLAYER));
		settingBag.add(new Setting("Public Chat", Material.SIGN, Rank.PLAYER));
		settingBag.add(new Setting("Staff Chat", Material.EYE_OF_ENDER, Rank.TRIAL_MOD));

		for (Setting setting : settingBag)
		{
			Main.debug("Creating " + setting.getName() + " Setting");
		}
	}

	public List<Setting> getSettingBag()
	{
		return settingBag;
	}
}
