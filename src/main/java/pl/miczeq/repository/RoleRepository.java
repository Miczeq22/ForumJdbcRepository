package pl.miczeq.repository;

import java.util.List;

import pl.miczeq.exception.DatabaseException;
import pl.miczeq.exception.ValidationException;
import pl.miczeq.model.Role;

public interface RoleRepository
{
	void save(Role role) throws DatabaseException, ValidationException;
	
	void update(Long id, Role role) throws DatabaseException, ValidationException;
	
	Role findOne(Long id) throws DatabaseException;
	
	List<Role> findAll() throws DatabaseException;
}
