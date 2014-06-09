package framework.server;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import framework.concurrency.ServerThreadFactory;

public class ThreadPooledServer extends Server
{
	public ThreadPooledServer(String serverName, int serverPort, int threadPoolSize, IWorkerFactory workerFactory)
	{
		super(serverName, serverPort, Executors.newFixedThreadPool(threadPoolSize), workerFactory);
	}
	
	public ThreadPooledServer(String serverName, int serverPort, int threadPoolSize, String threadPoolName, IWorkerFactory workerFactory)
	{
		super(serverName, serverPort, Executors.newFixedThreadPool(threadPoolSize, new ServerThreadFactory(threadPoolName)), workerFactory);
	}
	
	public ThreadPooledServer(String serverName, int serverPort, int threadPoolSize, String threadPoolName, IWorkerFactory workerFactory, int executionQueueSize, RejectedExecutionHandler rejectedExecutionHandler)
	{
		super(serverName, serverPort, new ThreadPoolExecutor(threadPoolSize, threadPoolSize, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(executionQueueSize), rejectedExecutionHandler), workerFactory);
	}
}
