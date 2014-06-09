package framework.channels;


public class TransformingChannelHandler<TRequest, TResponse> implements IChannelHandler<TRequest, TResponse>
{
	// dependencies
	private final ITransformer<TRequest, TResponse> channelTransformer;
	private final IChannelHandler<RequestBag, ResponseBag> underlyingChannelHandler;

	public TransformingChannelHandler(ITransformer<TRequest, TResponse> channelTransformer, IChannelHandler<RequestBag, ResponseBag> underlyingChannelHandler)
	{
		// inject dependencies
		if (channelTransformer == null)
		{
			throw new NullPointerException("channelTransformer is null");
		}

		if (underlyingChannelHandler == null)
		{
			throw new NullPointerException("underlyingChannelHandler is null");
		}

		this.channelTransformer = channelTransformer;
		this.underlyingChannelHandler = underlyingChannelHandler;
	}
	
	@Override
	public TResponse handle(TRequest request)
	{
		if (request == null)
		{
			throw new NullPointerException("request is null");
		}

		try
		{
			// transform TRequest to normalized message format
			RequestBag requestBag = this.channelTransformer.transformRequest(request);
			
			// invoke underlying IChannelHandler with normalized message
			ResponseBag responseBag = this.underlyingChannelHandler.handle(requestBag);
			
			// transform normalized response to the target Response type
			return this.channelTransformer.transformResponse(responseBag);
		}
		catch (Exception e)
		{
			return this.channelTransformer.transformError(e);
		}
	}
}
