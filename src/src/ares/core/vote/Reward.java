package src.ares.core.vote;

import java.util.Random;

import src.ares.core.battle.BattleManager;
import src.ares.core.battle.kit.Kit;
import src.ares.core.client.Client;
import src.ares.core.client.OfflineClient;
import src.ares.core.client.storage.ClientConst;
import src.ares.core.client.storage.ClientManager;
import src.ares.core.currency.type.AmbrosiaCurrency;
import src.ares.core.currency.type.GoldCurrency;

public class Reward
{
	private OfflineClient offlineClient;
	private Client onlineClient;
	private ClientManager manager;
	private Random random = new Random();

	public Reward(OfflineClient client)
	{
		this.offlineClient = client;
		this.manager = client.getManager();
	}

	public Reward(Client client)
	{
		this.onlineClient = client;
		this.manager = client.getManager();
	}

	public int goldReward(int min, int max)
	{
		int range = random.nextInt(min) + max;

		if (onlineClient != null)
		{
			onlineClient.addCurrency(new GoldCurrency(range), true);
		}
		else
		{
			offlineClient.addCurrency(new GoldCurrency(range));
		}

		return range;
	}

	public int ambrosiaReward(int min, int max)
	{
		int range = random.nextInt(min) + max;

		if (onlineClient != null)
		{
			onlineClient.addCurrency(new AmbrosiaCurrency(range), true);
		}
		else
		{
			offlineClient.addCurrency(new AmbrosiaCurrency(range));
		}

		return range;
	}

	public void kitReward()
	{
		for (Kit kit : BattleManager.getInstance().getKitBag())
		{
			if (manager.isKitOwned(kit) && manager.getKitLevel(kit) < ClientConst.MAX_KIT_LEVEL)
			{
				manager.upgradeKitLevel(kit);
				return;
			}
		}
	}

	public void randomReward()
	{
		int dice = random.nextInt(3);

		if (dice == 0)
		{
			goldReward(500, 800);
		}
		else if (dice == 1)
		{
			ambrosiaReward(700, 900);
		}
		else
		{
			kitReward();
		}
	}
}
