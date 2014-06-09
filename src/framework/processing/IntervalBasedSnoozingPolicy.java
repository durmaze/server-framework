package framework.processing;

import framework.utils.AllowedTimeInfo;
import framework.utils.DateTime;
import framework.utils.TimeSpan;


/**
 * IntervalBasedSnoozingPolicy
 * 
 * Snoozes if we are not in the allowed time interval 
 * 
 */
public class IntervalBasedSnoozingPolicy implements ISnoozingPolicy
{
	// dependencies
	private final AllowedTimeInfo allowedTimeInfo;
	private final TimeSpan snoozeTime;
	
	// variants
	private DateTime currentDate;

	public IntervalBasedSnoozingPolicy(AllowedTimeInfo allowedTimeInfo, TimeSpan snoozeTime)
	{
		// inject dependencies
		if (allowedTimeInfo == null)
		{
			throw new NullPointerException("allowedTimeInfo is null");
		}

		if (snoozeTime == null)
		{
			throw new NullPointerException("snoozeTime is null");
		}
		
		if (snoozeTime.toMilliSeconds() < 500)
		{
			throw new IllegalArgumentException("snoozeTime cannot be less than 500 milliseconds.");
		}

		this.allowedTimeInfo = allowedTimeInfo;
		this.snoozeTime = snoozeTime;
	}
	
	@Override
	public boolean shouldSnooze()
	{
		this.currentDate = new DateTime();
		
		return this.currentDate.getTime() < this.allowedTimeInfo.getStartTime() || this.currentDate.getTime() > this.allowedTimeInfo.getEndTime();
	}

	@Override
	public void snooze()
	{
		try
		{
			synchronized (this)
			{
				wait(this.snoozeTime.toMilliSeconds());
			}
		}
		catch (InterruptedException ignored) { }
	}
}
