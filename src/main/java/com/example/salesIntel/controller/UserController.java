package com.example.salesIntel.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.salesIntel.model.User;
import com.example.salesIntel.model.dtos.UserDTO;
import com.example.salesIntel.service.UserService;
import com.example.salesIntel.utils.SalesException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService service;
	
	@GetMapping
	public ResponseEntity<List<User>> getUsers(){
		return ResponseEntity.ok(service.getAllUsers());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id){
		try {
			return ResponseEntity.ok(service.getUserById(id));
		} catch (SalesException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("email/{email}")
	public ResponseEntity<?> getByEmail(@PathVariable String email){
		try {
			return ResponseEntity.ok(service.getUserByEmail(email));
		} catch (SalesException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody UserDTO dto){
		try {
			service.createUser(dto);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch(SalesException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO dto){
		try {
			return ResponseEntity.ok(service.updateUser(id, dto));
		} catch(SalesException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id){
		service.deleteUser(id);
		return ResponseEntity.noContent().build();
	}

}
