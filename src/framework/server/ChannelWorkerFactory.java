package framework.server;

import java.net.Socket;

import framework.channels.IChannelHandlerFactory;
import framework.channels.IConnectorFactory;

public class ChannelWorkerFactory<TRequest, TResponse> implements IWorkerFactory
{
	// dependencies
	private final IConnectorFactory<TRequest, TResponse> connectorFactory;
	private final IChannelHandlerFactory<TRequest, TResponse> channelHandlerFactory;

	public ChannelWorkerFactory(IConnectorFactory<TRequest, TResponse> connectorFactory, IChannelHandlerFactory<TRequest, TResponse> channelHandlerFactory)
	{
		// inject dependencies
		if (connectorFactory == null)
		{
			throw new NullPointerException("connectorFactory is null");
		}

		if (channelHandlerFactory == null)
		{
			throw new NullPointerException("channelHandlerFactory is null");
		}

		this.connectorFactory = connectorFactory;
		this.channelHandlerFactory = channelHandlerFactory;
	}
	
	@Override
	public IWorker createWorker(Socket clientSocket)
	{
		return new ChannelWorker<TRequest, TResponse>(this.connectorFactory.createConnector(clientSocket), this.channelHandlerFactory.createChannelHandler());
	}
}
