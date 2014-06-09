package framework.bag.exception;

public class BagKeyNotFoundException extends Exception
{
	private static final long serialVersionUID = -1300363644177616467L;

	public BagKeyNotFoundException()
	{
		super();
	}

	public BagKeyNotFoundException(String bagKey)
	{
		super(bagKey + " not found");
	}
}
