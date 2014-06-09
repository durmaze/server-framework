package framework.server;

import framework.channels.IChannelHandler;
import framework.channels.IConnector;

public class ChannelWorker<TRequest, TResponse> implements IWorker
{
	// dependencies
	private final IConnector<TRequest, TResponse> connector;
	private final IChannelHandler<TRequest, TResponse> channelHandler;
	
	public ChannelWorker(IConnector<TRequest, TResponse> connector, IChannelHandler<TRequest, TResponse> channelHandler)
	{
		// inject dependencies
		if (connector == null)
		{
			throw new NullPointerException("connector is null");
		}

		if (channelHandler == null)
		{
			throw new NullPointerException("channelHandler is null");
		}

		this.connector = connector;
		this.channelHandler = channelHandler;
	}
	
	@Override
	public void run()
	{
		try
		{
			// receive request thru IConnector
			TRequest request = this.connector.receive();

			if (request != null)
			{
				// handle request
				TResponse response = this.channelHandler.handle(request);
				
				// return result to the client
				this.connector.dispatch(response);
			}
		}
		catch (Exception ex)
		{
			this.connector.dispatch(ex); 
		}
		finally
		{
			this.connector.close();
		}
	}
}
