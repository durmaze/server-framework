package framework.server.diagnostics;

import java.util.ArrayList;
import java.util.List;

public class ThreadInspection implements IInspection
{
	private final long inspectionTime;
	private final List<ThreadInspectionResult> inspectionResults;
	
	public ThreadInspection(long inspectionTime)
	{
		if (inspectionTime <= 0L)
		{
			throw new IllegalArgumentException("inspectionTime must be positive");
		}
		
		this.inspectionTime = inspectionTime;
		this.inspectionResults = new ArrayList<ThreadInspectionResult>();
	}
	
	public ThreadInspection(long inspectionTime, List<ThreadInspectionResult> inspectionResults)
	{
		if (inspectionTime <= 0L)
		{
			throw new IllegalArgumentException("inspectionTime must be positive");
		}
		
		this.inspectionTime = inspectionTime;

		if (inspectionResults != null && !inspectionResults.isEmpty())
		{
			this.inspectionResults = inspectionResults;
		}
		else
		{
			this.inspectionResults = new ArrayList<ThreadInspectionResult>();
		}
	}
	
	@Override
	public long getInspectionTime()
	{
		return this.inspectionTime;
	}

	public List<ThreadInspectionResult> getInspectionResults()
	{
		return this.inspectionResults;
	}
}
