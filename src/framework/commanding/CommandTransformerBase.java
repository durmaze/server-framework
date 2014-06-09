package framework.commanding;

import framework.channels.ResponseBag;
import framework.exception.ExceptionCodes;
import framework.exception.ServerException;

public abstract class CommandTransformerBase implements ICommandTransformer
{
	@Override
	public <TResult> ResponseBag transformResult(ICommandResult<TResult> commandResult)
	{
		if (commandResult instanceof CommandResult)
		{
			return transformResult((CommandResult) commandResult);
		}
		
		if (commandResult instanceof CommandErrorResult)
		{
			return transformErrorResult((CommandErrorResult) commandResult);
		}
		
		if (commandResult instanceof CommandContentResult)
		{
			return transformContentResult((CommandContentResult<TResult>) commandResult);
		}

		throw new ServerException(ExceptionCodes.COMMANDING_UNSUPPORTED_COMMANDRESULT, commandResult.getClass().getName(), getClass().getName());
		
//		return commandResult.isSuccessful() ? ResponseBag.createSuccessResponse() : ResponseBag.createErrorResponse(commandResult.getErrorMessage());
	}

	protected ResponseBag transformResult(CommandResult commandResult)
	{
		if (commandResult.isSuccessful())
		{
			ResponseBag successResponseBag = ResponseBag.createSuccessResponse();
			
			return transformSuccessResult(commandResult, successResponseBag);
		}
		else
		{
			return (commandResult.getResult() == null) ? ResponseBag.createErrorResponse(commandResult.getErrorMessage()) : ResponseBag.createErrorResponse(commandResult.getResult());
		}
	}
	
	protected ResponseBag transformSuccessResult(CommandResult commandResult, ResponseBag responseBag)
	{
		return responseBag;
	}

	protected ResponseBag transformErrorResult(CommandErrorResult commandResult)
	{
		return ResponseBag.createErrorResponse(commandResult.getErrorMessage());
	}
	
	protected <TResult> ResponseBag transformContentResult(CommandContentResult<TResult> commandResult)
	{
		if (commandResult.isSuccessful())
		{
			ResponseBag successResponseBag = ResponseBag.createSuccessResponse();
			
			return transformContentSuccessResult(commandResult, successResponseBag);
		}
		else
		{
			return ResponseBag.createErrorResponse(commandResult.getErrorMessage());
		}
		
//		throw new ServerException(ExceptionCodes.COMMANDING_CONTENT_TRANSFORMATION_NOT_IMPLEMENTED);
	}
	
	protected <TResult> ResponseBag transformContentSuccessResult(CommandContentResult<TResult> commandResult, ResponseBag responseBag)
	{
		throw new ServerException(ExceptionCodes.COMMANDING_CONTENT_TRANSFORMATION_NOT_IMPLEMENTED);
	}
}
