package backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.models.User;

public interface UserRepository extends JpaRepository<User, Long>{

	public List<User> findAll();
	
	public List<User> findUserByEmailEquals(String email);
}
