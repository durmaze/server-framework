package framework.concurrency;

import java.lang.Thread.UncaughtExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import framework.exception.ExceptionUtils;

public class LoggingUncaughtExceptionHandler implements UncaughtExceptionHandler
{
	// logger
	private final Logger logger = LoggerFactory.getLogger(LoggingUncaughtExceptionHandler.class);

	@Override
	public void uncaughtException(Thread thread, Throwable e)
	{
		if (ExceptionUtils.containsType(e, Error.class))
		{
			this.logger.error("Uncaught error occurred in thread. Thread Name: " + thread.getName() + ", Thread Id: " + thread.getId(), e);
		}
		else
		{
			this.logger.warn("Uncaught exception occurred in thread. Thread Name: " + thread.getName() + ", Thread Id: " + thread.getId(), e);
		}
	}
}
