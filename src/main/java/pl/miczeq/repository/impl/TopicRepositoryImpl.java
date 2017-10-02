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
			validateTopic(topic);
			
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
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		final String SQL = "UPDATE topic SET NAME = ?, CONTENT = ?, USER_ID = ? WHERE ID = ?";
		
		try
		{
			validateTopic(topic);
			
			connection = ConnectionUtil.getConnection();
			preparedStatement = connection.prepareStatement(SQL);
			preparedStatement.setString(1, topic.getName());
			preparedStatement.setString(2, topic.getContent());
			preparedStatement.setLong(3, topic.getUserId());
			preparedStatement.setLong(4, id);
			
			int executeUpdate = preparedStatement.executeUpdate();
			
			if(executeUpdate != 0)
			{
				System.out.println("Topic with ID: " + id + " updated successfully!");
			}
			else
			{
				throw new DatabaseException("Error while updating topic with ID: " + id + ", SQL: " + SQL);
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
	public Topic findOne(Long id) throws DatabaseException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		final String SQL = "SELECT * FROM topic WHERE ID = ?";
		
		try
		{
			connection = ConnectionUtil.getConnection();
			preparedStatement = connection.prepareStatement(SQL);
			preparedStatement.setLong(1, id);
			
			resultSet = ConnectionUtil.getResultSet(preparedStatement);
			
			Topic topic = null;
			
			if(resultSet.next())
			{
				topic = new Topic(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getLong(4));
			}
			
			if(resultSet.next())
			{
				throw new DatabaseException("There exist more than one unique topic with ID: " + id);
			}
			
			return topic;
			
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
	public List<Topic> findAll() throws DatabaseException
	{
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		final String SQL = "SELECT * FROM topic";
		
		try
		{
			connection = ConnectionUtil.getConnection();
			statement = connection.createStatement();
			
			resultSet = ConnectionUtil.getResultSet(statement, SQL);
			
			List<Topic> topics = new ArrayList<>();
			
			while(resultSet.next())
			{
				topics.add(new Topic(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getLong(4)));
			}
			
			if(resultSet.next())
			{
				throw new DatabaseException("There exist more than one unique topic in database");
			}
			
			return topics;
			
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

	@Override
	public void remove(Long id) throws DatabaseException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		final String SQL = "DELETE FROM topic WHERE ID = ?";
		
		try
		{
			connection = ConnectionUtil.getConnection();
			preparedStatement = connection.prepareStatement(SQL);
			preparedStatement.setLong(1, id);
			
			int executeQuery = preparedStatement.executeUpdate();
			
			if(executeQuery != 0)
			{
				System.out.println("Topic with ID: " + id + " have been deleted successfully");
			}
			else
			{
				throw new DatabaseException("Error while deleteing topic with ID: " + id);
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
	
	private void validateTopic(Topic topic) throws ValidationException
	{
		if(topic.getName().isEmpty() || topic.getName().length() > 30 || topic.getName().length() < 3)
		{
			throw new ValidationException("Topic name length must be greater than 3 and lower than 30");
		}
		
		if(topic.getContent().isEmpty())
		{
			throw new ValidationException("Topic content cant be empty");
		}
		
		if(topic.getUserId() == null)
		{
			throw new ValidationException("Topic must have a user");
		}
	}
}
