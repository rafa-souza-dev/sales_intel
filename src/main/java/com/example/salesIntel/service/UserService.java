package com.example.salesIntel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.salesIntel.model.User;
import com.example.salesIntel.model.dtos.UserDTO;
import com.example.salesIntel.repository.UserRepository;
import com.example.salesIntel.utils.SalesException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository repository;
	
	public List<User> getAllUsers(){
		return repository.findAll();
	}
	
	public User getUserById(Long id) throws SalesException {
		User user = repository.findById(id).orElseThrow(()  
				-> new SalesException("There is no user associated with this id"));
		return user;
	}
	
	public User getUserByEmail(String email) throws SalesException {
		User user = repository.findByEmail(email).orElseThrow(()  
				-> new SalesException("There is no user associated with this id"));
		return user;
	}
	
	public User getUserByCompany(String company) throws SalesException {
		User user = repository.findByCompany(company).orElseThrow(()  
				-> new SalesException("There is no user associated with this id"));
		return user;
		
	}
	
		
	public void createUser(UserDTO userDTO) throws SalesException {
		Optional<User> userExist = repository.findByEmail(userDTO.getEmail());
		if(userExist.isPresent()) {
			throw new SalesException("This email is already beeing used");
		}
		
		User user = new User();
		user.setEmail(userDTO.getEmail());
		user.setCompany(userDTO.getCompany());
		user.setPassword(userDTO.getPassword());
		repository.save(user);
	}
	
	public User updateUser(Long id, UserDTO userDTO) throws SalesException {
		User user = getUserById(id);
		user.setEmail(userDTO.getEmail());
		user.setCompany(userDTO.getCompany());		
		repository.save(user);
		
		return user;
	}
	
	public void deleteUser(Long id) {
		repository.deleteById(id);				
	}

}