package framework.channels;

public interface IChannelHandlerFactory<TRequest, TResponse>
{
	public IChannelHandler<TRequest, TResponse> createChannelHandler();
}
