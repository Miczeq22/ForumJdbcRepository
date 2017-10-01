package pl.miczeq.exception;

public class DatabaseException extends Exception
{
	private static final long serialVersionUID = -5607093891361798974L;

	public DatabaseException()
	{
		super();
	}
	
	public DatabaseException(String msg)
	{
		super(msg);
	}
	
	public DatabaseException(String msg, Throwable e)
	{
		super(msg, e);
	}
}
