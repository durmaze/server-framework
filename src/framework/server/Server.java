package framework.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import framework.exception.ExceptionCodes;
import framework.exception.ExceptionUtils;
import framework.exception.ServerException;

public class Server extends ServerBase
{
	// dependencies
	private final ExecutorService executorService;
	private final IWorkerFactory workerFactory;
	
	// variants
	protected ServerSocket serverSocket = null;
	protected boolean isStopped = false;
	protected Thread runningThread = null;
	
	// logger
	private final Logger logger = LoggerFactory.getLogger(Server.class);

	public Server(String serverName, int serverPort, ExecutorService executorService, IWorkerFactory workerFactory)
	{
		super(serverName, serverPort);

		// inject dependencies
		if (executorService == null)
		{
			throw new NullPointerException("executorService is null");
		}
		
		if (workerFactory == null)
		{
			throw new NullPointerException("workerFactory is null");
		}
		
		this.executorService = executorService;
		this.workerFactory = workerFactory;
	}

	@Override
	public void start()
	{
		run();
	}

	@Override
	public synchronized boolean stop()
	{
//		super.stop();
		
		if (this.isStopped() && this.executorService.isShutdown() && this.executorService.isTerminated())
		{
			return true;
		}
		else
		{
			this.isStopped = true;
			
			closeServerSocket();
			
			return false;
		}
	}

	private void run()
	{
		synchronized (this)
		{
			this.runningThread = Thread.currentThread();
		}
		
		// try to bind "this.serverPort"
		openServerSocket();
	
		//---------------------SERVER LOOP------------------// 
		
		while (!isStopped())
		{
			Socket clientSocket = null;
			
			try
			{
				clientSocket = this.serverSocket.accept(); 
			}
			catch (IOException e)
			{
				if (isStopped())
				{
					this.executorService.shutdown();
	
					this.logger.info("{} ({}) is successfully stopped. Exception: {}", new Object[] { this.getClass().getSimpleName(), this.serverName, e.getMessage() });
					
					return;
				}
	
				throw new ServerException(e, ExceptionCodes.SERVER_SOCKET_NEW_CONNECTION_ACCEPTANCE_ERROR);
			}
			
			try
			{
				this.executorService.execute(createWorker(clientSocket));
			}
			catch (Throwable e)
			{
				if (ExceptionUtils.containsType(e, Error.class))
				{
					this.logger.error("Unexpected error occurred in " + this.getClass().getSimpleName() + " (" + serverName + ")", e);
					stop();
				}
				else
				{
					this.logger.error("Unexpected exception occurred in " + this.getClass().getSimpleName() + " (" + serverName + ")", e);
				}
				
				// clientSocket might not be closed in case of unhandled exception or rejected execution exception
				closeClientSocketIfNecessary(clientSocket);	
			}
		}
		
		//---------------------SERVER LOOP------------------// 
		
		// close executor
		this.executorService.shutdown();
	
		this.logger.info("{} ({}) is successfully stopped.", this.getClass().getSimpleName(), this.serverName);
	}

	protected IWorker createWorker(Socket clientSocket)
	{
		return this.workerFactory.createWorker(clientSocket);
	}

	private synchronized boolean isStopped()
	{
		return this.isStopped;
	}

	private void openServerSocket()
	{
		try
		{
			this.serverSocket = new ServerSocket(this.serverPort);
			
			this.logger.info("{} ({}) started to listen on port: {}", new Object[] { this.getClass().getSimpleName(), this.serverName, this.serverPort });
		}
		catch (IOException e)
		{
			throw new ServerException(e, ExceptionCodes.SERVER_SOCKET_INITIALIZATION_ERROR, this.serverPort);
		}
	}
	
	private void closeServerSocket()
	{
		try
		{
			if (this.serverSocket != null)
			{
				this.serverSocket.close();
			}
		}
		catch (IOException e)
		{
			throw new ServerException(e, ExceptionCodes.SERVER_SOCKET_CLOSURE_ERROR);
		}
	}

	private void closeClientSocketIfNecessary(Socket clientSocket)
	{
		try
		{
			if (clientSocket != null && !clientSocket.isClosed())
			{
				clientSocket.close();
			}
		}
		catch (Exception e)
		{
			this.logger.warn("ClientSocket cannot be closed. Port: {}", clientSocket.getPort());
		}
	}
}
