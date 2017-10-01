package pl.miczeq.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import pl.miczeq.exception.DatabaseException;

public class ConnectionUtil
{
	public static Connection getConnection() throws DatabaseException
	{
		final String URL = "jdbc:mysql://localhost:3306/forum";
		final String USER_NAME = "root";
		final String PASSWORD = "";
		
		try
		{
			return DriverManager.getConnection(URL, USER_NAME, PASSWORD);
		}
		catch(SQLException e)
		{
			throw new DatabaseException("Cant connect to DB: " + URL, e);
		}
	}
	
	public static ResultSet getResultSet(Statement statement, final String SQL) throws DatabaseException
	{
		ResultSet resultSet = null;
		try
		{
			resultSet = statement.executeQuery(SQL);
			
			if(resultSet == null)
			{
				throw new DatabaseException("Error with creating resultSet, SQL: " + SQL);
			}
			
			return resultSet;
			
		}
		catch(SQLException e)
		{
			throw new DatabaseException("Cant create resultSet", e);
		}
	}
	
	public static ResultSet getResultSet(PreparedStatement preparedStatement) throws DatabaseException
	{
		ResultSet resultSet = null;
		try
		{
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet == null)
			{
				throw new DatabaseException("Error with creating resultSet, SQL: " + preparedStatement.toString());
			}
			
			return resultSet;
		}
		catch(SQLException e)
		{
			throw new DatabaseException("Cant create resultSet", e);
		}
	}
	
	public static void close(Connection connection) throws DatabaseException
	{
		if(connection != null)
		{
			try
			{
				connection.close();
			}
			catch(SQLException e)
			{
				throw new DatabaseException("Cant close connection", e);
			}
		}
	}
	
	public static void close(Statement statement) throws DatabaseException
	{
		if(statement != null)
		{
			try
			{
				statement.close();
			}
			catch(SQLException e)
			{
				throw new DatabaseException("Cant close statement", e);
			}
		}
	}
	
	public static void close(PreparedStatement preparedStatement) throws DatabaseException
	{
		if(preparedStatement != null)
		{
			try
			{
				preparedStatement.close();
			}
			catch(SQLException e)
			{
				throw new DatabaseException("Cant close preparedStatement", e);
			}
		}
	}
	
	public static void close(ResultSet resultSet) throws DatabaseException
	{
		if(resultSet != null)
		{
			try
			{
				resultSet.close();
			}
			catch(SQLException e)
			{
				throw new DatabaseException("Cant close resultSet", e);
			}
		}
	}
}
