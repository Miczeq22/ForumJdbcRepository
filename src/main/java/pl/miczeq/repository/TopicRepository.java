package pl.miczeq.repository;

import java.util.List;

import pl.miczeq.exception.DatabaseException;
import pl.miczeq.exception.ValidationException;
import pl.miczeq.model.Topic;

public interface TopicRepository
{
	void save(Topic topic) throws DatabaseException, ValidationException;
	
	void update(Long id, Topic topic) throws DatabaseException, ValidationException;
	
	Topic findOne(Long id) throws DatabaseException;
	
	List<Topic> findAll() throws DatabaseException;
	
	void delete(Long id) throws DatabaseException;
}
