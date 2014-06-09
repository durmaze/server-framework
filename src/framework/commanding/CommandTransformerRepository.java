package framework.commanding;

import java.util.Map;

import framework.exception.ExceptionCodes;
import framework.exception.ServerException;

public class CommandTransformerRepository implements ICommandTransformerRepository
{
	// dependency
	private final Map<String, ICommandTransformerFactory> commandTransformerFactoryMap;

	public CommandTransformerRepository(Map<String, ICommandTransformerFactory> commandTransformerFactoryMap)
	{
		// inject dependency
		if (commandTransformerFactoryMap == null)
		{
			throw new NullPointerException("commandTransformerFactoryMap is null");
		}

		this.commandTransformerFactoryMap = commandTransformerFactoryMap;
	}
	
	@Override
	public ICommandTransformer findTransformerByName(String requestName)
	{
		if (!this.commandTransformerFactoryMap.containsKey(requestName))
		{
			throw new ServerException(ExceptionCodes.COMMANDING_NO_COMMANDTRANSFORMERFACTORY_FOUND, requestName);
		}
		
		// create a new instance of ITransformer
		ICommandTransformerFactory commandTransformerFactory = this.commandTransformerFactoryMap.get(requestName);
		ICommandTransformer commandTransformer = commandTransformerFactory.createTransformer();
		
		return commandTransformer;
	}
}
