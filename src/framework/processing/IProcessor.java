package framework.processing;


public interface IProcessor
{
	public void process();
	public void shutdown();
	public boolean isShutdown();
}
