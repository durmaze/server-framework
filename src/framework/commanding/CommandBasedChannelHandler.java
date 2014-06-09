package framework.commanding;

import framework.channels.IChannelHandler;
import framework.channels.RequestBag;
import framework.channels.ResponseBag;
import framework.exception.ExceptionCodes;
import framework.exception.ServerException;

public class CommandBasedChannelHandler implements IChannelHandler<RequestBag, ResponseBag>
{
	private final ICommandTransformerRepository commandTransformerRepository;
	private final ICommandChannel commandChannel;
	
	public CommandBasedChannelHandler(ICommandTransformerRepository commandTransformerRepository, ICommandChannel commandChannel)
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
	public ResponseBag handle(RequestBag requestBag)
	{
		if (requestBag == null)
		{
			throw new NullPointerException("requestBag is null");
		}

		String commandName = requestBag.getCommandName();
		
		if (commandName == null)
		{
			throw new ServerException(ExceptionCodes.COMMANDING_NO_COMMAND_IDENTIFIER_FOUND);
		}
		
		// find ICommandTransformer for the Command
		ICommandTransformer commandTransformer = this.commandTransformerRepository.findTransformerByName(commandName); 
		
		if (commandTransformer == null)
		{
			throw new ServerException(ExceptionCodes.COMMANDING_NO_COMMANDTRANSFORMER_FOUND, commandName);
		}
		
		// transform normalized request to ICommand
		ICommand command = commandTransformer.transform(requestBag);
		
		// send ICommand to the ICommandChannel
		ICommandResult<?> commandResult = this.commandChannel.submit(command);
		
		// transform ICommandResult to normalized response
		return commandTransformer.transformResult(commandResult); 
	}
}
