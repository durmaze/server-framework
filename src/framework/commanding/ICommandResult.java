package framework.commanding;

public interface ICommandResult<TResult>
{
	public boolean isSuccessful();
	public TResult getResult();
	public String getErrorMessage();
}
