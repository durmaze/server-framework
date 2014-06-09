package framework.commanding;

import java.util.Arrays;
import java.util.List;

public class ChainingCommandValidator<TCommand extends ICommand> implements ICommandValidator<TCommand>
{
	// dependency
	private final List<ICommandValidator<TCommand>> commandValidators;

	@SafeVarargs
	public ChainingCommandValidator(ICommandValidator<TCommand>... commandValidators)
	{
		// inject dependency
		if (commandValidators == null || commandValidators.length == 0)
		{
			throw new NullPointerException("commandValidators is null or empty");
		}
		
		this.commandValidators = Arrays.asList(commandValidators);
	}
	
	public ChainingCommandValidator(List<ICommandValidator<TCommand>> commandValidators)
	{
		// inject dependency
		if (commandValidators == null || commandValidators.isEmpty())
		{
			throw new NullPointerException("commandValidators is null");
		}

		this.commandValidators = commandValidators;
	}
	
	@Override
	public void validate(TCommand command)
	{
		for (ICommandValidator<TCommand> commandValidator : this.commandValidators)
		{
			if (commandValidator != null)
			{
				commandValidator.validate(command);
			}
		}
	}
}
