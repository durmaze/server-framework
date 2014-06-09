package framework.server;


public class AsyncServer implements IServer
{
	// dependency
	private final IServer underlyingServer;
	
	public AsyncServer(IServer underlyingServer)
	{
		// inject dependency
		if (underlyingServer == null)
		{
			throw new NullPointerException("underlyingServer is null");
		}

		this.underlyingServer = underlyingServer;
	}

	public String getName()
	{
		return this.underlyingServer.getName();
	}

	public void start()
	{
		Runnable runnable = new Runnable()  
		{
			@Override
			public void run()
			{
				underlyingServer.start();
			} 
		};
									
		Thread thread = new Thread(runnable, getName() + "-Async");
		thread.start();
	}

	public boolean stop()
	{
		return this.underlyingServer.stop();
		
//		boolean isUnderlyingServerStopped = this.underlyingServer.stop();
//
//		if (this.asyncExecutor != null && !this.asyncExecutor.isShutdown())
//		{
//			this.asyncExecutor.shutdown();
//		}
//
//		return isUnderlyingServerStopped && this.asyncExecutor.isShutdown();
	}
}
