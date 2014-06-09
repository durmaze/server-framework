package framework.bag;

import java.sql.Timestamp;

public class BagValue
{
	private Object value;
	private int type;
	private boolean error;

	public BagValue(Object oValue, int nType)
	{
		super();
		setValue(oValue);
		setType(nType);
		setError(false);
	}

	public int getType()
	{
		return type;
	}

	public Object getValue()
	{
		return value;
	}

	public boolean isError()
	{
		return error;
	}

	public void setError(boolean newError)
	{
		error = newError;
	}

	public void setType(int newType)
	{
		type = newType;
	}

	public void setValue(Object newValue)
	{
		value = newValue;
	}

	public Double toDouble()
	{
		try
		{
			switch (type)
			{
				case BagConstants.SIMPLE_DOUBLE:
				case BagConstants.DOUBLE:
					return (Double) value;
				case BagConstants.STRING:
					return new Double((String) value);
				default:
					setError(true);
					return new Double(0.0);
			}
		}
		catch (Exception e)
		{
			setError(true);
			return new Double(0.0);
		}
	}

	public Float toFloat()
	{
		try
		{
			switch (type)
			{
				case BagConstants.SIMPLE_FLOAT:
				case BagConstants.FLOAT:
					return (Float) value;
				case BagConstants.STRING:
					return new Float((String) value);
				default:
					setError(true);
					return new Float(0.0);
			}
		}
		catch (Exception e)
		{
			setError(true);
			return new Float(0.0);
		}
	}

	public Integer toInt()
	{
		try
		{
			switch (type)
			{
				case BagConstants.SIMPLE_INTEGER:
				case BagConstants.INTEGER:
					return (Integer) value;
				case BagConstants.STRING:
					return new Integer((String) value);
				default:
					setError(true);
					return new Integer(0);
			}
		}
		catch (Exception e)
		{
			setError(true);
			return new Integer(0);
		}
	}

	public Long toLong()
	{
		try
		{
			switch (type)
			{
				case BagConstants.SIMPLE_LONG:
				case BagConstants.LONG:
					return (Long) value;
				case BagConstants.STRING:
					return new Long((String) value);
				default:
					setError(true);
					return new Long(0);
			}
		}
		catch (Exception e)
		{
			setError(true);
			return new Long(0);
		}
	}

	public Object toObject()
	{
		return value;
	}

	public Short toShort()
	{
		try
		{
			switch (type)
			{
				case BagConstants.SIMPLE_SHORT:
					return (Short) value;
				case BagConstants.SHORT:
					return (Short) value;
				case BagConstants.STRING:
					return new Short((String) value);
				default:
					setError(true);
					return new Short((short) 0);
			}
		}
		catch (Exception e)
		{
			setError(true);
			return new Short((short) 0);
		}
	}

	public double toSimpleDouble()
	{
		try
		{
			switch (type)
			{
				case BagConstants.DOUBLE:
				case BagConstants.SIMPLE_DOUBLE:
					return ((Double) value).doubleValue();
				case BagConstants.STRING:
					return Double.valueOf((String) value).doubleValue();
				default:
					setError(true);
					return 0.0;
			}
		}
		catch (Exception e)
		{
			setError(true);
			return 0.0;
		}
	}

	public float toSimpleFloat()
	{
		try
		{
			switch (type)
			{
				case BagConstants.FLOAT:
				case BagConstants.SIMPLE_FLOAT:
					return ((Float) value).floatValue();
				case BagConstants.STRING:
					return Float.valueOf((String) value).floatValue();
				default:
					setError(true);
					return (float) 0.0;
			}
		}
		catch (Exception e)
		{
			setError(true);
			return (float) 0.0;
		}
	}

	public int toSimpleInt()
	{
		try
		{
			switch (type)
			{
				case BagConstants.INTEGER:
				case BagConstants.SIMPLE_INTEGER:
					return ((Integer) value).intValue();
				case BagConstants.STRING:
					return Integer.parseInt((String) value);
				default:
					setError(true);
					return 0;
			}
		}
		catch (Exception e)
		{
			setError(true);
			return 0;
		}
	}

	public long toSimpleLong()
	{
		try
		{
			switch (type)
			{
				case BagConstants.LONG:
				case BagConstants.SIMPLE_LONG:
					return ((Long) value).longValue();
				case BagConstants.STRING:
					return Long.parseLong((String) value);
				default:
					setError(true);
					return 0;
			}
		}
		catch (Exception e)
		{
			setError(true);
			return 0;
		}
	}

	public short toSimpleShort()
	{
		try
		{
			switch (type)
			{
				case BagConstants.SHORT:
				case BagConstants.SIMPLE_SHORT:
					return ((Short) value).shortValue();
				case BagConstants.STRING:
					return Short.parseShort((String) value);
				default:
					setError(true);
					return (short) 0;
			}
		}
		catch (Exception e)
		{
			setError(true);
			return (short) 0;
		}
	}

	public String toString()
	{
		try
		{
			return this.value.toString();
		}
		catch (Exception e)
		{
			setError(true);
			return "";
		}
	}

	public Timestamp toTimeStamp()
	{
		try
		{
			switch (type)
			{
				case BagConstants.TIMESTAMP:
					return (Timestamp) value;
				default:
					setError(true);
					return null;
			}
		}
		catch (Exception e)
		{
			setError(true);
			return null;
		}
	}
}
