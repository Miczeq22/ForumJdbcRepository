package pl.miczeq.test;

import org.junit.Before;
import org.junit.Test;

import pl.miczeq.exception.DatabaseException;
import pl.miczeq.exception.ValidationException;
import pl.miczeq.model.User;
import pl.miczeq.repository.UserRepository;
import pl.miczeq.repository.impl.UserRepositoryImpl;

public class UserRepositoryTest
{
	private UserRepository userRepository;
	
	@Before
	public void init()
	{
		userRepository = new UserRepositoryImpl();
	}
	
	@Test
	public void saveTest()
	{
		try
		{
			userRepository.save(new User("jan", "jan123"));
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
			userRepository.update(3L, new User("Jan", "jan123"));
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
			System.out.println(userRepository.findOne(1L));
		}
		catch(DatabaseException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void findAllTest()
	{
		try
		{
			userRepository.findAll().forEach(user -> System.out.println(user));
		} catch (DatabaseException e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void removeTest()
	{
		try
		{
			userRepository.remove(3L);
		}
		catch(DatabaseException e)
		{
			e.printStackTrace();
		}
	}
}
