package framework.commanding;

public interface ICommandChannel
{
	 public <TCommand extends ICommand> ICommandResult<?> submit(TCommand command);
}
