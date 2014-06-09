package framework.server.diagnostics;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadRepository implements IThreadRepository
{
	private Map<String, ThreadRuntimeInfo> threadMap = new ConcurrentHashMap<String, ThreadRuntimeInfo>();	// inspected threads

	@Override
	public Map<String, ThreadRuntimeInfo> getAllThreads()
	{
		return getAllThreads(false);
	}
	
	@Override
	public Map<String, ThreadRuntimeInfo> getAllThreads(boolean checkValidity)
	{
		if (checkValidity)
		{
			removeInactiveThreads();
		}

		return new HashMap<String, ThreadRuntimeInfo>(this.threadMap);
	}

	@Override
	public void updateThreadRuntimeInfo(ThreadStateInfo stateInfo)
	{
		updateThreadRuntimeInfo(new ThreadRuntimeInfo(Thread.currentThread().getName(), stateInfo));
	}
	
	@Override
	public void updateThreadRuntimeInfo(ThreadRuntimeInfo threadRuntimeInfo)
	{
		this.threadMap.put(threadRuntimeInfo.getThreadName(), threadRuntimeInfo);
	}

	@Override
	public void clearThreadRuntimeInfo()
	{
		String threadName = Thread.currentThread().getName();
		
		if (this.threadMap.containsKey(threadName))
		{
			this.threadMap.put(threadName, null);
		}
	}
	
	private void removeInactiveThreads()
	{
		ThreadLister threadLister = new ThreadLister();
		
		// only search for threads belonging to the current thread group in order to narrow down the search
		Map<String, Thread> activeThreads = threadLister.getThreadsByGroup(Thread.currentThread().getThreadGroup());
		
		// if thread is not active, remove it from threadMap
		for (String threadName : this.threadMap.keySet())
		{
			if (!activeThreads.containsKey(threadName))
			{
				this.threadMap.remove(threadName);
			}
		}
	}
}