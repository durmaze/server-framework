package framework.channels;


public class TransformingChannelHandlerFactory<TRequest, TResponse> implements IChannelHandlerFactory<TRequest, TResponse>
{
	// dependencies
	private final ITransformerFactory<TRequest, TResponse> channelTransformerFactory;
	private final IChannelHandlerFactory<RequestBag, ResponseBag> underlyingChannelHandlerFactory;

	public TransformingChannelHandlerFactory(ITransformerFactory<TRequest, TResponse> channelTransformerFactory, IChannelHandlerFactory<RequestBag, ResponseBag> underlyingChannelHandlerFactory)
	{
		// inject dependencies
		if (channelTransformerFactory == null)
		{
			throw new NullPointerException("channelTransformerFactory is null");
		}

		if (underlyingChannelHandlerFactory == null)
		{
			throw new NullPointerException("underlyingChannelHandlerFactory is null");
		}

		this.channelTransformerFactory = channelTransformerFactory;
		this.underlyingChannelHandlerFactory = underlyingChannelHandlerFactory;
	}
	
	@Override
	public IChannelHandler<TRequest, TResponse> createChannelHandler()
	{
		return new TransformingChannelHandler<TRequest, TResponse>(this.channelTransformerFactory.createTransformer(), this.underlyingChannelHandlerFactory.createChannelHandler());
	}
}
