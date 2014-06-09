package framework.utils;

public class Utils
{
	@SuppressWarnings("unchecked")
	public static <T> T unsafeCast(Object o) 
	{
	    return (T) o;
	}
}
