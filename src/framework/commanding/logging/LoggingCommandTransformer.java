package framework.commanding.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import framework.channels.RequestBag;
import framework.channels.ResponseBag;
import framework.commanding.ICommand;
import framework.commanding.ICommandResult;
import framework.commanding.ICommandTransformer;
import framework.exception.ServerException;

public class LoggingCommandTransformer implements ICommandTransformer
{
	// logger
	private final Logger logger = LoggerFactory.getLogger(LoggingCommandTransformer.class);
	
	// dependencies & invariants
	private final ICommandTransformer underlyingCommandTransformer;
	private final String transformerClassName;

	public LoggingCommandTransformer(ICommandTransformer underlyingCommandTransformer)
	{
		// inject dependency
		if (underlyingCommandTransformer == null)
		{
			throw new NullPointerException("underlyingCommandTransformer is null");
		}

		this.underlyingCommandTransformer = underlyingCommandTransformer;
		this.transformerClassName = this.underlyingCommandTransformer.getClass().getName();
	}
	
	@Override
	public ICommand transform(RequestBag requestBag)
	{
		try 
		{
			this.logger.debug("Transforming RequestBag to ICommand using ICommandTransformer: {}.", this.transformerClassName);
			
			ICommand command = this.underlyingCommandTransformer.transform(requestBag);
			
			if (command != null)
			{
				this.logger.debug("RequestBag is successfully transformed to {}.", command.getClass().getSimpleName());
			}
			
			return command;
		} 
		catch (ServerException e) 
		{	
			this.logger.error("Exception in tranforming RequestBag to ICommand.", e); 
			throw e;
		}
	}

	@Override
	public <TResult> ResponseBag transformResult(ICommandResult<TResult> commandResult)
	{
		try 
		{
			this.logger.debug("Transforming ICommandResult to ResponseBag using ICommandTransformer: {}.", this.transformerClassName);

			ResponseBag responseBag = this.underlyingCommandTransformer.transformResult(commandResult);
			
			if (responseBag != null)
			{
				this.logger.debug("ICommandResult ({}) is successfully transformed to ResponseBag.", commandResult.getClass().getSimpleName());
			}
			
			return responseBag;
		} 
		catch (ServerException e) 
		{	
			this.logger.error("Exception in transforming ICommandResult (" + commandResult.getClass().getSimpleName() + ") to ResponseBag", e); 
			throw e;
		}
	}
}
