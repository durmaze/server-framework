package framework.commanding;

public interface ICommandHandler<TCommand extends ICommand>
{
	public ICommandResult<?> execute(TCommand command);
}
