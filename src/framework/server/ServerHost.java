package framework.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import framework.concurrency.ServerThreadFactory;
import framework.exception.ServerException;

public class ServerHost implements IServerHost
{
	// dependency
	private final String hostName;
	private final Map<String, IServer> serverMap;
	
	private ExecutorService executorService;

	public ServerHost(String hostName)
	{
		// inject dependency
		if (hostName == null || hostName.isEmpty())
		{
			throw new NullPointerException("hostName is null or empty");
		}

		this.hostName = hostName;
		this.serverMap = new HashMap<String, IServer>();
	}
	
	@Override
	public List<IServer> getAllServers()
	{
		return new ArrayList<IServer>(this.serverMap.values());
	}

	@Override
	public IServer getServerByName(String serverName)
	{
		return this.serverMap.containsKey(serverName) ? this.serverMap.get(serverName) : null;
	}

	@Override
	public void addServer(IServer server)
	{
		this.serverMap.put(server.getName(), server);
	}

	@Override
	public void removeServer(String serverName)
	{
		if (this.serverMap.containsKey(serverName))
		{
			this.serverMap.remove(serverName);
		}
	}

	@Override
	public void open()
	{
		if (this.serverMap.isEmpty())
		{
			throw new IllegalStateException("No server is hosted. ServerHost cannot be opened for service");
		}
		
		List<IServer> servers = getAllServers();

		List<String> serverNames = new ArrayList<String>();
		
		for (IServer server : servers)
		{
			serverNames.add(server.getName());
		}
		
		this.executorService = Executors.newFixedThreadPool(servers.size(), new ServerThreadFactory(this.hostName + "-ServerPool", serverNames));
		
		try 
		{
			// wrap workers to callables
			List<Callable<Object>> callables = new ArrayList<Callable<Object>>();
			
			for (IServer server : servers)
			{
				callables.add(Servers.asCallable(server));
			}

			List<Future<Object>> futures = this.executorService.invokeAll(callables);
			
			// get results so that any ExecutionExcepition gets thrown
			for (Future<Object> future : futures)
			{
				future.get();
			}
		} 
		catch (Exception e) 
		{	
			throw new ServerException(e);
		}
		finally
		{
			this.executorService.shutdown();
		}
	}

	@Override
	public void close()
	{
		if (this.serverMap.isEmpty())
		{
			throw new IllegalStateException("No server is hosted. ServerHost cannot be closed");
		}

		for (IServer server : getAllServers())
		{
			server.stop();
		}
	}

	@Override
	public void beginOpen()
	{
		Runnable runnable = new Runnable()  
		{
			@Override
			public void run()
			{
				open();
			} 
		};
									
		Thread thread = new Thread(runnable, "ServerHost-AsyncOpen");
		thread.start();
	}

	@Override
	public void beginClose()
	{
		Runnable runnable = new Runnable()  
		{
			@Override
			public void run()
			{
				close();
			} 
		};
									
		Thread thread = new Thread(runnable, "ServerHost-AsyncClose");
		thread.start();
	}

	@Override
	public void beginClose(final long mseconds)
	{
		Runnable runnable = new Runnable()  
		{
			@Override
			public void run()
			{
				try
				{
					Thread.sleep(mseconds);
				}
				catch (Exception ignored) { }

				close();
			} 
		};
									
		Thread thread = new Thread(runnable, "ServerHost-AsyncClose");
		thread.start();
	}
}
