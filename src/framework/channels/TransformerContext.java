package framework.channels;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TransformerContext
{
	private final Map<String, String> map = new HashMap<String, String>();

	public boolean containsKey(Object key)
	{
		return this.map.containsKey(key);
	}

	public String get(String key)
	{
		return this.map.get(key);
	}

	public String put(String key, String value)
	{
		return this.map.put(key, value);
	}

	public Set<Entry<String, String>> entrySet()
	{
		return this.map.entrySet();
	}
}
