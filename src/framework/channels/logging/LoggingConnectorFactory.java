package framework.channels.logging;

import java.net.Socket;

import framework.channels.IConnector;
import framework.channels.IConnectorFactory;

public class LoggingConnectorFactory<TRequest, TResponse> implements IConnectorFactory<TRequest, TResponse>
{
	// dependency
	private final IConnectorFactory<TRequest, TResponse> underlyingConnectorFactory;

	public LoggingConnectorFactory(IConnectorFactory<TRequest, TResponse> underlyingConnectorFactory)
	{
		// inject dependency
		if (underlyingConnectorFactory == null)
		{
			throw new NullPointerException("underlyingConnectorFactory is null");
		}

		this.underlyingConnectorFactory = underlyingConnectorFactory;
	}
	
	@Override
	public IConnector<TRequest, TResponse> createConnector(Socket clientSocket)
	{
		return new LoggingConnector<TRequest, TResponse>(this.underlyingConnectorFactory.createConnector(clientSocket));
	}
}
