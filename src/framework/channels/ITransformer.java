package framework.channels;


public interface ITransformer<TRequest, TResponse>
{
	public RequestBag transformRequest(TRequest request);
	public TResponse transformResponse(ResponseBag responseBag);
	public TResponse transformError(Throwable throwable);
}
