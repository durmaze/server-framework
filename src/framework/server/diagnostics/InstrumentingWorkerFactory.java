package framework.server.diagnostics;

import java.net.Socket;

import framework.server.IWorker;
import framework.server.IWorkerFactory;

public class InstrumentingWorkerFactory implements IWorkerFactory
{
	// dependencies
	private final IWorkerFactory underlyingWorkerFactory;
	private final IThreadRepository threadRepository;

	public InstrumentingWorkerFactory(IWorkerFactory underlyingWorkerFactory, IThreadRepository threadRepository)
	{
		// inject dependencies
		if (underlyingWorkerFactory == null)
		{
			throw new NullPointerException("underlyingWorkerFactory is null");
		}

		if (threadRepository == null)
		{
			throw new NullPointerException("threadRepository is null");
		}

		this.underlyingWorkerFactory = underlyingWorkerFactory;
		this.threadRepository = threadRepository;
	}
	
	@Override
	public IWorker createWorker(Socket clientSocket)
	{
		return new InstrumentingWorker(this.underlyingWorkerFactory.createWorker(clientSocket), this.threadRepository);
	}
}
