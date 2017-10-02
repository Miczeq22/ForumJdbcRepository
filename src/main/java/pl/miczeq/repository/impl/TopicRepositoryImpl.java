package pl.miczeq.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import pl.miczeq.exception.DatabaseException;
import pl.miczeq.exception.ValidationException;
import pl.miczeq.model.Topic;
import pl.miczeq.repository.TopicRepository;
import pl.miczeq.util.ConnectionUtil;

public class TopicRepositoryImpl implements TopicRepository
{

	@Override
	public void save(Topic topic) throws DatabaseException, ValidationException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		final String SQL = "INSERT INTO topic(NAME, CONTENT, USER_ID) VALUES(?, ?, ?)";
		
		try
		{
			connection = ConnectionUtil.getConnection();
			preparedStatement = connection.prepareStatement(SQL);
			preparedStatement.setString(1, topic.getName());
			preparedStatement.setString(2, topic.getContent());
			preparedStatement.setLong(3, topic.getUserId());
			
			int executeUpdate = preparedStatement.executeUpdate();
			
			if(executeUpdate != 0)
			{
				System.out.println("Topic: " + topic.getName() + " added successfully!");
			}
			else
			{
				throw new DatabaseException("Error while adding new topic: " + topic.getContent() + ", SQL: " + SQL);
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

	@Override
	public void update(Long id, Topic topic) throws DatabaseException, ValidationException
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Topic findOne(Long id) throws DatabaseException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Topic> findAll() throws DatabaseException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) throws DatabaseException
	{
		// TODO Auto-generated method stub
		
	}
}
