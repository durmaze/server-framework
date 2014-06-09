package framework.utils;

import java.util.Arrays;
import java.util.Iterator;

public class StringUtils
{
	/**
	 * Joins a String array into one string, where each string element is delimited by the delimiter character
	 * @param iterable
	 * @param delimiter
	 * @return
	 */
	public static String join(String[] strings, String delimiter)
	{
		return join(Arrays.asList(strings), delimiter);
	}
	
	/**
	 * Joins a iterable String collection into one string, where each string element is delimited by the delimiter character
	 * @param iterable
	 * @param delimiter
	 * @return
	 */
	public static String join(Iterable<String> iterable, String delimiter)
	{
		if (iterable == null || delimiter == null)
		{
			throw new IllegalArgumentException("iterable or delimiter cannot be null");
		}
		
		Iterator<String> iterator = iterable.iterator();

		// Guard against empty list
		StringBuilder joinedStringBuilder = iterator.hasNext() ? new StringBuilder(iterator.next()) : new StringBuilder();
		
		// If we have further items, iterate and add (delimiter + string) to the joinedString
		while (iterator.hasNext())
		{
			joinedStringBuilder.append(delimiter).append(iterator.next());
		}
		
		return joinedStringBuilder.toString();
	}
	
	/**
	 * pads a String from right with the specified paddingCharacter until the final string length reaches totalLength
	 * @param aString string to be padded
	 * @param paddingCharacter 
	 * @param totalLength final length of the resulting string
	 * @return
	 */
	public static String padRight(String aString, String paddingCharacter, int totalLength) 
	{
		StringBuffer sb = new StringBuffer(aString);
		
		for (int i = 0; i < (totalLength - aString.length()); i++)
		{
			sb.append(paddingCharacter);
		}
		
		return sb.toString();
	}

	/**
	 * pads a String from left with the specified paddingCharacter until the final string length reaches totalLength
	 * @param aString string to be padded
	 * @param paddingCharacter 
	 * @param totalLength final length of the resulting string
	 * @return
	 */
	public static String padLeft(String aString, String paddingCharacter, int totalLength) 
	{
		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < (totalLength - aString.length()); i++)
		{
			sb.append(paddingCharacter);
		}
		sb.append(aString);
		
		return sb.toString();
	}
	
	public static String takeFirst(String aString, int size)
	{
		if (aString == null || size <= 0 || size > aString.length())
		{
			return aString;
		}
		
		return aString.substring(0, size);
	}

	public static String replaceFirst(String searchString, String aString, String replacement)
	{
		if (aString == null || aString.isEmpty())
		{
			return aString;
		}
		
		StringBuffer sb =  new StringBuffer(aString);

		int index = aString.indexOf(searchString);
		
		if (index > -1)
		{
			sb.replace(index, index + 1, replacement);
		}
		
		return sb.toString();
	}

	public static String replaceLast(String searchString, String aString, String replacement)
	{
		if (aString == null || aString.isEmpty())
		{
			return aString;
		}
		
		StringBuffer sb =  new StringBuffer(aString);

		int index = aString.lastIndexOf(searchString);
		
		if (index > -1)
		{
			sb.replace(index, index + 1, replacement);
		}
		
		return sb.toString();
	}

	public static String toLatin(String string)
	{
		if (string == null || string.isEmpty())
		{
			return string;
		}
		
		String stringInLatin = string;

		/*
		 * 
		 * retval=retval.replace('�','i'); retval=retval.replace('�','I');
		 * retval=retval.replace('�','s'); retval=retval.replace('�','S');
		 * retval=retval.replace('�','g'); retval=retval.replace('�','G');
		 * retval=retval.replace('�','c'); retval=retval.replace('�','C');
		 * retval=retval.replace('�','u'); retval=retval.replace('�','u');
		 * retval=retval.replace('�','o'); retval=retval.replace('�','O');
		 * 
		 */

		stringInLatin = stringInLatin.replace((char) 305, 'i');
		stringInLatin = stringInLatin.replace((char) 304, 'I');
		stringInLatin = stringInLatin.replace((char) 351, 's');
		stringInLatin = stringInLatin.replace((char) 350, 'S');
		stringInLatin = stringInLatin.replace((char) 287, 'g');
		stringInLatin = stringInLatin.replace((char) 286, 'G');
		stringInLatin = stringInLatin.replace((char) 231, 'c');
		stringInLatin = stringInLatin.replace((char) 199, 'C');
		stringInLatin = stringInLatin.replace((char) 252, 'u');
		stringInLatin = stringInLatin.replace((char) 220, 'U');
		stringInLatin = stringInLatin.replace((char) 246, 'o');
		stringInLatin = stringInLatin.replace((char) 214, 'O');

		return stringInLatin;
	}
}