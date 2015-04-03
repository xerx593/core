package src.ares.core.menu.type;

import src.ares.core.battle.kit.Kit;
import src.ares.core.client.Client;
import src.ares.core.client.storage.ClientManager;
import src.ares.core.currency.ICurrency;
import src.ares.core.currency.type.GoldCurrency;
import src.ares.core.menu.PurchaseMenu;

public class PurchaseMenuKitLevel extends PurchaseMenu
{
	private Kit kit;

	public PurchaseMenuKitLevel(Kit sellableKit, GoldCurrency currencyCost)
	{
		super("Purchase Kit Level", sellableKit.getDisplay(), currencyCost);

		this.kit = sellableKit;
	}

	@Override
	public void PurchaseProduct(ICurrency currency, Client client)
	{
		client.getManager().upgradeKitLevel(kit);
	}

	public boolean PrePaymentChecks(Client client)
	{
		ClientManager manager = client.getManager();

		if (manager.isKitOwned(kit))
		{
			if (manager.getKitLevel(kit) >= 3)
			{
				client.sendMessage(getModuleName(), "You have reached the maximum level.");
				return false;
			}
		}

		return true;
	}
}
