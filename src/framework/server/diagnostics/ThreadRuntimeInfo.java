package framework.server.diagnostics;

public class ThreadRuntimeInfo
{
	// dependencies
	private final String threadName;
	private final ThreadStateInfo stateInfo;	// thread's current state
	private final long timestamp;				// indicates when this thread info is created

	public ThreadRuntimeInfo(String threadName, ThreadStateInfo stateInfo)
	{
		// inject dependencies
		if (threadName == null || threadName.isEmpty())
		{
			throw new NullPointerException("threadName is null or empty");
		}

		if (stateInfo == null)
		{
			throw new NullPointerException("state is null");
		}

		this.threadName = threadName;
		this.stateInfo = stateInfo;
		
		this.timestamp = System.currentTimeMillis();
	}

	public String getThreadName()
	{
		return this.threadName;
	}

	public ThreadStateInfo getStateInfo()
	{
		return this.stateInfo;
	}

	public long getTimestamp()
	{
		return this.timestamp;
	}
}
