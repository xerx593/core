package src.ares.core.command.type.currency;

import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;
import src.ares.core.currency.CurrencyType;
import src.ares.core.currency.type.GoldCurrency;

public class CommandGold extends CoreCommand
{
	public CommandGold()
	{
		super("gold", new String[] {}, 0, Rank.PLAYER, null);
	}

	@Override
	public void execute()
	{
		getClient().sendMessage(getModuleName(), "You have " + getClient().getManager().getCurrency(CurrencyType.GOLD) + " " + CurrencyType.GOLD.toString() + ".");
	}
}
