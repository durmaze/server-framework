package framework.concurrency;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerThreadFactory implements ThreadFactory
{
	// dependencies & invariants
	private final String poolName;
	private final List<String> threadNamePrefixes;
	private final UncaughtExceptionHandler uncaughtExceptionHandler;
	private final AtomicInteger threadNumber = new AtomicInteger(1);

	public ServerThreadFactory(String poolName)
	{
		// inject dependency
		if (poolName == null || poolName.isEmpty())
		{
			throw new NullPointerException("poolName is null or empty");
		}
		
		this.poolName = poolName;
		this.threadNamePrefixes = null;
		this.uncaughtExceptionHandler = null;
	}

	public ServerThreadFactory(String poolName, List<String> threadNamePrefixes)
	{
		// inject dependency
		if (poolName == null || poolName.isEmpty())
		{
			throw new NullPointerException("poolName is null or empty");
		}

		if (threadNamePrefixes == null || threadNamePrefixes.isEmpty())
		{
			throw new NullPointerException("threadNamePrefixes is null or empty");
		}

		this.poolName = poolName;
		this.threadNamePrefixes = threadNamePrefixes;
		this.uncaughtExceptionHandler = null;
	}

	public ServerThreadFactory(String poolName, List<String> threadNamePrefixes, UncaughtExceptionHandler uncaughtExceptionHandler)
	{
		// inject dependency
		if (poolName == null || poolName.isEmpty())
		{
			throw new NullPointerException("poolName is null or empty");
		}

		if (threadNamePrefixes == null || threadNamePrefixes.isEmpty())
		{
			throw new NullPointerException("threadNamePrefixes is null or empty");
		}
		
		if (uncaughtExceptionHandler == null)
		{
			throw new NullPointerException("uncaughtExceptionHandler is null");
		}
		
		this.poolName = poolName;
		this.threadNamePrefixes = threadNamePrefixes;
		this.uncaughtExceptionHandler = uncaughtExceptionHandler;
	}
	
	@Override
	public Thread newThread(Runnable runnable)
	{
		String threadName = null;
		
		if (this.threadNamePrefixes == null)
		{
			threadName = this.poolName + "-Thread-" + this.threadNumber.getAndIncrement();
		}
		else
		{
			/*
			 * round-robin prefixes wrt current thread number
			 * 
			 * Example:
			 * -------- 
			 * prefixes {PrefixA, PrefixB, PrefixC} => prefixes.size() is 3
			 * threadNumber is 5
			 * 
			 * selected prefix is prefixes.get((5 - 1) % 3) (i.e. prefixes.get(1)) => PrefixB
			 */
			
			threadName = this.poolName + "-" + this.threadNamePrefixes.get((this.threadNumber.get() - 1) % this.threadNamePrefixes.size()) + "-Thread-" + this.threadNumber.getAndIncrement();
		}

//		Thread thread = new Thread(runnable, threadName); // LATER ileride bunu ServerThread'e �evir.
		ServerThread thread = new ServerThread(runnable, threadName); 
		
		if (this.uncaughtExceptionHandler != null)
		{
			thread.setUncaughtExceptionHandler(this.uncaughtExceptionHandler);
		}
		
		return thread;
	}
}
