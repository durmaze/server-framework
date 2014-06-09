package framework.channels.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import framework.channels.IConnector;
import framework.exception.ServerException;

public class LoggingConnector<TRequest, TResponse> implements IConnector<TRequest, TResponse>
{
	// logger
	private final Logger logger = LoggerFactory.getLogger(LoggingConnector.class);
	
	// dependency
	private final IConnector<TRequest, TResponse> underlyingConnector;

	public LoggingConnector(IConnector<TRequest, TResponse> underlyingConnector)
	{
		// inject dependency
		if (underlyingConnector == null)
		{
			throw new NullPointerException("underlyingConnector is null");
		}

		this.underlyingConnector = underlyingConnector;
	}
	
	@Override
	public TRequest receive()
	{
		try
		{
			TRequest request = this.underlyingConnector.receive();
			
			if (request != null)
			{
				this.logger.debug("{} is received. Request: {}", request.getClass().getSimpleName(), request.toString());
			}
			
			return request;
		}
		catch (ServerException e)
		{
			this.logger.error("Exception occurred in receiving request.", e);
			throw e;
		}
	}

	@Override
	public void dispatch(TResponse response)
	{
		try
		{
			if (response != null)
			{
				this.logger.debug("{} is being sent. Response: {}", response.getClass().getSimpleName(), response.toString());
			}
			
			this.underlyingConnector.dispatch(response);
		}
		catch (ServerException e)
		{
			this.logger.error("Exception occurred in dispatching response.", e);
			throw e;
		}
	}

	@Override
	public void dispatch(Exception ex)
	{
		try
		{
			if (ex != null)
			{
				String errorMessage = (ex.getCause() != null) ? ex.getMessage() + " (Cause: " + ex.getCause().getMessage().trim() + ")" : ex.getMessage();
				
				this.logger.debug("Error response is being sent. ErrorMessage: {}", errorMessage);
			}
			
			this.underlyingConnector.dispatch(ex);
		}
		catch (ServerException e)
		{
			this.logger.error("Exception occurred in dispatching Error response.", e);
			throw e;
		}
	}

	@Override
	public void close()
	{
		this.underlyingConnector.close();
	}
}
