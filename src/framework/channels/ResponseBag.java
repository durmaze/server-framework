package framework.channels;


public class ResponseBag extends MessageBag
{
	// constants
	private static final String SUCCESS_RETURNCODE = "0";
	private static final String ERROR_RETURNCODE = "70134";

	private final String returnCode;
	private final String errorMessage;
	
	public static ResponseBag createSuccessResponse()
	{
		return new ResponseBag(SUCCESS_RETURNCODE);
	}

	public static ResponseBag createErrorResponse(String errorMessage)
	{
		return new ResponseBag(ERROR_RETURNCODE, errorMessage);
	}
	
	public static ResponseBag createErrorResponse(String errorCode, String errorMessage)
	{
		if (errorCode.equals(SUCCESS_RETURNCODE))
		{
			throw new IllegalArgumentException("errorCode cannot be equal to success return code. errorCode: " + errorCode);
		}
		
		return new ResponseBag(errorCode, errorMessage);
	}
	
	public static ResponseBag createFromBag(ResponseBag aResponseBag)
	{
		if (aResponseBag == null)
		{
			throw new NullPointerException("responseBag is null");
		}
		
		ResponseBag responseBag = aResponseBag.getReturnCode().equals(SUCCESS_RETURNCODE) ? createSuccessResponse() : createErrorResponse(aResponseBag.getReturnCode(), aResponseBag.getErrorMessage());
		aResponseBag.copyTo(responseBag);
		
		return responseBag;
	}

	private ResponseBag(String returnCode) 
	{ 
		if (returnCode == null)
		{
			throw new NullPointerException("returnCode is null");
		}
		
		this.returnCode = returnCode;
		this.errorMessage = null;
	}
	
	private ResponseBag(String returnCode, String errorMessage)
	{
		if (returnCode == null)
		{
			throw new NullPointerException("returnCode is null");
		}
		
		this.returnCode = returnCode;
		this.errorMessage = errorMessage;
	}

	public String getReturnCode()
	{
		return returnCode;
	}

	public String getErrorMessage()
	{
		return errorMessage;
	}
}
