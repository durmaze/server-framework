package framework.server.diagnostics;

public class ThreadInspectionResult
{
	// dependencies
	private final String threadName;
	private final String threadState;
	private final long executionElapsedTime;
	private final long stateElapsedTime;

	public ThreadInspectionResult(String threadName, String threadState, long executionElapsedTime, long stateElapsedTime)
	{
		// inject dependencies
		if (threadName == null)
		{
			throw new NullPointerException("threadName is null");
		}

		if (threadState == null)
		{
			throw new NullPointerException("threadState is null");
		}

		if (executionElapsedTime < 0)
		{
			throw new IllegalArgumentException("executionElapsedTime cannot be negative");
		}

		if (stateElapsedTime < 0)
		{
			throw new IllegalArgumentException("stateElapsedTime cannot be negative");
		}

		this.threadName = threadName;
		this.threadState = threadState;
		this.executionElapsedTime = executionElapsedTime;
		this.stateElapsedTime = stateElapsedTime;
	}

	public String getThreadName()
	{
		return this.threadName;
	}

	public String getThreadState()
	{
		return this.threadState;
	}

	public long getExecutionElapsedTime()
	{
		return this.executionElapsedTime;
	}

	public long getStateElapsedTime()
	{
		return this.stateElapsedTime;
	}
}
