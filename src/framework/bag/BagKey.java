package framework.bag;


public class BagKey
{
	private java.lang.String keyName;
	private int firstIndex = -1;
	private int secondIndex = -1;
	private String columnName = "";
	private int thirdIndex = -1;
	private java.lang.String keyString;

	/**
	 * BagKey constructor. Used for non-array parameters
	 */
	public BagKey(String sKey)
	{
		super();
		keyName = sKey;
		keyString = keyName;
	}

	/**
	 * BagKey constructor. Used for one-dimensional array parameters
	 */
	public BagKey(String sKey, int first)
	{
		super();
		keyName = sKey;
		firstIndex = first;
		StringBuffer bf = new StringBuffer(keyName);
		bf.append(":");
		bf.append(firstIndex);
		keyString = bf.toString();
	}

	/**
	 * BagKey constructor. Used for two-dimensional array parameters
	 */
	public BagKey(String sKey, int first, int second)
	{
		super();
		keyName = sKey;
		firstIndex = first;
		secondIndex = second;
		StringBuffer bf = new StringBuffer(keyName);
		bf.append(":");
		bf.append(firstIndex);
		bf.append(":");
		bf.append(secondIndex);
		keyString = bf.toString();
	}

	/**
	 * BagKey constructor. Used for three-dimensional array parameters
	 */
	public BagKey(String sKey, int first, int second, int third)
	{
		super();
		keyName = sKey;
		firstIndex = first;
		secondIndex = second;
		thirdIndex = third;
		StringBuffer bf = new StringBuffer(keyName);
		bf.append(":");
		bf.append(firstIndex);
		bf.append(":");
		bf.append(secondIndex);
		bf.append(":");
		bf.append(thirdIndex);
		keyString = bf.toString();
	}

	/**
	 * BagKey constructor. Used for two-dimensional array parameters
	 */
	public BagKey(String tableName, int index, String columnName)
	{
		super();
		keyName = tableName;
		firstIndex = index;
		this.columnName = columnName;
		StringBuffer bf = new StringBuffer(tableName);
		bf.append(":");
		bf.append(firstIndex);
		bf.append(":");
		bf.append(this.columnName);
		keyString = bf.toString();
	}

	public java.lang.String getColumnName()
	{
		return columnName;
	}

	/**
	 * Returns first dimension index of key.
	 */
	public int getFirstIndex()
	{
		return firstIndex;
	}

	public String getKeyName()
	{
		return keyName;
	}

	public String getKeyString()
	{
		return keyString;
	}

	/**
	 * Returns second dimension index of key.
	 */
	public int getSecondIndex()
	{
		return secondIndex;
	}

	/**
	 * Returns third dimension index of key.
	 */
	public int getThirdIndex()
	{
		return thirdIndex;
	}

	public void setColumnName(java.lang.String newColumnName)
	{
		columnName = newColumnName;
	}

	public void setKeyName(java.lang.String newKeyName)
	{
		keyName = newKeyName;
	}
}
