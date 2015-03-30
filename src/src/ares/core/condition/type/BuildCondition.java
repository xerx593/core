package src.ares.core.condition.type;

import org.bukkit.entity.Player;

import src.ares.core.condition.Condition;

public class BuildCondition extends Condition<Player>
{
	private static BuildCondition instance = new BuildCondition();

	public static BuildCondition getCondition()
	{
		return instance;
	}
}
