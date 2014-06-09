package framework.channels;

public class RequestBag extends MessageBag
{
	private String commandName;

	public String getCommandName()
	{
		return commandName;
	}

	public void setCommandName(String commandName)
	{
		this.commandName = commandName;
	}
}
