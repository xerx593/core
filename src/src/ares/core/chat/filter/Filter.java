package src.ares.core.chat.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import src.ares.core.common.util.UtilString;

public class Filter
{
	private String sentence;
	private FilterRepository filter = new FilterRepository();
	private Random random = new Random();
	private ArrayList<String> matches = new ArrayList<>();

	/**
	 * Default Constructor
	 * 
	 * @param sentence The sentence to store for validation.
	 * @see validate()
	 */
	public Filter(String sentence)
	{
		this.sentence = sentence;
	}

	/**
	 * Replaces the string with asterisks according the size of the word.
	 * 
	 * @param match The word to replace.
	 * @return String
	 */
	public String getAsterisks(String match)
	{
		List<String> asterisks = new ArrayList<>();
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < match.length(); i++)
		{
			asterisks.add("*");
		}

		for (String string : asterisks)
		{
			builder.append(string);
		}

		return builder.toString();
	}

	/**
	 * Returns a formatted version of the sentence with all the matchings replaced by asterisks.
	 * In order to find matchings, you must run validate() function first.
	 * Otherwise, it will not find anything to replace.
	 * 
	 * @return String
	 */
	public String getFiltered()
	{
		if (!matches.isEmpty())
		{
			for (String match : matches)
			{
				sentence = sentence.replace(match, getAsterisks(match));
			}

			return sentence;
		}

		return sentence;
	}

	public FilterRepository getFilterRepository()
	{
		return filter;
	}

	public String getRandomReplace()
	{
		return filter.getReplaces()[random.nextInt(filter.getReplaces().length)];
	}

	public String getSentence()
	{
		return sentence;
	}

	/**
	 * Breaks up a specific sentence and adds any possible matches to an array.
	 * 
	 * @return If the sentence has at least 1 match.
	 */
	public boolean validate()
	{
		// Looping through all possible matchings.

		for (String word : UtilString.getWords(sentence))
		{
			// Breaking up the sentence to match any positive words.

			for (String matching : filter.getWords())
			{
				// If we find any matches, adding them to the list.

				if (word.contains(matching) && !matches.contains(matching))
				{
					matches.add(matching);
				}
			}
		}

		return matches.isEmpty();
	}
}
