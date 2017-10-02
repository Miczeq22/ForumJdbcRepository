package pl.miczeq.repository;

import pl.miczeq.exception.DatabaseException;
import pl.miczeq.exception.ValidationException;
import pl.miczeq.model.Role;

public interface RoleRepository
{
	void save(Role role) throws DatabaseException, ValidationException;
}
