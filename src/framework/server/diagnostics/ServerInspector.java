package framework.server.diagnostics;

import java.util.Arrays;
import java.util.List;

import framework.server.IServer;


public class ServerInspector implements IInspector<ServerInspection>
{
	// dependencies
	private final IServer server;
	private final List<IInspector<?>> inspectors;
	
	public ServerInspector(IServer server, IInspector<?>... inspectors)
	{
		// inject dependencies
		if (server == null)
		{
			throw new NullPointerException("server is null");
		}
		
		if (inspectors == null || inspectors.length == 0)
		{
			throw new NullPointerException("inspectors is null or empty");
		}
		
		this.server = server;
		this.inspectors = Arrays.asList(inspectors);
	}

	public ServerInspector(IServer server, List<IInspector<?>> inspectors)
	{
		// inject dependencies
		if (server == null)
		{
			throw new NullPointerException("server is null");
		}
		
		if (inspectors == null || inspectors.isEmpty())
		{
			throw new NullPointerException("inspectors is null or empty");
		}

		this.server = server;
		this.inspectors = inspectors;
	}
	
	@Override
	public ServerInspection inspect()
	{
		// get inspection time
		long inspectionTime = System.currentTimeMillis();
		
		ServerInspection serverInspection = new ServerInspection(inspectionTime, this.server.getName());
		
		for (IInspector<?> inspector : this.inspectors)
		{
			IInspection inspection = inspector.inspect();
			
			serverInspection.addInspection(inspection);
		}
		
		return serverInspection;
	}
}
