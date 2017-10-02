package pl.miczeq.test;

import org.junit.Before;
import org.junit.Test;

import pl.miczeq.exception.DatabaseException;
import pl.miczeq.exception.ValidationException;
import pl.miczeq.model.Role;
import pl.miczeq.repository.RoleRepository;
import pl.miczeq.repository.impl.RoleRepositoryImpl;

public class RoleRepositoryTest
{
	private RoleRepository roleRepository;
	
	@Before
	public void init()
	{
		roleRepository = new RoleRepositoryImpl();
	}
	
	@Test
	public void saveTest()
	{
		try
		{
			roleRepository.save(new Role("SUPER_ADMIN", 1L));
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
