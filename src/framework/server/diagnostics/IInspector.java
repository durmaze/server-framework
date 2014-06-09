package framework.server.diagnostics;


public interface IInspector<TInspection extends IInspection>
{
	public TInspection inspect();
}