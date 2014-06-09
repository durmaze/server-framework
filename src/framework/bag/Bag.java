package framework.bag;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import framework.bag.exception.BagKeyNotFoundException;

public class Bag
{
	private final Hashtable<String, BagValue> hash = new Hashtable<String, BagValue>();
	
	public void clear()
	{
		this.hash.clear();
	}

	public void copyTo(Bag bag)
	{
		Enumeration<String> enumeration = this.hash.keys();
		
		while (enumeration.hasMoreElements())
		{
			String key = enumeration.nextElement();
			bag.getAllElements().put(key, this.hash.get(key));
		}
	}

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

	public BagValue get(BagKey key) throws BagKeyNotFoundException
	{
		BagValue value = this.hash.get(key.getKeyString());

		if (value == null)
		{
			throw new BagKeyNotFoundException(key.getKeyString());
		}

		return value;
	}

	public Hashtable<String, BagValue> getAllElements()
	{
		return this.hash;
	}

	public Vector<String> getColums(String tableName) 
	{
		Hashtable<String, BagValue> allElements = new Hashtable<>();
		String tablePrefix = tableName + ":";

		Vector<String> columns = new Vector<>();

		allElements = getAllElements();
		Enumeration<String> enumKeys = allElements.keys();
		
		while (enumKeys.hasMoreElements())
		{
			String currentElement = enumKeys.nextElement().toString();
			int columnIndex = currentElement.lastIndexOf(":");

			if (columnIndex != -1 && currentElement.startsWith(tablePrefix)) 	// isTable()
			{
				String columnName = currentElement.substring(columnIndex + 1);
				columns.addElement(columnName);
			}
		}
		
		return columns;
	}

	public int getDimension(BagKey key)
	{
		Object dim = this.hash.get(key.getKeyString() + "_DIM");

		if (dim == null)
		{
			return 0;
		}
		
		return ((BagValue) dim).toSimpleInt();
	}

	public int getSize(BagKey key)
	{
		Object size = this.hash.get(key.getKeyString() + "_SIZE");

		if (size == null)
		{
			return 0;
		}
		
		return ((BagValue) size).toSimpleInt();
	}

	public boolean isEmpty()
	{
		return this.hash.isEmpty();
	}

	public Bag put(BagKey key, double value)
	{
		this.hash.put(key.getKeyString(), new BagValue(new Double(value), BagConstants.SIMPLE_DOUBLE));
		
		if (key.getFirstIndex() > -1)
		{
			setBagSize(key);
		}
		
		return this;
	}

	public Bag put(BagKey key, float value)
	{
		this.hash.put(key.getKeyString(), new BagValue(new Float(value), BagConstants.SIMPLE_FLOAT));
		
		if (key.getFirstIndex() > -1)
		{
			setBagSize(key);
		}
		
		return this;
	}

	public Bag put(BagKey key, int value)
	{
		this.hash.put(key.getKeyString(), new BagValue(new Integer(value), BagConstants.SIMPLE_INTEGER));
		if (key.getFirstIndex() > -1)
			setBagSize(key);
		return this;
	}

	public Bag put(BagKey key, long value)
	{
		this.hash.put(key.getKeyString(), new BagValue(new Long(value), BagConstants.SIMPLE_LONG));
		if (key.getFirstIndex() > -1)
			setBagSize(key);
		return this;
	}

	public Bag put(BagKey key, Double value)
	{
		this.hash.put(key.getKeyString(), new BagValue(value, BagConstants.DOUBLE));
		if (key.getFirstIndex() > -1)
			setBagSize(key);
		return this;
	}

	public Bag put(BagKey key, Float value)
	{
		this.hash.put(key.getKeyString(), new BagValue(value, BagConstants.FLOAT));
		if (key.getFirstIndex() > -1)
			setBagSize(key);
		return this;
	}

	public Bag put(BagKey key, Integer value)
	{
		this.hash.put(key.getKeyString(), new BagValue(value, BagConstants.INTEGER));
		if (key.getFirstIndex() > -1)
			setBagSize(key);
		return this;
	}

	public Bag put(BagKey key, Long value)
	{
		this.hash.put(key.getKeyString(), new BagValue(value, BagConstants.LONG));
		if (key.getFirstIndex() > -1)
			setBagSize(key);
		return this;
	}

	public Bag put(BagKey key, Object value)
	{
		boolean bSerializable = false;
		Class<? extends Object> clazz = value.getClass();
		
		@SuppressWarnings("rawtypes")
		Class[] cls = clazz.getInterfaces();
		while (!bSerializable)
		{
			for (int i = 0; i < cls.length; i++)
			{
				if (cls[i] == Serializable.class)
				{
					bSerializable = true;
					break;
				}
			}
			if (!bSerializable)
			{
				clazz = clazz.getSuperclass();
				if (clazz == null)
					break;
				cls = clazz.getInterfaces();
			}
		}

		if (bSerializable == true)
		{
			this.hash.put(key.getKeyString(), new BagValue(value, BagConstants.OBJECT));
			if (key.getFirstIndex() > -1)
				setBagSize(key);
		}
		return this;
	}

	public Bag put(BagKey key, Short value)
	{
		this.hash.put(key.getKeyString(), new BagValue(value, BagConstants.SHORT));
		if (key.getFirstIndex() > -1)
			setBagSize(key);
		return this;
	}

	public Bag put(BagKey key, String value)
	{
		this.hash.put(key.getKeyString(), new BagValue(value, BagConstants.STRING));
		if (key.getFirstIndex() > -1)
			setBagSize(key);
		return this;
	}

	public Bag put(BagKey key, Timestamp value)
	{
		this.hash.put(key.getKeyString(), new BagValue(value, BagConstants.TIMESTAMP));
		if (key.getFirstIndex() > -1)
			setBagSize(key);
		return this;
	}

	public Bag put(BagKey key, short value)
	{
		this.hash.put(key.getKeyString(), new BagValue(new Short(value), BagConstants.SIMPLE_SHORT));
		if (key.getFirstIndex() > -1)
			setBagSize(key);
		return this;
	}

	public void remove(BagKey key)
	{
		this.hash.remove(key.getKeyString());
	}

	protected void setBagSize(BagKey key)
	{
		int nIndex = -1;
		int nIndex2 = -1;
		int nIndex3 = -1;
		nIndex = key.getKeyString().indexOf(":");
		nIndex2 = key.getKeyString().indexOf(":", nIndex + 1);
		if (nIndex2 > -1)
			nIndex3 = key.getKeyString().indexOf(":", nIndex2 + 1);
		StringBuffer buf = new StringBuffer(key.getKeyString().substring(0, nIndex));
		StringBuffer bufDimension = new StringBuffer(key.getKeyString().substring(0, nIndex));
		bufDimension.append("_DIM");
		if (nIndex2 == -1)
		{
			buf.append("_SIZE");
			put(new BagKey(buf.toString()),
					Integer.parseInt(key.getKeyString().substring(nIndex + 1)) + 1);
			put(new BagKey(bufDimension.toString()), 1);
		}
		else if (nIndex3 == -1)
		{
			buf.append("_SIZE");
			put(new BagKey(buf.toString()),
					Integer.parseInt(key.getKeyString().substring(nIndex + 1, nIndex2)) + 1);

		}
	}

	public String toString()
	{
		String rStr = "";
		Hashtable<String, BagValue> h = getAllElements();
		Enumeration<String> keys = h.keys();

		while (keys.hasMoreElements())
		{
			String k = keys.nextElement().toString();
			rStr += k + "=" + h.get(k) + "\r\n";
		}
		
		return rStr;
	}
}
