package framework.channels;

import java.net.Socket;

public interface IConnectorFactory<TRequest, TResponse>
{
	public IConnector<TRequest, TResponse> createConnector(Socket clientSocket); 
}
