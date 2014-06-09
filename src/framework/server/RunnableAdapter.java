package framework.server;

public class RunnableAdapter implements Runnable
{
	// dependency
	private final IServer server;

	public RunnableAdapter(IServer server)
	{
		// inject dependency
		if (server == null)
		{
			throw new NullPointerException("server is null");
		}

		this.server = server;
	}

	public IServer getServer()
	{
		return server;
	}

	@Override
	public void run()
	{
		this.server.start();
	}
}
