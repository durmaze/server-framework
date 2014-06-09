package framework.commanding;

public interface ICommandTransformerRepository
{
	public ICommandTransformer findTransformerByName(String commandName);
}
