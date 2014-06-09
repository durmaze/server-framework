package framework.commanding;

public interface ICommandHandlerFactory<TCommand extends ICommand>
{
	public ICommandHandler<TCommand> createCommandHandler();
}
