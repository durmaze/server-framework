package framework.channels;

public interface IConnector<TRequest, TResponse>
{
	/**
	 * Receives request from socket
	 * @return returns read InputStream as TRequest
	 */
	public TRequest receive();
	
	/**
	 * Dispatches result to client
	 * @param response
	 */
	public void dispatch(TResponse response);
	
	/**
	 * Dispatches error result to client
	 * @param ex
	 */
	public void dispatch(Exception ex);
	
	/**
	 * All acquired resources must be closed (e.g. clientSocket, InputStream, OutputStream, etc.)
	 */
	public void close();
}

//public interface IConnector
//{
//	public <TRequest> TRequest receive();
//	public <TResponse> void dispatch(TResponse response);
//	public void close();
//}
