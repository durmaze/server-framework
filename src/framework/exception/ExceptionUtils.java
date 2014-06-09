package framework.exception;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ExceptionUtils
{
	private static final String[] CAUSE_METHOD_NAMES = 		// names of methods commonly used to access a wrapped exception.
		{ 			
			"getCause",
			"getNextException",
			"getTargetException",
			"getException",
			"getSourceException",
			"getRootCause",
			"getCausedByException",
			"getNested",
			"getLinkedException",
			"getNestedException",
			"getLinkedCause",
			"getThrowable",
		};

	/**
	 * Examines the Throwable to obtain the cause.
	 * 
	 * The method searches for methods with specific names that return a
	 * Throwable object. 
	 * 
	 * If none of the above is found, returns null.
	 * 
	 * @param throwable
	 * @return the cause of the Throwable, null if none found
	 */
	public static Throwable getCause(Throwable throwable)
	{
		return getCause(throwable, CAUSE_METHOD_NAMES);
	}

	/**
	 * Examines the Throwable to obtain the cause with the given cause method names.
	 * 
	 * @param throwable 
	 * @param causeMethodNames the method names, null treated as default set
	 * @return the cause of the Throwable, null if none found
	 */
	public static Throwable getCause(Throwable throwable, String[] causeMethodNames)
	{
		if (throwable == null)
		{
			return null;
		}

		if (causeMethodNames == null)
		{
			causeMethodNames = CAUSE_METHOD_NAMES;
		}

		for (String methodName : causeMethodNames)
		{
			if (methodName != null)
			{
				Throwable cause = getCauseUsingMethodName(throwable, methodName);

				if (cause != null)
				{
					return cause;
				}
			}
		}

		return null;
	}

	/**
	 * Examines the Throwable to obtain the root cause. 
	 * If the throwable has no inner cause, then null is returned.
	 * 
	 * @param throwable 
	 * @return the cause of the Throwable, null if none found
	 */
	public static Throwable getRootCause(Throwable throwable)
	{
		List<Throwable> list = getThrowableList(throwable);
		return list.size() < 2 ? null : (Throwable) list.get(list.size() - 1);
	}

	/**
	 * Returns the list of Throwable objects in the exception chain.
	 * 
	 * @param throwable 
	 * @return the array of throwables, including the input throwable
	 */
	public static Throwable[] getThrowables(Throwable throwable)
	{
		List<Throwable> list = getThrowableList(throwable);
		
		return list.toArray(new Throwable[list.size()]);
	}

	/**
	 * Returns the list of Throwable objects in the exception chain.
	 * Returned list is never null, but returned items in the list can be null
	 * 
	 * @param throwable
	 * @return the list of throwables, including the input throwable
	 */
	public static List<Throwable> getThrowableList(Throwable throwable)
	{
		List<Throwable> list = new ArrayList<Throwable>();
		while (throwable != null && list.contains(throwable) == false)
		{
			list.add(throwable);
			throwable = ExceptionUtils.getCause(throwable);
		}
		return list;
	}

	/**
	 * Returns the index of the first Throwable that matches the specified class 
	 * in the exception chain. Subclasses of the specified class do not match.
	 * 
	 * @param throwable 
	 * @param clazz the class to search for
	 * @return the index into the throwable chain, -1 if no match
	 */
	public static int indexOfThrowable(Throwable throwable, Class<?> clazz)
	{
		return indexOf(throwable, clazz, 0, false);
	}

	/**
	 * Returns the index of the first Throwable that matches the specified class 
	 * in the exception chain. Subclasses of the specified class do match.
	 * 
	 * @param throwable 
	 * @param clazz the class to search for
	 * @return the index into the throwable chain, -1 if no match
	 */
	public static int indexOfType(Throwable throwable, Class<?> type)
	{
		return indexOf(throwable, type, 0, true);
	}
	
	/**
	 * Returns true if the specified class is contained in the exception chain. 
	 * Subclasses of the specified class do not match.
	 * 
	 * @param throwable 
	 * @param clazz the class to search for
	 * @return result
	 */
	public static boolean containsThrowable(Throwable throwable, Class<?> clazz)
	{
		return indexOfThrowable(throwable, clazz) != -1;
	}
	
	/**
	 * Returns true if the specified class is contained in the exception chain. 
	 * Subclasses of the specified class do match.
	 * 
	 * @param throwable 
	 * @param clazz the class to search for
	 * @return result
	 */
	public static boolean containsType(Throwable throwable, Class<?> type)
	{
		return indexOfType(throwable, type) != -1;
	}

	/**
	 * Finds a Throwable by method name.
	 * 
	 * @param throwable 
	 * @param methodName the name of the method to find and invoke
	 * @return the Throwable, null if none found
	 */
	private static Throwable getCauseUsingMethodName(Throwable throwable, String methodName)
	{
		Method method = null;
		try
		{
			method = throwable.getClass().getMethod(methodName);
		}
		catch (Exception ignored) { }
	
		if (method != null && Throwable.class.isAssignableFrom(method.getReturnType()))
		{
			try
			{
				return (Throwable) method.invoke(throwable);
			}
			catch (Exception ignored) { }
		}
		return null;
	}

	private static int indexOf(Throwable throwable, Class<?> type, int fromIndex, boolean includeSubclasses)
	{
		if (throwable == null || type == null)
		{
			return -1;
		}
		
		if (fromIndex < 0)
		{
			fromIndex = 0;
		}
		
		Throwable[] throwables = ExceptionUtils.getThrowables(throwable);
		
		if (fromIndex >= throwables.length)
		{
			return -1;
		}
		
		if (includeSubclasses)
		{
			for (int i = fromIndex; i < throwables.length; i++)
			{
				if (type.isAssignableFrom(throwables[i].getClass()))
				{
					return i;
				}
			}
		}
		else
		{
			for (int i = fromIndex; i < throwables.length; i++)
			{
				if (type.equals(throwables[i].getClass()))
				{
					return i;
				}
			}
		}
		return -1;
	}
}
