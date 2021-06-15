package backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

public interface IService<T> {
	
	public List<T> findAll();
	
	public T findById(Long id);
	
	public T save(T obj);
	
	public void delete(T obj);
}
