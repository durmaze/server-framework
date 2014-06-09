package framework.commanding;

public interface ICommandValidatorFactory<TCommand extends ICommand>
{
	public ICommandValidator<TCommand> createCommandValidator();
}
