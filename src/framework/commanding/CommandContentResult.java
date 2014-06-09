package framework.commanding;

public class CommandContentResult<TResult> implements ICommandResult<TResult>
{
	// dependency
	private final TResult result;
//	private final boolean isSuccessful;
//	private final String errorMessage;

	public CommandContentResult(TResult result)
	{
		if (result == null)
		{
			throw new NullPointerException("result is null");
		}
		
		if (Exception.class.isInstance(result))
		{
			throw new IllegalArgumentException("result object must be a non-exception type");
		}
		
		this.result = result;
//		this.isSuccessful = true;
//		this.errorMessage = null;
	}
	
//	public CommandResult(boolean isSuccessful)
//	{
//		this.isSuccessful = isSuccessful;
//		this.result = null;
//		this.errorMessage = null;
//	}
	
//	public CommandResult(String errorMessage)
//	{
//		if (errorMessage == null)
//		{
//			throw new NullPointerException("errorMessage is null");
//		}
//		
//		this.isSuccessful = false;
//		this.result = null;
//		this.errorMessage = errorMessage;
//	}
	
//	public CommandResult(Exception ex)
//	{
//		if (ex == null)
//		{
//			throw new NullPointerException("ex is null");
//		}
//		
//		this.isSuccessful = false;
//		this.result = null;
//		this.errorMessage = (ex.getCause() != null) ? ex.getMessage() + " (Cause: " + ex.getCause().getMessage().trim() + ")" : ex.getMessage();
//	}

	@Override
	public boolean isSuccessful()
	{
		return true;
//		return this.isSuccessful;
	}

	@Override
	public String getErrorMessage()
	{
		return null;
//		return this.errorMessage;
	}

	@Override
	public TResult getResult()
	{
		return this.result;
	}
}
