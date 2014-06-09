package framework.commanding;

import java.util.HashMap;
import java.util.Map;

import framework.exception.ExceptionCodes;
import framework.exception.ServerException;

public class ValidatingCommandChannel implements ICommandChannel
{
	// dependencies
	@SuppressWarnings("rawtypes")
	private final Map<String, ICommandHandlerFactory> commandMap;

	@SuppressWarnings("rawtypes")
	private final Map<String, ICommandValidatorFactory> validationMap;

	@SuppressWarnings("rawtypes")
	public ValidatingCommandChannel(Map<String, ICommandHandlerFactory> commandMap, Map<String, ICommandValidatorFactory> validationMap)
	{
		// inject dependency
		if (commandMap == null || commandMap.size() == 0)
		{
			throw new NullPointerException("commandMap is null or empty");
		}

		this.commandMap = commandMap;
		this.validationMap = validationMap != null ? validationMap : new HashMap<String, ICommandValidatorFactory>(); // validationMap is optional
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <TCommand extends ICommand> ICommandResult<?> submit(TCommand command)
	{
		if (this.commandMap.containsKey(command.getClass().getName()))
		{	
			// validate ICommand if it has an associated validator
			if (this.validationMap.containsKey(command.getClass().getName()))
			{
				// create ICommandValidator
				ICommandValidatorFactory<TCommand> commandValidatorFactory = this.validationMap.get(command.getClass().getName());
				ICommandValidator<TCommand> commandValidator = commandValidatorFactory.createCommandValidator();
				
				if (commandValidator == null)
				{
					throw new ServerException(ExceptionCodes.COMMANDING_CANNOT_INSTANTIATE_COMMANDVALIDATOR, command.getClass().getName(), commandValidatorFactory.getClass().getName());
				}

				// validate ICommand
				commandValidator.validate(command);
			}
			
			// create ICommandHandler
			ICommandHandlerFactory<TCommand> commandHandlerFactory = this.commandMap.get(command.getClass().getName());
			ICommandHandler<TCommand> commandHandler = commandHandlerFactory.createCommandHandler();
			
			if (commandHandler == null)
			{
				throw new ServerException(ExceptionCodes.COMMANDING_CANNOT_INSTANTIATE_COMMANDHANDLER, command.getClass().getName(), commandHandlerFactory.getClass().getName());	
			}
			
			// execute ICommand
			return commandHandler.execute(command);
		}
		
		throw new ServerException(ExceptionCodes.COMMANDING_UNKNOWN_COMMAND, command.getClass().getName());	
	}
}
