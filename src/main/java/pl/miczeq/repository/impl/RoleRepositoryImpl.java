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
import pl.miczeq.model.Role;
import pl.miczeq.repository.RoleRepository;
import pl.miczeq.util.ConnectionUtil;

public class RoleRepositoryImpl implements RoleRepository
{

	@Override
	public void save(Role role) throws DatabaseException, ValidationException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		final String SQL = "INSERT INTO role(NAME, USER_ID) VALUES(?, ?)";
		
		try
		{
			validateRole(role);
			
			connection  = ConnectionUtil.getConnection();
			preparedStatement = connection.prepareStatement(SQL);
			preparedStatement.setString(1, role.getName());
			preparedStatement.setLong(2, role.getUserId());
			
			int executeQuery = preparedStatement.executeUpdate();
			
			if(executeQuery != 0)
			{
				System.out.println("Role: " + role.getName() + " added successfully!");
			}
			else
			{
				throw new DatabaseException("Error whiel adding new role: " + role.getName() + ", SQL: " + SQL);
			}
		}
		catch(SQLException e)
		{
			throw new DatabaseException("Error databse connection error", e);
		}
		finally
		{
			ConnectionUtil.close(preparedStatement);
			ConnectionUtil.close(connection);
		}
	}
	
	@Override
	public void update(Long id, Role role) throws DatabaseException, ValidationException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		final String SQL = "UPDATE role SET NAME = ?, USER_ID = ? WHERE ID = ?";
		
		try
		{
			validateRole(role);
			
			connection = ConnectionUtil.getConnection();
			preparedStatement = connection.prepareStatement(SQL);
			preparedStatement.setString(1, role.getName());
			preparedStatement.setLong(2, role.getUserId());
			preparedStatement.setLong(3, id);
			
			int executeQuery = preparedStatement.executeUpdate();
			
			if(executeQuery != 0)
			{
				System.out.println("Role with ID: " + id + " updated successfully!");
			}
			else
			{
				throw new DatabaseException("Cant update role with ID: " + id);
			}
		}
		catch(SQLException e)
		{
			throw new DatabaseException("Error database connection error", e);
		}
		finally
		{
			ConnectionUtil.close(preparedStatement);
			ConnectionUtil.close(connection);
		}
	}

	@Override
	public Role findOne(Long id) throws DatabaseException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		final String SQL = "SELECT * FROM role WHERE ID = ?";
		
		try
		{
			connection = ConnectionUtil.getConnection();
			preparedStatement = connection.prepareStatement(SQL);
			preparedStatement.setLong(1, id);
			
			resultSet = ConnectionUtil.getResultSet(preparedStatement);
			
			Role role = null;
			
			if(resultSet.next())
			{
				role = new Role(resultSet.getLong(1), resultSet.getString(2), resultSet.getLong(3));
			}
			
			if(resultSet.next())
			{
				throw new DatabaseException("There are more rows with unique ID: " + id);
			}
			
			return role;
		}
		catch(SQLException e)
		{
			throw new DatabaseException("Error database connection error", e);
		}
		finally
		{
			ConnectionUtil.close(resultSet);
			ConnectionUtil.close(preparedStatement);
			ConnectionUtil.close(connection);
		}
	}

	@Override
	public List<Role> findAll() throws DatabaseException
	{
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		final String SQL = "SELECT * FROM role";
		
		try
		{
			connection = ConnectionUtil.getConnection();
			statement = connection.createStatement();
			
			resultSet = ConnectionUtil.getResultSet(statement, SQL);
			
			List<Role> roles = new ArrayList<>();
			
			while(resultSet.next())
			{
				roles.add(new Role(resultSet.getLong(1), resultSet.getString(2), resultSet.getLong(3)));
			}
			
			if(resultSet.next())
			{
				throw new DatabaseException("There are more rows with unique ID");
			}
			
			return roles;
		}
		catch(SQLException e)
		{
			throw new DatabaseException("Error database connection error", e);
		}
		finally
		{
			ConnectionUtil.close(resultSet);
			ConnectionUtil.close(statement);
			ConnectionUtil.close(connection);
		}
	}

	private void validateRole(Role role) throws ValidationException
	{
		if(role.getUserId() == null)
		{
			throw new ValidationException("Role must have user");
		}
		
		if(role.getName().isEmpty() || role.getName().length() > 30 || role.getName().length() < 3)
		{
			throw new ValidationException("Role name length must be greater than 30 and lower than 3");
		}
	}
}
