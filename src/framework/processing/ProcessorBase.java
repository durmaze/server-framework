package framework.processing;

import framework.utils.TimeSpan;

public abstract class ProcessorBase implements IProcessor
{
	// dependency
	private final TimeSpan processorWaitTime;

	public ProcessorBase(TimeSpan processorWaitTime)
	{
		// inject dependency
		if (processorWaitTime == null)
		{
			throw new NullPointerException("processorWaitTime is null");
		}
		
		if (processorWaitTime.toMilliSeconds() < 500)
		{
			throw new IllegalArgumentException("processorWaitTime cannot be less than 500 milliseconds.");
		}

		this.processorWaitTime = processorWaitTime;
	}

	@Override
	public void process()
	{
		// since most processors need pacing to prevent cpu starvation, logic is added to the superclass
		waitFor(this.processorWaitTime);
		
		// NOTE subclasses must add processing logic
	}
	
	protected void waitFor(TimeSpan processorWaitTime)
	{
		try
		{
			synchronized (this)
			{
				wait(processorWaitTime.toMilliSeconds());
			}
		}
		catch (InterruptedException ignored) { }
	}
}
