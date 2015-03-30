package src.ares.core.vote;

import org.bukkit.event.EventHandler;

import src.ares.core.chat.Notification;
import src.ares.core.client.OfflineClient;
import src.ares.core.common.Module;
import src.ares.core.common.util.Chat;
import src.ares.core.currency.type.GoldCurrency;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

public class VoteListener extends Module
{
	private Notification notification = new Notification();

	public VoteListener()
	{
		super("Vote");
	}

	@EventHandler
	public void onVote(VotifierEvent event)
	{
		Vote vote = event.getVote();
		OfflineClient client = new OfflineClient(vote.getUsername());

		Reward reward = new Reward(client);
		int amount = reward.goldReward(100, 300);

		notification.sendToPlayers(false, getModuleName(), Chat.player(vote.getUsername()) + " voted on " + Chat.link(vote.getServiceName()) + " and got " + Chat.gold(new GoldCurrency(amount).getFormatted()) + ".");
	}
}
