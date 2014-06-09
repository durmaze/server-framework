package framework.commanding;

import java.util.Map;

import framework.exception.ExceptionCodes;
import framework.exception.ServerException;

public class SimpleCommandChannel implements ICommandChannel
{
	// dependency
	@SuppressWarnings("rawtypes")
	private final Map<String, ICommandHandlerFactory> commandMap;

	@SuppressWarnings("rawtypes")
	public SimpleCommandChannel(Map<String, ICommandHandlerFactory> commandMap)
	{
		// inject dependency
		if (commandMap == null || commandMap.size() == 0)
		{
			throw new NullPointerException("commandMap is null or empty");
		}

		this.commandMap = commandMap;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <TCommand extends ICommand> ICommandResult<?> submit(TCommand command)
	{
		for (Map.Entry<String, ICommandHandlerFactory> commandMapEntry : this.commandMap.entrySet())
		{
			if (commandMapEntry.getKey().equals(command.getClass().getName()))
			{
				// create ICommandHandler
				ICommandHandlerFactory<TCommand> commandHandlerFactory = (ICommandHandlerFactory<TCommand>) commandMapEntry.getValue();
				ICommandHandler<TCommand> commandHandler = commandHandlerFactory.createCommandHandler();
				
				if (commandHandler == null)
				{
					throw new ServerException(ExceptionCodes.COMMANDING_CANNOT_INSTANTIATE_COMMANDHANDLER, command.getClass().getName(), commandHandlerFactory.getClass().getName());	
				}
				
				// execute ICommand
				return commandHandler.execute(command);
			}
		}
		
		throw new ServerException(ExceptionCodes.COMMANDING_UNKNOWN_COMMAND, command.getClass().getName());
	}
}
