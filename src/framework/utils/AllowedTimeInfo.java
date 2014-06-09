package framework.utils;


public class AllowedTimeInfo
{
	//constants
	public static final int NULL_TIME = -1;
	
	// dependencies
	private final int startTime;
	private final int endTime;
	private final AllowedDays daysAllowed;

	public AllowedTimeInfo(int startTime, int endTime)
	{
		// inject dependencies
		if ((startTime != NULL_TIME && endTime == NULL_TIME) || (startTime == NULL_TIME && endTime != NULL_TIME))
		{
			throw new IllegalArgumentException("Both StartTime and EndTime must be supplied");
		}
		
		if (startTime != NULL_TIME && endTime != NULL_TIME)
		{
			validateTime(startTime);
			validateTime(endTime);

			if (startTime >= endTime)
			{
				throw new IllegalArgumentException("EndTime must be greater than StartTime. StartTime: " + startTime + ", " + "EndTime: " + endTime);
			}
		}

		this.startTime = startTime;
		this.endTime = endTime;
		this.daysAllowed = null;
	}

	public AllowedTimeInfo(int startTime, int endTime, AllowedDays daysAllowed)
	{
		// inject dependencies
		if ((startTime != NULL_TIME && endTime == NULL_TIME) || (startTime == NULL_TIME && endTime != NULL_TIME))
		{
			throw new IllegalArgumentException("Both StartTime and EndTime must be supplied");
		}
		
		if (startTime != NULL_TIME && endTime != NULL_TIME)
		{
			validateTime(startTime);
			validateTime(endTime);

			if (startTime >= endTime)
			{
				throw new IllegalArgumentException("EndTime must be greater than StartTime. StartTime: " + startTime + ", " + "EndTime: " + endTime);
			}
		}
		
		this.startTime = startTime;
		this.endTime = endTime;
		this.daysAllowed = daysAllowed;
	}

	public int getStartTime()
	{
		return this.startTime;
	}

	public int getEndTime()
	{
		return this.endTime;
	}

	public AllowedDays getDaysAllowed()
	{
		return this.daysAllowed;
	}

	private void validateTime(int time)
	{
		if (time < 0)
		{
			throw new IllegalArgumentException("Time cannot be negative value");
		}
	
		String strTime = String.valueOf(time);
	
		if (strTime.length() > 6)
		{
			throw new IllegalArgumentException("Time cannot be longer than 6 digits");
		}
	
		// validate time
		String paddedTime = StringUtils.padLeft(strTime, "0", 6);
	
		int hour = Integer.parseInt(paddedTime.substring(0, 2));
		int minute = Integer.parseInt(paddedTime.substring(2, 4));
		int second = Integer.parseInt(paddedTime.substring(4, 6));
	
		if (hour >= 24 || minute >= 60 || second >= 60)
		{
			throw new IllegalArgumentException("Time is not in valid format");
		}
	}

	@Override
	public String toString()
	{
		return "AllowedTimeInfo [startTime=" + this.startTime + ", endTime=" + this.endTime + ", daysAllowed=" + this.daysAllowed + "]";
	}
}
