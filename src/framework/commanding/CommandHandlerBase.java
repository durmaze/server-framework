package framework.commanding;

public abstract class CommandHandlerBase<TCommand extends ICommand> implements ICommandHandler<TCommand>
{
	protected CommandResult successResult()
	{
		return new CommandResult(true);
	}
	
	protected CommandResult failureResult()
	{
		return new CommandResult(false);
	}
	
	protected CommandResult failureResult(String failureReason)
	{
		return new CommandResult(failureReason);
	}
	
	protected <TContent> CommandContentResult<TContent> contentResult(TContent content)
	{
		return new CommandContentResult<TContent>(content);
	}
	
	protected CommandErrorResult errorResult(Exception ex)
	{
		return new CommandErrorResult(ex);
	}
}
