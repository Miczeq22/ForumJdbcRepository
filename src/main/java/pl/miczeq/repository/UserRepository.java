package pl.miczeq.repository;

import java.util.List;

import pl.miczeq.exception.DatabaseException;
import pl.miczeq.exception.ValidationException;
import pl.miczeq.model.User;

public interface UserRepository
{
	void save(User user) throws DatabaseException, ValidationException;
	
	void update(Long id, User user) throws DatabaseException, ValidationException;
	
	User findOne(Long id) throws DatabaseException;
	
	User findOneByTopicId(Long id) throws DatabaseException;
	
	List<User> findAll() throws DatabaseException;
	
	void remove(Long id) throws DatabaseException;
}
