package framework.concurrency;


public class ServerThread extends Thread
{
//	private static final AtomicInteger alive = new AtomicInteger();

//	public static int getThreadsAlive() { return alive.get(); }

	public ServerThread(Runnable runnable, String poolName)
	{
		super(runnable, poolName);
	}

	@Override
	public void run()
	{
		try
		{
//			alive.incrementAndGet();

			super.run();
		}
		finally
		{
//			alive.decrementAndGet();
		}
	}
}
