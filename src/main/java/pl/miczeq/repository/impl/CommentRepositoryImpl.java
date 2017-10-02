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
import pl.miczeq.model.Comment;
import pl.miczeq.repository.CommentRepository;
import pl.miczeq.util.ConnectionUtil;

public class CommentRepositoryImpl implements CommentRepository
{

	@Override
	public void save(Comment comment) throws DatabaseException, ValidationException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		final String SQL = "INSERT INTO comment(USER_ID, TOPIC_ID, CONTENT, LIKES) VALUES(?, ?, ?, ?)";
		
		try
		{
			validateComment(comment);
			
			connection = ConnectionUtil.getConnection();
			preparedStatement = connection.prepareStatement(SQL);
			preparedStatement.setLong(1, comment.getUserId());
			preparedStatement.setLong(2, comment.getTopicId());
			preparedStatement.setString(3, comment.getContent());
			preparedStatement.setInt(4, comment.getLikes());
			
			int executeQuery = preparedStatement.executeUpdate();
			
			if(executeQuery != 0)
			{
				System.out.println("Comment added successfully!");
			}
			else
			{
				throw new DatabaseException("Error while adding new comment, SQL: " + SQL);
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
	public void update(Long id, Comment comment) throws DatabaseException, ValidationException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		final String SQL = "UPDATE comment SET USER_ID = ?, TOPIC_ID = ?, CONTENT = ?, LIKES = ? WHERE ID = ?";
		
		try
		{
			validateComment(comment);
			
			connection = ConnectionUtil.getConnection();
			preparedStatement = connection.prepareStatement(SQL);
			preparedStatement.setLong(1, comment.getUserId());
			preparedStatement.setLong(2, comment.getTopicId());
			preparedStatement.setString(3, comment.getContent());
			preparedStatement.setInt(4, comment.getLikes());
			preparedStatement.setLong(5, id);
			
			int executeQuery = preparedStatement.executeUpdate();
			
			if(executeQuery != 0)
			{
				System.out.println("Comment with ID: " + id + " updated successfully!");
			}
			else
			{
				throw new DatabaseException("Error while updating comment with ID: " + id + ", SQL: " + SQL);
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
	public Comment findOne(Long id) throws DatabaseException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		final String SQL = "SELECT * FROM comment WHERE ID = ?";
		
		try
		{
			connection = ConnectionUtil.getConnection();
			preparedStatement = connection.prepareStatement(SQL);
			preparedStatement.setLong(1, id);
			
			resultSet = ConnectionUtil.getResultSet(preparedStatement);
			
			Comment comment = null;
			
			if(resultSet.next())
			{
				comment = new Comment(resultSet.getLong(1), resultSet.getString(4), resultSet.getLong(3), resultSet.getLong(2), resultSet.getInt(5));
			}
			
			if(resultSet.next())
			{
				throw new DatabaseException("There exist more than one unique comment with ID: " + id);
			}
			
			return comment;
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
	public List<Comment> findAll() throws DatabaseException
	{
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		final String SQL = "SELECT * FROM comment";
		
		try
		{
			connection = ConnectionUtil.getConnection();
			statement = connection.createStatement();
			
			resultSet = ConnectionUtil.getResultSet(statement, SQL);
			
			List<Comment> comments = new ArrayList<>();
			
			while(resultSet.next())
			{
				comments.add(new Comment(resultSet.getLong(1), resultSet.getString(4), resultSet.getLong(3), resultSet.getLong(2), resultSet.getInt(5)));
			}
			
			if(resultSet.next())
			{
				throw new DatabaseException("There exist more than one unique comment in database");
			}
			
			return comments;
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
	public List<Comment> findAllByTopicId(Long id) throws DatabaseException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		final String SQL = "SELECT * FROM comment WHERE TOPIC_ID = ?";
		
		try
		{
			connection = ConnectionUtil.getConnection();
			preparedStatement = connection.prepareStatement(SQL);
			preparedStatement.setLong(1, id);
			
			resultSet = ConnectionUtil.getResultSet(preparedStatement);
			
			List<Comment> comments = new ArrayList<>();
			
			while(resultSet.next())
			{
				comments.add(new Comment(resultSet.getLong(1), resultSet.getString(4), resultSet.getLong(3), resultSet.getLong(2), resultSet.getInt(5)));
			}
			
			if(resultSet.next())
			{
				throw new DatabaseException("There exist more than one unique comment in database");
			}
			
			return comments;
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
	public void remove(Long id) throws DatabaseException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		final String SQL = "DELETE FROM comment WHERE ID = ?";
		
		try
		{
			connection = ConnectionUtil.getConnection();
			preparedStatement = connection.prepareStatement(SQL);
			preparedStatement.setLong(1, id);
			
			int executeUpdate = preparedStatement.executeUpdate();
			
			if(executeUpdate != 0)
			{
				System.out.println("Comment with ID: " + id + " deleted successfully!");
			}
			else
			{
				throw new DatabaseException("Error while deleting comment with ID: " + id);
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
	
	private void validateComment(Comment comment) throws ValidationException
	{
		if(comment.getUserId() == null)
		{
			throw new ValidationException("Comment must have a user");
		}
		
		if(comment.getTopicId() == null)
		{
			throw new ValidationException("Comment must have a topic");
		}
		
		if(comment.getContent().isEmpty())
		{
			throw new ValidationException("Comment content cant be empty");
		}
	}
	
}
