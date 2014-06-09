package framework.commanding.logging;

import framework.commanding.ICommandTransformer;
import framework.commanding.ICommandTransformerFactory;

public class LoggingCommandTransformerFactory implements ICommandTransformerFactory
{
	// dependency
	private final ICommandTransformerFactory underlyingCommandTransformerFactory;

	public LoggingCommandTransformerFactory(ICommandTransformerFactory underlyingCommandTransformerFactory)
	{
		// inject dependency
		if (underlyingCommandTransformerFactory == null)
		{
			throw new NullPointerException("underlyingCommandTransformerFactory is null");
		}

		this.underlyingCommandTransformerFactory = underlyingCommandTransformerFactory;
	}
	
	@Override
	public ICommandTransformer createTransformer()
	{
		return new LoggingCommandTransformer(this.underlyingCommandTransformerFactory.createTransformer());
	}
}
