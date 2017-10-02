package pl.miczeq.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
