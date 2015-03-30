package src.ares.core.condition.type;

import org.bukkit.entity.Player;

import src.ares.core.condition.Condition;

public class FlyCondition extends Condition<Player>
{
	private static FlyCondition instance = new FlyCondition();

	public static FlyCondition getCondition()
	{
		return instance;
	}
}
