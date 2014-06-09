package framework.commanding.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import framework.commanding.ICommand;
import framework.commanding.ICommandChannel;
import framework.commanding.ICommandResult;

public class LoggingCommandChannel implements ICommandChannel
{
	// logger
	private final Logger logger = LoggerFactory.getLogger(LoggingCommandChannel.class);

	// dependency
	private final ICommandChannel underlyingCommandChannel;

	public LoggingCommandChannel(ICommandChannel underlyingCommandChannel)
	{
		// inject dependency
		if (underlyingCommandChannel == null)
		{
			throw new NullPointerException("underlyingCommandChannel is null");
		}

		this.underlyingCommandChannel = underlyingCommandChannel;
	}
	
	@Override
	public <TCommand extends ICommand> ICommandResult<?> submit(TCommand command)
	{
		this.logger.debug("Sending Command to CommandChannel. CommandInfo: {}", command.getCommandInfo());
		
		ICommandResult<?> commandResult = this.underlyingCommandChannel.submit(command);
		
		this.logger.debug("Command result is received. Arguments => CommandName: {}, IsSuccessful: {}, ErrorMessage: {}", new Object [] 
			{
				command.getClass().getSimpleName(),
				commandResult.isSuccessful(),
				commandResult.getErrorMessage()
			});
		
		return commandResult;
	}
}
