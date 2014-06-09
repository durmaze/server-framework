package framework.exception;

import java.text.MessageFormat;

public class ExceptionCode
{
	private final String key;
	private final String messageTemplate;
	
	public ExceptionCode(String key, String messageTemplate)
	{
		this.key = key;
		this.messageTemplate = messageTemplate;
	}

	public String toString(Object... messageTemplateArgs)
	{
		try
		{
			MessageFormat messageFormat = new MessageFormat(this.messageTemplate);
			
			return messageFormat.format(messageTemplateArgs);
		}
		catch (Exception e)
		{
			return this.key;
		}
	}
	
	public String toString()
	{
		Object[] parameters = null;
		return toString(parameters);
	}
}

