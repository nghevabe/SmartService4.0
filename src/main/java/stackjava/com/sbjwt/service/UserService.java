package stackjava.com.sbjwt.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import stackjava.com.sbjwt.entities.UserEntity;
import stackjava.com.sbjwt.repo.UserRepo;


@Service
public class UserService {



	@Autowired
	UserRepo userRepo;

	public List<UserEntity> findAll() {
		return userRepo.findAll();
	}

	public UserEntity findById(int id) {
		return userRepo.findById(id).get();
	}

	public boolean add(UserEntity userEntity) {
		userRepo.save(userEntity);
		return true;
	}

	public void delete(int id) {
		userRepo.deleteById(id);
	}

	public UserEntity loadUserByUsername(String username) {
		//return userRepo.findByUsername(username).orElseThrow( () -> new UsernameNotFoundException(username + " not found") );
		return userRepo.findByUsername(username).get();
	}

	public UserEntity getUserByUsername(String username) {
		//return userRepo.findByUsername(username).orElseThrow( () -> new UsernameNotFoundException(username + " not found") );
		return userRepo.getByUsername(username);
	}

	public boolean checkLogin(UserEntity userEntity) {
		UserEntity temp = loadUserByUsername(userEntity.getUsername());
		return temp.getPassword().equals(userEntity.getPassword());
	}
}
