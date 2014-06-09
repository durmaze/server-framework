package framework.channels.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import framework.channels.IChannelHandler;
import framework.exception.ServerException;

public class LoggingChannelHandler<TRequest, TResponse> implements IChannelHandler<TRequest, TResponse>
{
	// logger
	private final Logger logger = LoggerFactory.getLogger(LoggingChannelHandler.class);
	
	// dependency
	private final IChannelHandler<TRequest, TResponse> underlyingChannelHandler;

	public LoggingChannelHandler(IChannelHandler<TRequest, TResponse> underlyingChannelHandler)
	{
		// inject dependency
		if (underlyingChannelHandler == null)
		{
			throw new NullPointerException("underlyingChannelHandler is null");
		}

		this.underlyingChannelHandler = underlyingChannelHandler;
	}
	
	@Override
	public TResponse handle(TRequest request)
	{
		try
		{
			if (request != null)
			{
				this.logger.debug("Sending Request to IChannelHandler ({}). Request: {}", this.underlyingChannelHandler.getClass().getSimpleName(), request);
			}
			
			TResponse response = this.underlyingChannelHandler.handle(request);
			
			if (response != null)
			{
				this.logger.debug("Response is returned by IChannelHandler ({}). Response: {}", this.underlyingChannelHandler.getClass().getSimpleName(), response);
			}
			
			return response;
		}
		catch (ServerException e)
		{
			this.logger.error("Exception occured in handling request by IChannelHandler ({}). Arguments => {}", "Request: " + (request != null ? request.toString() : ""), e);
			throw e;
		}
	}
}
