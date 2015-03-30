package src.ares.core.command.type.currency;

import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;
import src.ares.core.currency.CurrencyType;

public class CommandAmbrosia extends CoreCommand
{
	public CommandAmbrosia()
	{
		super("ambrosia", new String[] {}, 0, Rank.PLAYER);
	}

	@Override
	public void execute()
	{
		getClient().sendMessage(getModuleName(), "You have " + getClient().getManager().getCurrency(CurrencyType.AMBROSIA) + " " + CurrencyType.AMBROSIA.toString() + ".");
	}
}
