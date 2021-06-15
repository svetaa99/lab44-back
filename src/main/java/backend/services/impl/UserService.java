package backend.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.models.User;
import backend.repositories.UserRepository;
import backend.security.TokenUtils;
import backend.services.IService;

@Service
public class UserService implements IService<User>{
	
	@Autowired 
	private UserRepository userService;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Override
	public List<User> findAll() {
		return userService.findAll();
	}
	
	public User findUserByEmail(String email){
		if(userService.findUserByEmailEquals(email).size() == 0)
			return null;
		return userService.findUserByEmailEquals(email).get(0);
	}
	
	public User findUserByToken(String token) {
		if(userService.findUserByEmailEquals(tokenUtils.getUsernameFromToken(token)).size() == 0)
			return null;
		return userService.findUserByEmailEquals(tokenUtils.getUsernameFromToken(token)).get(0);
	}
	
	@Override
	public User findById(Long id) {
		if(userService.findById(id) == null)
			return null;
		return userService.findById(id).get();
	}

	@Override
	public User save(User obj) {
		// TODO Auto-generated method stub
		return userService.save(obj);
	}

	@Override
	public void delete(User obj) {
		// TODO Auto-generated method stub
		
	}

}
