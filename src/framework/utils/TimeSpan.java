package framework.utils;

/**
 * Utility class to get milliseconds equivalents of seconds, minutes, etc.
 */

public class TimeSpan
{
	private static final int ONE_SECOND = 1000;
	private static final int ONE_MINUTE = 60* ONE_SECOND;
	private static final int ONE_HOUR = 60 * ONE_MINUTE;

	// dependency
	private final long milliSeconds;

	public TimeSpan(long milliSeconds)
	{
		// inject dependency
		if (milliSeconds < 0)
		{
			throw new IllegalArgumentException("milliSeconds cannot be negative");
		}
		
		if (milliSeconds > Long.MAX_VALUE)
		{
			throw new IllegalArgumentException("milliSeconds exceeds allowed limit. Upper limit: " + Long.MAX_VALUE + ", MilliSeconds: " + milliSeconds);
		}

		this.milliSeconds = milliSeconds;
	}
	
	public static TimeSpan fromMilliSeconds(long milliSeconds)
	{
		return new TimeSpan(milliSeconds);
	}

	public static TimeSpan fromSeconds(int seconds)
	{
		if (seconds < 0)
		{
			throw new IllegalArgumentException("seconds cannot be smaller than zero!");
		}
		
		return new TimeSpan(seconds * ONE_SECOND);
	}
	
	public static TimeSpan fromMinutes(int minutes)
	{
		if (minutes < 0)
		{
			throw new IllegalArgumentException("minutes cannot be smaller than zero!");
		}
		
		return new TimeSpan(minutes * ONE_MINUTE);
	}
	
	public static TimeSpan fromHours(int hours)
	{
		if (hours < 0)
		{
			throw new IllegalArgumentException("hours cannot be smaller than zero!");
		}
		
		return new TimeSpan(hours * ONE_HOUR);
	}

	public Long toMilliSeconds()
	{
		return this.milliSeconds;
	}
}
