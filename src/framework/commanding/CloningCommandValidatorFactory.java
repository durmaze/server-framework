package framework.commanding;

public class CloningCommandValidatorFactory<TCommand extends ICommand, TCommandValidator extends ICommandValidator<TCommand>> implements ICommandValidatorFactory<TCommand>
{
	// dependency
	private final ICloneable<TCommandValidator> cloneableCommandValidator;

	public CloningCommandValidatorFactory(ICloneable<TCommandValidator> cloneableCommandValidator)
	{
		// inject dependency
		if (cloneableCommandValidator == null)
		{
			throw new NullPointerException("cloneableCommandValidator is null");
		}
		
		this.cloneableCommandValidator = cloneableCommandValidator;
	}
	
	@Override
	public ICommandValidator<TCommand> createCommandValidator()
	{
		return this.cloneableCommandValidator.clone();
	}
}
