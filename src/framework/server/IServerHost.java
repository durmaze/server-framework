package framework.server;

import java.util.List;

public interface IServerHost
{
	public List<IServer> getAllServers();
	public IServer getServerByName(String serverName);
	public void addServer(IServer server);
	public void removeServer(String serverName);
	
	public void open();
	public void beginOpen();
	public void close();
	public void beginClose();
	public void beginClose(long mseconds);
}
