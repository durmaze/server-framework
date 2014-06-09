package framework.commanding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChainingCommandValidatorFactory<TCommand extends ICommand> implements ICommandValidatorFactory<TCommand>
{
	// dependency
	private final List<ICommandValidatorFactory<TCommand>> commandValidatorFactories;

	public ChainingCommandValidatorFactory(List<ICommandValidatorFactory<TCommand>> commandValidatorFactories)
	{
		// inject dependency
		if (commandValidatorFactories == null || commandValidatorFactories.size() == 0)
		{
			throw new NullPointerException("commandValidatorFactories is null or empty");
		}

		this.commandValidatorFactories = commandValidatorFactories;
	}
	
	@SafeVarargs
	public ChainingCommandValidatorFactory(ICommandValidatorFactory<TCommand>... commandValidatorFactories)
	{
		// inject dependency
		if (commandValidatorFactories == null || commandValidatorFactories.length == 0)
		{
			throw new NullPointerException("commandValidatorFactories is null or empty");
		}
		
		this.commandValidatorFactories = Arrays.asList(commandValidatorFactories);
	}
	
	@Override
	public ICommandValidator<TCommand> createCommandValidator()
	{
		List<ICommandValidator<TCommand>> commandValidators = new ArrayList<ICommandValidator<TCommand>>();
		
		for (ICommandValidatorFactory<TCommand> commandValidatorFactory : this.commandValidatorFactories)
		{
			commandValidators.add(commandValidatorFactory.createCommandValidator());
		}

		return new ChainingCommandValidator<TCommand>(commandValidators);
	}
}
