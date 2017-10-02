package pl.miczeq.test;

import org.junit.Before;
import org.junit.Test;

import pl.miczeq.exception.DatabaseException;
import pl.miczeq.exception.ValidationException;
import pl.miczeq.model.Topic;
import pl.miczeq.repository.TopicRepository;
import pl.miczeq.repository.impl.TopicRepositoryImpl;

public class TopicRepositoryTest
{
	private TopicRepository topicRepository;
	
	@Before
	public void init()
	{
		topicRepository = new TopicRepositoryImpl();
	}
	
	@Test
	public void saveTest()
	{
		try
		{
			topicRepository.save(new Topic("Test topic", "Some content...", 1L));
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
	
	@Test 
	public void updateTest()
	{
		try
		{
			topicRepository.update(1L, new Topic("Test Topic", "Some Content...", 1L));
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
	
	@Test
	public void findOneTest()
	{
		try
		{
			System.out.println(topicRepository.findOne(1L));
		}
		catch(DatabaseException e)
		{
			e.printStackTrace();
		}
	}
}
