package framework.commanding;

public class CloningCommandTransformerFactory<TCommandTransformer extends ICommandTransformer> implements ICommandTransformerFactory
{
	// dependency
	private final ICloneable<TCommandTransformer> cloneableCommandTransformer;
	
	public CloningCommandTransformerFactory(ICloneable<TCommandTransformer> cloneableCommandTransformer)
	{
		// inject dependency
		if (cloneableCommandTransformer == null)
		{
			throw new NullPointerException("cloneableCommandTransformer is null");
		}
		
		this.cloneableCommandTransformer = cloneableCommandTransformer;
	}
	
	@Override
	public ICommandTransformer createTransformer()
	{
		return this.cloneableCommandTransformer.clone();
	}
}
