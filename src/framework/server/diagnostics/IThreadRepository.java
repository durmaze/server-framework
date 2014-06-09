package framework.server.diagnostics;

import java.util.Map;

public interface IThreadRepository
{
	/**
	 * Returns a copy of the current threads
	 * 
	 * @return Runtime information of each thread
	 */
	public Map<String, ThreadRuntimeInfo> getAllThreads();

	/**
	 * Returns a copy of the current threads
	 * 
	 * @param checkValidity checks the validity of threads
	 * @return
	 */
	public Map<String, ThreadRuntimeInfo> getAllThreads(boolean checkValidity);

	/**
	 * Updates currently executing thread's state
	 * 
	 * @param stateInfo	new state
	 */
	public void updateThreadRuntimeInfo(ThreadStateInfo stateInfo);

	/**
	 * Updates thread's runtime info
	 * 
	 * @param threadRuntimeInfo new runtime info
	 */
	public void updateThreadRuntimeInfo(ThreadRuntimeInfo threadRuntimeInfo);

	/**
	 * Clears thread's runtime info
	 */
	public void clearThreadRuntimeInfo();
}