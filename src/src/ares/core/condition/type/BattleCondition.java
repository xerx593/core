package src.ares.core.condition.type;

import org.bukkit.entity.Player;

import src.ares.core.condition.Condition;

public class BattleCondition extends Condition<Player>
{
	private static BattleCondition instance = new BattleCondition();

	public static BattleCondition getCondition()
	{
		return instance;
	}
}
