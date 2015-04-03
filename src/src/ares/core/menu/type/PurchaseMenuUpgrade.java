package src.ares.core.menu.type;

import src.ares.core.battle.upgrade.Upgrade;
import src.ares.core.client.Client;
import src.ares.core.currency.ICurrency;
import src.ares.core.currency.type.GoldCurrency;
import src.ares.core.menu.PurchaseMenu;

public class PurchaseMenuUpgrade extends PurchaseMenu
{
	private Upgrade upgrade;

	public PurchaseMenuUpgrade(Upgrade sellableUpgrade, GoldCurrency currencyCost)
	{
		super("Purchase Upgrade", sellableUpgrade.getDisplay(), currencyCost);

		this.upgrade = sellableUpgrade;
	}

	@Override
	public void PurchaseProduct(ICurrency currency, Client client)
	{
		int level = client.getManager().getUpgradeLevel(upgrade);

		if (level >= upgrade.getLevel())
		{
			client.sendMessage("Upgrade", "You have reached the maximum level.");
			return;
		}

		client.getManager().setUpgradeLevel(upgrade, level + 1);
	}

	public boolean PrePaymentChecks(Client client)
	{
		return true;
	}
}
