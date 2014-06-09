package framework.concurrency;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingAbortPolicy implements RejectedExecutionHandler
{
	// logger
	private final Logger logger = LoggerFactory.getLogger(LoggingAbortPolicy.class);
	
	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor)
	{
		// TODO Bu durumu test et. Alttaki exception catch edilince clientSocket kapat�l�yor mu?
		if (!executor.isShutdown())
		{
			this.logger.warn("Execution queue is full. Rejecting task! QueueSize: {}, ActiveThreadCount: {}, CorePoolSize: {}, MaxPoolSize: {}, CurrentPoolSize: {}", 
					new Object[] { executor.getQueue().size(), executor.getActiveCount(), executor.getCorePoolSize(), executor.getMaximumPoolSize(), executor.getPoolSize() });
		}
		
		throw new RejectedExecutionException();
	}
}
