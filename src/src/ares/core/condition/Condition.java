package src.ares.core.condition;

import java.util.ArrayList;
import java.util.List;

public abstract class Condition<T>
{
	private List<T> bag;

	public Condition()
	{
		this.bag = new ArrayList<>();
	}

	public void addItem(T item)
	{
		this.bag.add(item);
	}

	public List<T> getBag()
	{
		return bag;
	}

	public String getType()
	{
		return "Condition";
	}

	public boolean hasItem(T item)
	{
		return this.bag.contains(item);
	}

	public void removeItem(T item)
	{
		this.bag.remove(item);
	}
}
