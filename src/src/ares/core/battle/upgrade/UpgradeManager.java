package src.ares.core.battle.upgrade;

import java.util.ArrayList;

import src.ares.core.Main;

public class UpgradeManager
{
	private static UpgradeManager instance = new UpgradeManager();

	public static UpgradeManager getInstance()
	{
		return instance;
	}

	private ArrayList<Upgrade> upgradeBag;

	public UpgradeManager()
	{

	}

	public void createUpgrades()
	{
		upgradeBag = new ArrayList<>();

		// upgradeBag.add(new ArmorTypeUpgrade());
		// upgradeBag.add(new UpgradeArmorProtection());
		// upgradeBag.add(new UpgradeArmorFireProtection());
		// upgradeBag.add(new UpgradeArmorProjectileProtection());
		// upgradeBag.add(new ArmorThornsEnchantment());
		// upgradeBag.add(new UpgradeMysteriousOrb());

		for (Upgrade upgrade : upgradeBag)
		{
			Main.debug("Creating " + upgrade.getName() + ".");
		}
	}

	public Upgrade getUpgrade(String name)
	{
		for (Upgrade upgrade : upgradeBag)
		{
			if (upgrade.getName().contains(name))
				return upgrade;
		}

		return null;
	}

	public ArrayList<Upgrade> getUpgrades()
	{
		return upgradeBag;
	}
}
