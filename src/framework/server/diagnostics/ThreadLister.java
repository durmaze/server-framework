package framework.server.diagnostics;

import java.util.HashMap;
import java.util.Map;

public class ThreadLister
{
	public Map<String, Thread> getAllThreads()
	{
		Map<String, Thread> threadMap = new HashMap<String, Thread>();
				
		populate(findRootThreadGroup(), threadMap);
				
		return threadMap;
	}

	public Map<String, Thread> getThreadsByGroup(ThreadGroup threadGroup)
	{
		if (threadGroup == null)
		{
			throw new NullPointerException("threadGroup is null");
		}
		
		Map<String, Thread> threadMap = new HashMap<String, Thread>();
		
		populate(threadGroup, threadMap);
		
		return threadMap;
	}

	private void populate(ThreadGroup threadGroup, Map<String, Thread> threadMap)
	{
		if (threadGroup != null)
		{
			int activeThreadCount = threadGroup.activeCount();
			int activeGroupCount = threadGroup.activeGroupCount();
		
			Thread[] threads = new Thread[activeThreadCount];
			ThreadGroup[] threadGroups = new ThreadGroup[activeGroupCount];
		
			threadGroup.enumerate(threads, false);
			threadGroup.enumerate(threadGroups, false);
		
			for (int i = 0; i < activeThreadCount; i++)
			{
				if (threads[i] != null)
				{
					threadMap.put(threads[i].getName(), threads[i]);
				}
			}
			
			for (int i = 0; i < activeGroupCount; i++)
			{
				if (threadGroups[i] != null)
				{
					populate(threadGroups[i], threadMap);
				}
			}
		}
	}

	private ThreadGroup findRootThreadGroup()
	{
		ThreadGroup rootThreadGroup = Thread.currentThread().getThreadGroup();
		ThreadGroup parent = rootThreadGroup.getParent();
		
		while (parent != null)
		{
			rootThreadGroup = parent;
			parent = parent.getParent();
		}
		
		return rootThreadGroup;
	}
}
