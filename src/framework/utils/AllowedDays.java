package framework.utils;


public class AllowedDays
{
	// dependency
	private final String daysAllowed;
	
	private final boolean isMondayAllowed;
	private final boolean isTuesdayAllowed;
	private final boolean isWednesdayAllowed;
	private final boolean isThursdayAllowed;
	private final boolean isFridayAllowed;
	private final boolean isSaturdayAllowed;
	private final boolean isSundayAllowed;
	
	public AllowedDays(String daysAllowed)
	{
		// inject dependency
		if (daysAllowed == null)
		{
			throw new NullPointerException("daysAllowed is null");
		}

		this.daysAllowed = daysAllowed;
		
		// validate daysAllowed & update invariants
		String paddedDaysAllowed = StringUtils.padLeft(daysAllowed, "0", 7);
		
		this.isMondayAllowed = toBoolean(paddedDaysAllowed, 0);
		this.isTuesdayAllowed = toBoolean(paddedDaysAllowed, 1);
		this.isWednesdayAllowed = toBoolean(paddedDaysAllowed, 2);
		this.isThursdayAllowed = toBoolean(paddedDaysAllowed, 3);
		this.isFridayAllowed = toBoolean(paddedDaysAllowed, 4);
		this.isSaturdayAllowed = toBoolean(paddedDaysAllowed, 5);
		this.isSundayAllowed = toBoolean(paddedDaysAllowed, 6);
	}

	public boolean isMondayAllowed()
	{
		return this.isMondayAllowed;
	}

	public boolean isTuesdayAllowed()
	{
		return this.isTuesdayAllowed;
	}

	public boolean isWednesdayAllowed()
	{
		return this.isWednesdayAllowed;
	}

	public boolean isThursdayAllowed()
	{
		return this.isThursdayAllowed;
	}

	public boolean isFridayAllowed()
	{
		return this.isFridayAllowed;
	}

	public boolean isSaturdayAllowed()
	{
		return this.isSaturdayAllowed;
	}

	public boolean isSundayAllowed()
	{
		return this.isSundayAllowed;
	}

	@Override
	public String toString()
	{
		return this.daysAllowed;
	}

	private boolean toBoolean(String daysAllowed, int index)
	{
		char ch = daysAllowed.charAt(index);
		
		if (ch == '0')
		{
			return false;
		}
		
		if (ch == '1')
		{
			return true;
		}
		
		throw new IllegalArgumentException("daysAllowed contains non-binary value at index " + index + ". DaysAllowed: " + daysAllowed); 
	}
}
