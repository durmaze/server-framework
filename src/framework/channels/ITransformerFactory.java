package framework.channels;

public interface ITransformerFactory<TRequest, TResponse>
{
	public ITransformer<TRequest, TResponse> createTransformer();
	public ITransformer<TRequest, TResponse> createTransformer(TransformerContext transformerContext);
}
