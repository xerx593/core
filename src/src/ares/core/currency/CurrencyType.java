package src.ares.core.currency;

import src.ares.core.common.util.UtilString;

public enum CurrencyType
{
	GOLD, AMBROSIA;

	@Override
	public String toString()
	{
		return UtilString.format(this.name());
	}
}
