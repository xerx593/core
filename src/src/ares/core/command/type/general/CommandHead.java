package src.ares.core.command.type.general;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;
import src.ares.core.common.util.Chat;
import src.ares.core.condition.type.BuildCondition;

public class CommandHead extends CoreCommand
{
	public CommandHead()
	{
		super("head", new String[] {}, 1, Rank.BUILDER, "<player>");
	}

	@Override
	public void execute()
	{
		if (BuildCondition.getCondition().hasItem(getClient().getPlayer()))
		{
			ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
			SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
			skullMeta.setDisplayName(getArgs()[0]);
			skullMeta.setOwner(getArgs()[0]);
			skull.setItemMeta(skullMeta);

			getClient().getPlayer().getInventory().addItem(skull);

			getClient().sendMessage("Head", "You have been given " + Chat.player(getArgs()[0] + "'s Head."));
		}
		else
		{
			getClient().sendMessage("Command", "You need to be in buildmode.");
		}
	}
}
