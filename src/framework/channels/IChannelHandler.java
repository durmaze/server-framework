package framework.channels;

public interface IChannelHandler<TRequest, TResponse>
{
	public TResponse handle(TRequest request);
}
