package pl.miczeq.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pl.miczeq.exception.DatabaseException;
import pl.miczeq.exception.ValidationException;
import pl.miczeq.model.User;
import pl.miczeq.repository.UserRepository;
import pl.miczeq.util.ConnectionUtil;

public class UserRepositoryImpl implements UserRepository
{

	public void save(User user) throws DatabaseException, ValidationException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		final String SQL = "INSERT INTO user(LOGIN, PASSWORD) VALUES (?, ?)";
		
		try
		{
			validateUser(user);
			
			connection = ConnectionUtil.getConnection();
			preparedStatement = connection.prepareStatement(SQL);
			preparedStatement.setString(1, user.getLogin());
			preparedStatement.setString(2, user.getPassword());
			
			int executeQuery = preparedStatement.executeUpdate();
			
			if(executeQuery != 0)
			{
				System.out.println("User: " + user.getLogin() + " added successfully!");
			}
			else
			{
				throw new DatabaseException("Error while adding new user: " + user.getLogin() + ", SQL: " + SQL);
			}
		}
		catch(SQLException e)
		{
			throw new DatabaseException("Error database connection failed", e);
		}
		finally
		{
			ConnectionUtil.close(preparedStatement);
			ConnectionUtil.close(connection);
		}
	}

	public void update(Long id, User user) throws DatabaseException, ValidationException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		final String SQL = "UPDATE user SET LOGIN = ?, PASSWORD = ? WHERE ID = ?";
		
		try
		{
			validateUser(user);
			
			connection = ConnectionUtil.getConnection();
			preparedStatement = connection.prepareStatement(SQL);
			preparedStatement.setString(1, user.getLogin());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setLong(3, id);
			
			int executeQuery = preparedStatement.executeUpdate();
			
			if(executeQuery != 0)
			{
				System.out.println("User with ID: " + id + " updated successfully!");
			}
			else
			{
				throw new DatabaseException("Error while updating user with ID: " + id + ", SQL: " + SQL);
			}
		}
		catch(SQLException e)
		{
			throw new DatabaseException("Error database connection failed", e);
		}
		finally
		{
			ConnectionUtil.close(preparedStatement);
			ConnectionUtil.close(connection);
		}
	}

	public User findOne(Long id) throws DatabaseException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		final String SQL = "SELECT * FROM user WHERE ID = ?";
		
		try
		{
			connection = ConnectionUtil.getConnection();
			preparedStatement = connection.prepareStatement(SQL);
			preparedStatement.setLong(1, id);
			resultSet = ConnectionUtil.getResultSet(preparedStatement);
			
			User user = null;
			
			if(resultSet.next())
			{
				user = new User(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3));
			}
			
			if(resultSet.next())
			{
				throw new DatabaseException("There exist more than one unique user with ID: " + id);
			}
			
			return user;
		}
		catch(SQLException e)
		{
			throw new DatabaseException("Error database connection failed", e);
		}
		finally
		{
			ConnectionUtil.close(resultSet);
			ConnectionUtil.close(preparedStatement);
			ConnectionUtil.close(connection);
		}
	}

	@Override
	public User findOneByTopicId(Long id) throws DatabaseException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		final String SQL = "SELECT u.ID, u.LOGIN, u.PASSWORD FROM user u INNER JOIN topic t ON (u.ID = t.USER_ID) WHERE u.ID = ?";
		
		try
		{
			connection = ConnectionUtil.getConnection();
			preparedStatement = connection.prepareStatement(SQL);
			preparedStatement.setLong(1, id);
			resultSet = ConnectionUtil.getResultSet(preparedStatement);
			
			User user = null;
			
			if(resultSet.next())
			{
				user = new User(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3));
			}
			
			if(resultSet.next())
			{
				throw new DatabaseException("There exist more than one unique user with ID: " + id);
			}
			
			return user;
		}
		catch(SQLException e)
		{
			throw new DatabaseException("Error database connection failed", e);
		}
		finally
		{
			ConnectionUtil.close(resultSet);
			ConnectionUtil.close(preparedStatement);
			ConnectionUtil.close(connection);
		}
	}

	public List<User> findAll() throws DatabaseException
	{
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		final String SQL = "SELECT * FROM user";
		
		try
		{
			connection = ConnectionUtil.getConnection();
			statement = connection.createStatement();
			resultSet = ConnectionUtil.getResultSet(statement, SQL);
			
			List<User> users = new ArrayList<User>();
			
			while(resultSet.next())
			{
				users.add(new User(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3)));
			}
			
			if(resultSet.next())
			{
				throw new DatabaseException("There exist more than one unique user");
			}
			
			return users;
		}
		catch(SQLException e)
		{
			throw new DatabaseException("Error database connection failed", e);
		}
		finally
		{
			ConnectionUtil.close(resultSet);
			ConnectionUtil.close(statement);
			ConnectionUtil.close(connection);
		}
	}

	public void remove(Long id) throws DatabaseException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		final String SQL = "DELETE FROM user WHERE ID = ?";
		
		try
		{
			connection = ConnectionUtil.getConnection();
			preparedStatement = connection.prepareStatement(SQL);
			preparedStatement.setLong(1, id);
			
			int executeUpdate = preparedStatement.executeUpdate();
			
			if(executeUpdate != 0)
			{
				System.out.println("User with ID: " + id + " have been deleted successfully");
			}
			else
			{
				throw new DatabaseException("Error while deleteing user with ID: " + id);
			}
			
		}
		catch(SQLException e)
		{
			throw new DatabaseException("Error database connection failed", e);
		}
		finally
		{
			ConnectionUtil.close(preparedStatement);
			ConnectionUtil.close(connection);
		}
	}
	
	private void validateUser(User user) throws ValidationException
	{
		if(invalidString(user.getLogin()))
		{
			throw new ValidationException("Login is incorrect, login length must be greater than 3 and lower than 30");
		}
		if(invalidString(user.getPassword()))
		{
			throw new ValidationException("Password is incorrect, login length must be greater than 3 and lower than 30");
		}
	}
	
	private boolean invalidString(String string)
	{
		return string.isEmpty() || string.length() < 3 || string.length() > 30;
	}
}
