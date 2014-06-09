package framework.server.diagnostics;

public class ThreadStateInfo
{
	// dependency
	private final String state;			// indicates thread's state
	private final String description;	// indicates detailed information about thread's state
	private final long timestamp;		// indicates when this thread state started
	
	public ThreadStateInfo(String state)
	{
		// inject dependency
		if (state == null || state.isEmpty())
		{
			throw new NullPointerException("state is null or empty");
		}

		this.state = state;
		
		this.description = null;
		this.timestamp = System.currentTimeMillis();
	}

	public ThreadStateInfo(String state, String description)
	{
		// inject dependency
		if (state == null || state.isEmpty())
		{
			throw new NullPointerException("state is null or empty");
		}
		
		this.state = state;
		
		this.description = description;
		this.timestamp = System.currentTimeMillis();
	}
	
	public String getState()
	{
		return this.state;
	}

	public String getDescription()
	{
		return description;
	}

	public long getTimestamp()
	{
		return this.timestamp;
	}
}	
