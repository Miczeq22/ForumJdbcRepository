package pl.miczeq.test;

import org.junit.Before;
import org.junit.Test;

import pl.miczeq.exception.DatabaseException;
import pl.miczeq.exception.ValidationException;
import pl.miczeq.model.Comment;
import pl.miczeq.repository.CommentRepository;
import pl.miczeq.repository.impl.CommentRepositoryImpl;

public class CommentRepositoryTest
{
	private CommentRepository commentRepository;
	
	@Before
	public void init()
	{
		commentRepository = new CommentRepositoryImpl();
	}
	
	@Test
	public void saveTest()
	{
		try
		{
			commentRepository.save(new Comment("Some test comment", 1L, 1L, 0));
		}
		catch(DatabaseException e)
		{
			e.printStackTrace();
		}
		catch(ValidationException e)
		{
			e.printStackTrace();
		}
	}
}
