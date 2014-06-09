package framework.server.diagnostics;

import framework.server.IWorker;

public class InstrumentingWorker implements IWorker
{
	// dependencies
	private final IWorker underlyingWorker;
	private final IThreadRepository threadRepository;	// LATER ileride say� artarsa Callback'e �evir

	public InstrumentingWorker(IWorker underlyingWorker, IThreadRepository threadRepository)
	{
		// inject dependencies
		if (underlyingWorker == null)
		{
			throw new NullPointerException("underlyingWorker is null");
		}

		if (threadRepository == null)
		{
			throw new NullPointerException("threadRepository is null");
		}

		this.underlyingWorker = underlyingWorker;
		this.threadRepository = threadRepository;
	}
	
	@Override
	public void run()
	{
		try
		{
			this.threadRepository.updateThreadRuntimeInfo(new ThreadStateInfo("RUNNING"));
			
			this.underlyingWorker.run();
		}
		finally
		{
			this.threadRepository.clearThreadRuntimeInfo();
		}
	}
}
