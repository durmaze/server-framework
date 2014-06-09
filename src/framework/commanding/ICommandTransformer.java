package framework.commanding;

import framework.channels.RequestBag;
import framework.channels.ResponseBag;

public interface ICommandTransformer
{
	public ICommand transform(RequestBag requestBag);
	public <TResult> ResponseBag transformResult(ICommandResult<TResult> commandResult);
}

