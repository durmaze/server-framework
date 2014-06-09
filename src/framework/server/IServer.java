package framework.server;

public interface IServer // extends Runnable
{
	public String getName();
	public void start();
	public boolean stop();
}
