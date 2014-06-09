package framework.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import framework.exception.ExceptionUtils;
import framework.processing.IProcessor;


public class ProcessingServer implements IServer
{
	// logger
	private final Logger logger = LoggerFactory.getLogger(ProcessingServer.class);
	
	// dependencies
	private final String serverName;
	private final IProcessor processor;

	// variants
	private boolean isStopped = false;
	
	public ProcessingServer(String serverName, IProcessor processor)
	{
		// inject dependencies
		if (serverName == null)
		{
			throw new NullPointerException("serverName is null");
		}
		
		if (processor == null)
		{
			throw new NullPointerException("processor is null");
		}

		this.serverName = serverName;
		this.processor = processor;
	}
	
	@Override
	public String getName()
	{
		return this.serverName;
	}

	@Override
	public void start()
	{
		run();
	}

	@Override
	public synchronized boolean stop()
	{
		if (this.isStopped() && this.processor.isShutdown())
		{
			return true;
		}
		else
		{
			this.isStopped = true;
			
			return false;
		}
	}

	private void run()
	{
		// ---------------------PROCESSOR LOOP------------------//

		while (!isStopped())
		{
			try
			{
				this.processor.process();
			}
			catch (Throwable throwable)
			{
				// if exception contains error, then stop the ProcessingServer, else log as warning and continue
				if (ExceptionUtils.containsType(throwable, Error.class))
				{
					this.logger.error("Unexpected error occurred in ProcessingServer (" + this.serverName + "). Processor: " + this.processor.getClass().getSimpleName(), throwable);
					stop();
				}
				else
				{
					this.logger.warn("Unexpected exception occurred in ProcessingServer (" + this.serverName + "). Processor: " + this.processor.getClass().getSimpleName(), throwable);
				}
			}
		}

		// ---------------------PROCESSOR LOOP------------------//

		this.processor.shutdown();
		
		this.logger.info("ProcessingServer ({}) is successfully stopped. (Processor: {})", this.serverName, this.processor.getClass().getSimpleName());
	}

	private synchronized boolean isStopped()
	{
		return this.isStopped;
	}
}
