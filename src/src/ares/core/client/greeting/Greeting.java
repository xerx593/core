package src.ares.core.client.greeting;

import java.util.Collections;
import java.util.Random;

public class Greeting
{
	private GreetingData data = new GreetingData();
	private Random random = new Random();

	public String chooseGreeting()
	{
		int index = random.nextInt(data.getGreetings().size());
		return data.getGreetings().get(index);
	}

	public void shuffleGreetings()
	{
		Collections.shuffle(data.getGreetings());
	}

	public GreetingData getGreetingData()
	{
		return data;
	}
}
