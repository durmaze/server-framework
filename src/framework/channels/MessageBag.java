package framework.channels;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import framework.bag.Bag;
import framework.bag.BagKey;
import framework.bag.BagValue;
import framework.bag.exception.BagKeyNotFoundException;

public abstract class MessageBag extends Bag
{
	private Map<String, Set<String>> columnsMap = new HashMap<String, Set<String>>();

	@Override
	public boolean existsBagKey(String key)
	{
		boolean exists = false;
		
		Hashtable<String, BagValue> bagKeys = getAllElements();
		
		if (bagKeys.containsKey(key))
		{
			exists = true;
		}
		
		return exists;
	}

	public boolean existsBagKey(String table, int index)
	{
		try
		{
			get(table, index);
			return true;
		}
		catch (BagKeyNotFoundException ex)
		{
			return false;
		}
	}

	public boolean existsBagKey(String table, int index, String key)
	{
		try
		{
			get(table, index, key);
			return true;
		}
		catch (BagKeyNotFoundException ex)
		{
			return false;
		}
	}

	public String get(String strKey) throws BagKeyNotFoundException
	{
		return get(new BagKey(strKey)).toString();
	}

	public String get(String value, int index) throws BagKeyNotFoundException
	{
		if (index == -1)
		{
			return get(new BagKey(value)).toString();
		}
		else
		{
			return this.get(value + ":" + String.valueOf(index)).toString();
		}
	}

	public String get(String value, int index1, int index2) throws BagKeyNotFoundException
	{
		return this.get(value + ":" + String.valueOf(index1) + ":" + String.valueOf(index2)).toString();
	}

	public String get(String tableName, int index, String columnName) throws BagKeyNotFoundException
	{
		if (index == -1)
		{
			return this.get(columnName).toString();
		}

		return this.get(tableName + ":" + String.valueOf(index) + ":" + columnName).toString();
	}

	public int getDim(String sDim)
	{
		return this.getDimension(new BagKey(sDim));
	}

	public int getSize(String sSize)
	{
		return this.getSize(new BagKey(sSize));
	}

	public MessageBag put(String strKey, int firstIndex, int secondIndex, String value)
	{
		this.put(new BagKey(strKey, firstIndex, secondIndex), value);
		return this;
	}

	public MessageBag put(String strKey, int index, String value)
	{
		this.put(new BagKey(strKey, index), value);
		return this;
	}

	public MessageBag put(String strKey, String value)
	{
		this.put(new BagKey(strKey), value);
		return this;
	}
	
	public MessageBag put(String tableName, int index, String columnName, String value)
	{
		super.put(new BagKey(tableName, index, columnName), value);
		
		if (!this.columnsMap.containsKey(tableName))
		{
			this.columnsMap.put(tableName, new LinkedHashSet<String>());
		}
		
		Set<String> columnsSet = this.columnsMap.get(tableName);
		
		columnsSet.add(columnName);
		
		return this;
	}
	
	@Override
	public void clear()
	{
		super.clear();
		
		this.columnsMap.clear();
	}

	@Override
	public void copyTo(Bag bag)
	{
		super.copyTo(bag);
		
		if (bag instanceof MessageBag)
		{
			MessageBag messageBag = (MessageBag) bag;
			
			messageBag.columnsMap = new HashMap<String, Set<String>>(this.columnsMap);
		}
	}

	@Override
	public void remove(BagKey key)
	{
		super.remove(key);
		
		this.columnsMap.remove(key);
	}

	public Set<String> getColumns(String tableName)
	{
		return this.columnsMap.get(tableName);
	}
}
