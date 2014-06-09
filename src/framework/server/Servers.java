package framework.server;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;

import framework.processing.IProcessor;

public class Servers
{
	public static IServer createServer(String serverName, int serverPort, ExecutorService executorService, IWorkerFactory workerFactory)
	{
		return new Server(serverName, serverPort, executorService, workerFactory);
	}
	
	public static IServer createServer(String serverName, int serverPort, int threadPoolSize, IWorkerFactory workerFactory)
	{
		return new ThreadPooledServer(serverName, serverPort, threadPoolSize, workerFactory);
	}
	
	public static IServer createServer(String serverName, int serverPort, int threadPoolSize, String threadPoolName, IWorkerFactory workerFactory)
	{
		return new ThreadPooledServer(serverName, serverPort, threadPoolSize, threadPoolName, workerFactory);
	}
	
	public static IServer createServer(String serverName, int serverPort, int threadPoolSize, String threadPoolName, IWorkerFactory workerFactory, int executionQueueSize, RejectedExecutionHandler rejectedExecutionHandler)
	{
		return new ThreadPooledServer(serverName, serverPort, threadPoolSize, threadPoolName, workerFactory, executionQueueSize, rejectedExecutionHandler);
	}
	
	public static IServer createServer(String serverName, IProcessor processor)
	{
		return new ProcessingServer(serverName, processor);
	}
	
	public static Runnable asRunnable(IServer server)
	{
		return new RunnableAdapter(server);
	}
	
	public static Callable<Object> asCallable(IServer server)
	{
		return Executors.callable(new RunnableAdapter(server));
	}
}
