package src.ares.core.common.util;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;

import src.ares.core.server.data.ServerStorage;

public class UtilString
{
	public static String build(String[] array, int index)
	{
		String result = "";

		for (int i = index; i < array.length; i++)
		{
			result = result + array[i] + " ";
		}

		return result.trim();
	}

	@SuppressWarnings("deprecation")
	public static String cap(String word)
	{
		return StringUtils.capitaliseAllWords(word);
	}

	public static String disconnect(String head, String body)
	{
		ServerStorage storage = ServerStorage.getInstance();
		String header = storage.getServerName();

		return header + "\n\n" + head + "\n" + ChatColor.RESET + body;
	}

	public static String enumerator(String word)
	{
		return word.toLowerCase().trim().replace(' ', '_').replace('-', '_').toUpperCase();
	}

	public static String format(String word)
	{
		return cap(word.toLowerCase().trim().replace('_', ' ').replace('-', ' '));
	}

	public static List<String> getWords(String text)
	{
		List<String> words = new ArrayList<String>();
		BreakIterator breakIterator = BreakIterator.getWordInstance();
		breakIterator.setText(text);
		int lastIndex = breakIterator.first();

		while (BreakIterator.DONE != lastIndex)
		{
			int firstIndex = lastIndex;
			lastIndex = breakIterator.next();

			if (lastIndex != BreakIterator.DONE && Character.isLetterOrDigit(text.charAt(firstIndex)))
			{
				words.add(text.substring(firstIndex, lastIndex));
			}
		}

		return words;
	}

	public static String slug(String word)
	{
		return word.toLowerCase().trim().replace(' ', '-');
	}
}
