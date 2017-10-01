package pl.miczeq.exception;

public class ValidationException extends Exception
{
	private static final long serialVersionUID = -7600071658656005975L;

	public ValidationException()
	{
		super();
	}
	
	public ValidationException(String msg)
	{
		super(msg);
	}
	
	public ValidationException(String msg, Throwable e)
	{
		super(msg, e);
	}
}
