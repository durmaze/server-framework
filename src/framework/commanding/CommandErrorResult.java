package framework.commanding;

public class CommandErrorResult implements ICommandResult<Exception>
{
	private final Exception exception;
	private final String errorMessage;
	
	public CommandErrorResult(Exception ex)
	{
		if (ex == null)
		{
			throw new NullPointerException("ex is null");
		}
		
		this.exception = null;
		this.errorMessage = (ex.getCause() != null) ? ex.getMessage() + " (Cause: " + ex.getCause().getMessage().trim() + ")" : ex.getMessage();
	}
	
	@Override
	public boolean isSuccessful()
	{
		return false;
	}

	@Override
	public Exception getResult()
	{
		return this.exception;
	}

	@Override
	public String getErrorMessage()
	{
		return this.errorMessage;
	}
}
