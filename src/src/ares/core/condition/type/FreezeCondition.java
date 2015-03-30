package src.ares.core.condition.type;

import org.bukkit.entity.Player;

import src.ares.core.condition.Condition;

public class FreezeCondition extends Condition<Player>
{
	private static FreezeCondition instance = new FreezeCondition();

	public static FreezeCondition getCondition()
	{
		return instance;
	}
}
