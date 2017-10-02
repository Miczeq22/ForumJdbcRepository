package pl.miczeq.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public Comment findOne(Long id) throws DatabaseException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Comment> findAll() throws DatabaseException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Comment> findAllByTopicId(Long id) throws DatabaseException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(Long id) throws DatabaseException
	{
		// TODO Auto-generated method stub
		
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
