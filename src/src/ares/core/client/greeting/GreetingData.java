package src.ares.core.client.greeting;

import java.util.ArrayList;
import java.util.List;

public class GreetingData
{
	private static List<String> greetings = new ArrayList<>();

	public GreetingData()
	{
		if (greetings.isEmpty())
		{
			greetings.add("Have a wonderful time here.");
			greetings.add("Have a good time here.");
			greetings.add("How are you doing?");
			greetings.add("How are things?");
			greetings.add("How's your day going");
			greetings.add("How have you been?");
			greetings.add("G'day mate!");
			greetings.add("Everything is just fine.");
			greetings.add("One of a sunny day, today!");
			greetings.add("Good day to eat some oreos.");
		}
	}

	public List<String> getGreetings()
	{
		return greetings;
	}
}
