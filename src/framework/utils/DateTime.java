package framework.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * This is a DB-friendly utility class.
 * 
 * It handles (and centralizes) basic date operations and converts date information in various formats to the DB format.
 * 
 */
public class DateTime
{
	// constants
	private static final String DATABASE_DATE_FORMATTER_PATTERN = "yyyyMMdd";
	private static final String DATABASE_TIME_FORMATTER_PATTERN = "HHmmss";
	private static final String DATABASE_DATETIME_FORMATTER_PATTERN = "yyyyMMddHHmmss";
	private static final String YEARMONTH_FORMATTER_PATTERN = "yyyyMM";
	private static final String DATE_AND_TIME_FORMATTER_PATTERN = "yyyyMMdd HHmmss";
	private static final String HOUR_FORMATTER_PATTERN = "HH";
	
	public static final int NULL_DATE = -1;
	public static final int NULL_TIME = -1;
	public static final long NULL_DATETIME = -1L;

	// formatters (initialized when needed)
	private SimpleDateFormat databaseDateFormatter; 	// = new SimpleDateFormat(DATABASE_DATE_FORMATTER_PATTERN);
	private SimpleDateFormat databaseTimeFormatter; 	// = new SimpleDateFormat(DATABASE_TIME_FORMATTER_PATTERN);
	private SimpleDateFormat databaseDateTimeFormatter; // = new SimpleDateFormat(DATABASE_DATETIME_FORMATTER_PATTERN);
	private SimpleDateFormat dateTimeFormatter; 		// = new SimpleDateFormat(DATE_AND_TIME_FORMATTER_PATTERN);
	private SimpleDateFormat yearMonthFormatter;		// = new SimpleDateFormat(YEARMONTH_FORMATTER_PATTERN);
	private SimpleDateFormat hourFormatter;				// = new SimpleDateFormat(HOUR_FORMATTER_PATTERN);

	// invariant
	private final Date date; // internal Date object used to represent SqlDate

	/**
	 * Constructs an SqlDate object for current time
	 */
	public DateTime()
	{
		this.date = new Date();
	}

	/**
	 * Constructs an SqlDate object from Java date object.
	 */
	public DateTime(Date date)
	{
		this.date = date;
	}

	/**
	 * Constructs an SqlDate object from date and time.
	 * 
	 * @param date in yyyyMMdd format
	 * @param time in HHmmss format
	 * @return
	 */
	public DateTime(int date, int time)
	{
		if (date == NULL_DATE && time == NULL_TIME)
		{
			this.date = null;
		}
		else
		{
			if (date <= 0 || time < 0)
			{
				throw new IllegalArgumentException("Date/Time cannot be negative value. Date: " + date + " Time: " + time);
			}

			String strDate = String.valueOf(date);
			String strTime = String.valueOf(time);

			if (strDate.length() != 8)
			{
				throw new IllegalArgumentException("Date must be 8-digit long. Date: " + date + " Time: " + time);
			}

			if (strTime.length() > 6)
			{
				throw new IllegalArgumentException("Time cannot be longer than 6 digits. Date: " + date + " Time: " + time);
			}

			// validate time
			String paddedTime = StringUtils.padLeft(strTime, "0", 6);

			int hour = Integer.parseInt(paddedTime.substring(0, 2));
			int minute = Integer.parseInt(paddedTime.substring(2, 4));
			int second = Integer.parseInt(paddedTime.substring(4, 6));

			if (hour >= 24 || minute >= 60 || second >= 60)
			{
				throw new IllegalArgumentException("Time is not in valid format. Date: " + date + " Time: " + time);
			}

			// validate date
			String paddedDate = StringUtils.padLeft(strDate, "0", 8);

			// int year = Integer.parseInt(paddedDate.substring(0, 4));
			int month = Integer.parseInt(paddedDate.substring(4, 6));
			int day = Integer.parseInt(paddedDate.substring(6, 8));

			if (month > 12 || month == 0 || day > 31 || day == 0)
			{
				throw new IllegalArgumentException("Date is not in valid format. Date: " + date + " Time: " + time);
			}

			try
			{
				// INSIGHT burda formatter 8192 gibi bir time'� parse etti�inde exception throw etmiyor. bunun i�in yukar�daki kontrolleri yapt�k.
				this.dateTimeFormatter = new SimpleDateFormat(DATE_AND_TIME_FORMATTER_PATTERN);

				String logDateTime = paddedDate + " " + paddedTime;
				this.date = this.dateTimeFormatter.parse(logDateTime);
			}
			catch (Exception e)
			{
				throw new IllegalArgumentException("Date/Time is not in valid format. Date: " + date + " Time: " + time + " PaddedDate: " + paddedDate + " PaddedTime: " + paddedTime, e);
			}
		}
	}

	/**
	 * Constructs an SqlDate object from dateTime.
	 * 
	 * @param dateTime in yyyyMMddHHmmss format
	 * @return
	 */
	public DateTime(long dateTime)
	{
		if (dateTime == NULL_DATETIME)
		{
			this.date = null;
		}
		else
		{
			if (dateTime <= 0)
			{
				throw new IllegalArgumentException("DateTime must be a positive value. DateTime: " + dateTime);
			}

			String strDateTime = String.valueOf(dateTime);

			if (strDateTime.length() != 14)
			{
				throw new IllegalArgumentException("DateTime must be 14-digit long. DateTime: " + dateTime);
			}

			// get date and time parts
			String strDate = strDateTime.substring(0, 8);
			String strTime = strDateTime.substring(8, 14);

			// validate time
			String paddedTime = StringUtils.padLeft(strTime, "0", 6);

			int hour = Integer.parseInt(paddedTime.substring(0, 2));
			int minute = Integer.parseInt(paddedTime.substring(2, 4));
			int second = Integer.parseInt(paddedTime.substring(4, 6));

			if (hour >= 24 || minute >= 60 || second >= 60)
			{
				throw new IllegalArgumentException("Time is not in valid format. DateTime: " + dateTime);
			}

			// validate date
			String paddedDate = StringUtils.padLeft(strDate, "0", 8);

			// int year = Integer.parseInt(paddedDate.substring(0, 4));
			int month = Integer.parseInt(paddedDate.substring(4, 6));
			int day = Integer.parseInt(paddedDate.substring(6, 8));

			if (month > 12 || month == 0 || day > 31 || day == 0)
			{
				throw new IllegalArgumentException("Date is not in valid format. DateTime: " + dateTime);
			}

			try
			{
				// INSIGHT burda formatter 8192 gibi bir time'� parse etti�inde exception throw etmiyor. bunun i�in yukar�daki kontrolleri yapt�k.
				this.dateTimeFormatter = new SimpleDateFormat(DATE_AND_TIME_FORMATTER_PATTERN);
				
				String logDateTime = paddedDate + " " + paddedTime;
				this.date = this.dateTimeFormatter.parse(logDateTime);
			}
			catch (Exception e)
			{
				throw new IllegalArgumentException("DateTime is not in valid format. DateTime: " + dateTime + " PaddedDate: " + paddedDate + " PaddedTime: " + paddedTime, e);
			}
		}
	}

	/**
	 * Parses given dateTime string to DB time format. String is parsed to SqlDate object using the given dateTime formatter.
	 * 
	 * If dateTime cannot be parsed, -1 is returned.
	 * 
	 * @param dateTime input string
	 * @param dateTimeFormatter format to be used to parse the dateTime
	 */
	public DateTime(String dateTime, SimpleDateFormat dateTimeFormatter)
	{
		Date parsedDate = null;

		try
		{
			parsedDate = dateTimeFormatter.parse(dateTime);
		}
		catch (ParseException e)
		{
		}

		this.date = parsedDate;
	}

	public Date toDate()
	{
		return this.date;
	}

	/**
	 * Formats given date in DB-format
	 * 
	 * @param date
	 * @return returns given date in DB-format
	 */
	public int getDate()
	{
		if (this.date == null)
		{
			return NULL_DATE;
		}

		if (this.databaseDateFormatter == null)
		{
			this.databaseDateFormatter = new SimpleDateFormat(DATABASE_DATE_FORMATTER_PATTERN);
		}

		String strDate = this.databaseDateFormatter.format(this.date);
		return Integer.parseInt(strDate);
	}

	/**
	 * Formats given time in DB-format
	 * 
	 * @param time
	 * @return returns given time in DB-format
	 */
	public int getTime()
	{
		if (this.date == null)
		{
			return NULL_TIME;
		}

		if (this.databaseTimeFormatter == null)
		{
			this.databaseTimeFormatter = new SimpleDateFormat(DATABASE_TIME_FORMATTER_PATTERN);
		}

		String strTime = this.databaseTimeFormatter.format(this.date);
		return Integer.parseInt(strTime);
	}

	/**
	 * Formats given date in year+month format
	 * 
	 * @param date
	 * @return returns given date in year+month format
	 */
	public int getMonth()
	{
		if (this.date == null)
		{
			return NULL_DATE;
		}

		if (this.yearMonthFormatter == null)
		{
			this.yearMonthFormatter = new SimpleDateFormat(YEARMONTH_FORMATTER_PATTERN);
		}
		
		String strDate = this.yearMonthFormatter.format(this.date);
		return Integer.parseInt(strDate);
	}

	/**
	 * Formats given date and time in DB-format
	 * 
	 * @return returns given dateTime in DB-format
	 */
	public long getDateTime()
	{
		if (this.date == null)
		{
			return NULL_DATETIME;
		}

		if (this.databaseDateTimeFormatter == null)
		{
			this.databaseDateTimeFormatter = new SimpleDateFormat(DATABASE_DATETIME_FORMATTER_PATTERN);
		}

		String strDateTime = this.databaseDateTimeFormatter.format(this.date);
		return Long.parseLong(strDateTime);
	}
	
	/**
	 * Formats given date in hour(24) format
	 * @param date
	 * @return returns given date in hour(24) format 
	 */
	public int getHour()
	{
		if (this.date == null)
		{
			return NULL_DATE;
		}
		
		if (this.hourFormatter == null)
		{
			this.hourFormatter = new SimpleDateFormat(HOUR_FORMATTER_PATTERN);
		}
		
		String strHour = this.hourFormatter.format(this.date);
		return Integer.parseInt(strHour);
	}

	/**
	 * Returns the number of milliseconds since the Epoch.
	 * 
	 * @return
	 */
	public long toMilliSeconds()
	{
		return this.date.getTime();
	}

	public DateTime addDay(int days)
	{
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(this.date);
		calendar.add(Calendar.DATE, days);

		return new DateTime(calendar.getTime());
	}

	public DateTime addHour(int hours)
	{
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(this.date);
		calendar.add(Calendar.HOUR_OF_DAY, hours);

		return new DateTime(calendar.getTime());
	}

	public DateTime addMinute(int minutes)
	{
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(this.date);
		calendar.add(Calendar.MINUTE, minutes);

		return new DateTime(calendar.getTime());
	}

	public DateTime addSeconds(int seconds)
	{
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(this.date);
		calendar.add(Calendar.SECOND, seconds);

		return new DateTime(calendar.getTime());
	}

	public DateTime subtractDay(int days)
	{
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(this.date);
		calendar.add(Calendar.DATE, -days);

		return new DateTime(calendar.getTime());
	}

	public DateTime subtractHour(int hours)
	{
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(this.date);
		calendar.add(Calendar.HOUR_OF_DAY, -hours);

		return new DateTime(calendar.getTime());
	}

	public DateTime subtractMinute(int minutes)
	{
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(this.date);
		calendar.add(Calendar.MINUTE, -minutes);

		return new DateTime(calendar.getTime());
	}

	public DateTime subtractSeconds(int seconds)
	{
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(this.date);
		calendar.add(Calendar.SECOND, -seconds);

		return new DateTime(calendar.getTime());
	}

	/**
     * Compares two SqlDate objects numerically.
     *
     * @param   anotherSqlDate   the SqlDate to be compared.
     * @return	the value 0 if this SqlDate is equal to the argument SqlDate; 
     * 			a value less than 0 if this SqlDate is numerically less than the argument SqlDate; 
     * 			and a value greater than 0 if this SqlDate is numerically greater than the argument SqlDate.
     */
	public int compareTo(DateTime anotherSqlDate)
	{
//		return new String(String.valueOf(getDate()) + String.valueOf(getTime())).compareTo(new String(String.valueOf(sqlDate.getDate()) + String.valueOf(sqlDate.getTime())));
		return new Long(getDateTime()).compareTo(new Long(anotherSqlDate.getDateTime()));
	}
	
	public boolean isAllowed(AllowedDays daysAllowed)
	{
		int dayOfWeek = getDayOfWeek();
		return daysAllowed.toString().charAt(dayOfWeek) == '1';
	}

	/** returns the offset number starting from MONDAY until the day of week */
	public int getDayOfWeek() 
	{
		return DateUtils.getDayOfWeek(this.date);
	}

	/**
	 * @return returns an id for each week of the yar
	 */
	public int getWeekId() 
	{
		if (this.date == null)
		{
			return 0;
		}
		
		return DateUtils.getWeekOfYear(this.date);
	}

	@Override
	public String toString()
	{
		return "SqlDate [date=" + this.date.toString() + "]";
	}
	
	public String toString(SimpleDateFormat dateTimeFormatter)
	{
		if (dateTimeFormatter == null)
		{
			throw new NullPointerException("dateTimeFormatter is null");
		}
		
		return dateTimeFormatter.format(this.date);
	}
}
