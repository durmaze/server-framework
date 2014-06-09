package framework.server;


public abstract class ServerBase implements IServer
{
	// dependencies
	protected final String serverName;
	protected final int serverPort;
	
//	private ExecutorService asyncExecutor;

	public ServerBase(String serverName, int serverPort)
	{
		// inject dependencies
		if (serverName == null)
		{
			throw new NullPointerException("serverName is null");
		}
		
		if (!isValidPortNumber(serverPort))
		{
			throw new IllegalArgumentException("serverPort is not a valid port number.");
		}

		this.serverName = serverName;
		this.serverPort = serverPort;
	}

	@Override
	public String getName()
	{
		return this.serverName;
	}

//	@Override
//	public void startAsync()
//	{
//		this.asyncExecutor = Executors.newSingleThreadExecutor(new ThreadFactory()
//		{
//			@Override
//			public Thread newThread(Runnable r)
//			{
//				return new Thread(serverName + "AsyncExecutor");
//			}
//		});
//		
//		this.asyncExecutor.execute(new Runnable()  
//		{
//			@Override
//			public void run()
//			{
//				start();
//			}
//		});
//	}
	
//	@Override
//	public boolean stop()
//	{
//		if (this.asyncExecutor != null && !this.asyncExecutor.isShutdown())
//		{
//			this.asyncExecutor.shutdown();
//		}
//		
//		return true;
//	}

	private boolean isValidPortNumber(int serverport)
	{
		return serverport >= 1024; 
	}
}
