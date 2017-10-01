package pl.miczeq.test;

import java.sql.Connection;

import org.junit.Test;

import pl.miczeq.exception.DatabaseException;
import pl.miczeq.util.ConnectionUtil;

public class ConnectionUtilTest
{
	@Test
	public void connectionTest()
	{
		try
		{
			Connection connection = ConnectionUtil.getConnection();
			if(connection != null)
			{
				System.out.println("You are connected to Database");
				ConnectionUtil.close(connection);
			}
		}
		catch(DatabaseException e)
		{
			e.printStackTrace();
		}
	}
}
