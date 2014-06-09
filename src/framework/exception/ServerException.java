package framework.exception;


public class ServerException extends RuntimeException
{
	private static final long serialVersionUID = 8332759373558182325L;

	private final ExceptionCode exceptionCode;
	private final Object[] messageTemplateArgs;
	
	public ServerException(String message)
	{
		super(message);
		
		this.exceptionCode = null;
		this.messageTemplateArgs = new Object[0];
	}
	
	public ServerException(Throwable cause)
	{
		super(cause);
		
		this.exceptionCode = null;
		this.messageTemplateArgs = new Object[0];
	}
	
	public ServerException(ExceptionCode exceptionCode)
	{
		if (exceptionCode == null)
		{
			throw new NullPointerException("exceptionCode is null");
		}

		this.exceptionCode = exceptionCode;
		
		this.messageTemplateArgs = new Object[0];
	}
	
	public ServerException(ExceptionCode exceptionCode, Object... messageTemplateArgs)
	{
		if (exceptionCode == null)
		{
			throw new NullPointerException("exceptionCode is null");
		}

		this.exceptionCode = exceptionCode;
		
		this.messageTemplateArgs = (messageTemplateArgs == null ? new Object[0] : messageTemplateArgs);
	}
	
	public ServerException(Throwable cause, ExceptionCode exceptionCode, Object... messageTemplateArgs)
	{
		// pass inner exception/error to base
		super(cause);
		
		if (exceptionCode == null)
		{
			throw new NullPointerException("exceptionCode is null");
		}

		this.exceptionCode = exceptionCode;
		
		this.messageTemplateArgs = (messageTemplateArgs == null ? new Object[0] : messageTemplateArgs);
	}

	@Override
	public String getMessage()
	{
		return (this.getExceptionCode() != null) ? this.getExceptionCode().toString(this.messageTemplateArgs) : super.getMessage();
	}

	public ExceptionCode getExceptionCode()
	{
		return this.exceptionCode;
	}
}
