package framework.commanding;

import framework.channels.IChannelHandler;
import framework.channels.IChannelHandlerFactory;
import framework.channels.RequestBag;
import framework.channels.ResponseBag;

public class CommandBasedChannelHandlerFactory implements IChannelHandlerFactory<RequestBag, ResponseBag>
{
	private final ICommandTransformerRepository commandTransformerRepository;
	private final ICommandChannel commandChannel;
	
	public CommandBasedChannelHandlerFactory(ICommandTransformerRepository commandTransformerRepository, ICommandChannel commandChannel)
	{
		// inject dependencies
		if (commandTransformerRepository == null)
		{
			throw new NullPointerException("commandTransformerRepository is null");
		}

		if (commandChannel == null)
		{
			throw new NullPointerException("commandChannel is null");
		}

		this.commandTransformerRepository = commandTransformerRepository;
		this.commandChannel = commandChannel;
	}
	
	@Override
	public IChannelHandler<RequestBag, ResponseBag> createChannelHandler()
	{
		return new CommandBasedChannelHandler(this.commandTransformerRepository, this.commandChannel);
	}
}
