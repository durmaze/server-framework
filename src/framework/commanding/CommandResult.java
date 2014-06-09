package framework.commanding;

public class CommandResult implements ICommandResult<String>
{
	private final boolean isSuccessful;
	private final String failureReason;
	
	public CommandResult(boolean isSuccessful)
	{
		this.isSuccessful = isSuccessful;
		this.failureReason = null;
	}
	
	public CommandResult(String failureReason)
	{
		this.isSuccessful = false;
		this.failureReason = failureReason;
	}
	
	@Override
	public boolean isSuccessful()
	{
		return this.isSuccessful;
	}

	@Override
	public String getResult()
	{
		return this.failureReason;
	}

	@Override
	public String getErrorMessage()
	{
		return null;
	}
}
