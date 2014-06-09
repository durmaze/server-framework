package framework.processing;

public class SnoozingProcessor implements IProcessor
{
	// dependencies
	private final IProcessor underlyingProcessor;
	private final ISnoozingPolicy snoozingPolicy;

	public SnoozingProcessor(IProcessor underlyingProcessor, ISnoozingPolicy snoozingPolicy)
	{
		// inject dependencies
		if (underlyingProcessor == null)
		{
			throw new NullPointerException("underlyingProcessor is null");
		}

		if (snoozingPolicy == null)
		{
			throw new NullPointerException("snoozingPolicy is null");
		}

		this.underlyingProcessor = underlyingProcessor;
		this.snoozingPolicy = snoozingPolicy;
	}
	
	@Override
	public void process()
	{
		while (this.snoozingPolicy.shouldSnooze())
		{
			this.snoozingPolicy.snooze();
		}
		
		this.underlyingProcessor.process();
	}

	@Override
	public void shutdown()
	{
		this.underlyingProcessor.shutdown();
	}

	@Override
	public boolean isShutdown()
	{
		return this.underlyingProcessor.isShutdown();
	}
}
