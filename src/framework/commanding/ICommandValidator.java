package framework.commanding;

public interface ICommandValidator<TCommand extends ICommand>
{
	public void validate(TCommand command);
}
