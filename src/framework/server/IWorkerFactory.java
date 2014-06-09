package framework.server;

import java.net.Socket;

public interface IWorkerFactory
{
	public IWorker createWorker(Socket clientSocket);
}
