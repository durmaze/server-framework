package framework.server.diagnostics;

import java.util.HashMap;
import java.util.Map;

public class ServerInspection implements IInspection
{
	// dependencies & invariants
	private final long inspectionTime;
	private final String serverName;
	private final Map<Class<?>, IInspection> inspectionMap;
	
	public ServerInspection(long inspectionTime, String serverName)
	{
		// inject dependency
		if (inspectionTime <= 0L)
		{
			throw new IllegalArgumentException("inspectionTime must be positive");
		}
		
		if (serverName == null)
		{
			throw new NullPointerException("serverName is null");
		}

		this.inspectionTime = inspectionTime;
		this.serverName = serverName;
		
		this.inspectionMap = new HashMap<Class<?>, IInspection>();
	}

	@Override
	public long getInspectionTime()
	{
		return this.inspectionTime;
	}

	public String getServerName()
	{
		return this.serverName;
	}

	public Map<Class<?>, IInspection> getInspectionMap()
	{
		return this.inspectionMap;
	}

	public void addInspection(IInspection inspection)
	{
		if (inspection == null)
		{
			throw new NullPointerException("inspection is null");
		}
		
		this.getInspectionMap().put(inspection.getClass(), inspection);
	}
	
	public void removeInspection(IInspection inspection)
	{
		if (inspection == null)
		{
			throw new NullPointerException("inspection is null");
		}
		
		this.getInspectionMap().remove(inspection.getClass());
	}
}
