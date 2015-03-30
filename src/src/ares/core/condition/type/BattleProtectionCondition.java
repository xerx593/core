package src.ares.core.condition.type;

import org.bukkit.entity.Player;

import src.ares.core.condition.Condition;

public class BattleProtectionCondition extends Condition<Player>
{
	private static BattleProtectionCondition instance = new BattleProtectionCondition();

	public static BattleProtectionCondition getCondition()
	{
		return instance;
	}
}
