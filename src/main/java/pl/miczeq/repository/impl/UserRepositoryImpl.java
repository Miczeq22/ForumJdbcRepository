package pl.miczeq.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
		// TODO Auto-generated method stub
		return null;
	}

	public List<User> findAll() throws DatabaseException
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(Long id) throws DatabaseException
	{
		// TODO Auto-generated method stub
		
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
