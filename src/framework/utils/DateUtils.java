package framework.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils
{
	public static int getDayOfWeek(Date date)
	{
		return getDayOfWeek(date, 0);
	}

	public static int getDayOfWeek(Date date, int offset)
	{
		if (date == null)
		{
			throw new NullPointerException("date is null");
		}
		
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		
		return offset + ((7 + calendar.get(GregorianCalendar.DAY_OF_WEEK) - GregorianCalendar.MONDAY) % 7);
	}

	public static int getWeekOfYear(Date date)
	{
		if (date == null)
		{
			throw new NullPointerException("date is null");
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		int i = cal.get(Calendar.WEEK_OF_YEAR);
		String strWeek = cal.get(Calendar.YEAR) + (i < 10 ? "0" : "") + i;

		return Integer.parseInt(strWeek);
	}
}
