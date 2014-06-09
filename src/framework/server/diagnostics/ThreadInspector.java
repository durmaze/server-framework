package framework.server.diagnostics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ThreadInspector implements IInspector<ThreadInspection>
{
	// dependency
	private final IThreadRepository threadRepository;

	public ThreadInspector(IThreadRepository threadRepository)
	{
		// inject dependency
		if (threadRepository == null)
		{
			throw new NullPointerException("threadRepository is null");
		}

		this.threadRepository = threadRepository;
	}
	
	@Override
	public ThreadInspection inspect()
	{
		// get inspection time
		long inspectionTime = System.currentTimeMillis();

		Map<String, ThreadRuntimeInfo> threads = this.threadRepository.getAllThreads(true);
		
		if (!threads.isEmpty())
		{
			List<ThreadInspectionResult> inspectionResults = new ArrayList<ThreadInspectionResult>();
			
			for (Entry<String, ThreadRuntimeInfo> threadEntry : threads.entrySet())
			{
				ThreadInspectionResult inspectionResult = null;
				
				String threadName = threadEntry.getKey();
				ThreadRuntimeInfo runtimeInfo = threadEntry.getValue();
				
				if (runtimeInfo != null)
				{
					// calculate elapsed times
					long executionElapsedTime = inspectionTime - runtimeInfo.getTimestamp();
					long stateElapsedTime = inspectionTime - runtimeInfo.getStateInfo().getTimestamp();
					
					inspectionResult = new ThreadInspectionResult(threadName, runtimeInfo.getStateInfo().getState(), executionElapsedTime, stateElapsedTime);
				}
				else
				{
					inspectionResult = new ThreadInspectionResult(threadName, "IDLE", 0L, 0L);
				}
				
				inspectionResults.add(inspectionResult);
			}
			
			return new ThreadInspection(inspectionTime, inspectionResults);
		}
		
		return new ThreadInspection(inspectionTime);
	}
}
