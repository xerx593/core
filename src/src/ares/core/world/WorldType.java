package src.ares.core.world;

import src.ares.core.common.util.UtilString;

public enum WorldType
{
	HUB, PVP, SURVIVAL, CREATIVE, BUILD, OTHER;

	@Override
	public String toString()
	{
		return UtilString.format(this.name());
	}
}
