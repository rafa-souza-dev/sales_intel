package com.example.salesIntel.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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

import com.example.salesIntel.controller.responses.UserResponse;
import com.example.salesIntel.model.User;
import com.example.salesIntel.model.dtos.LoginDTO;
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
	public ResponseEntity<List<UserResponse>> getUsers(){
		return ResponseEntity.ok(service.getAllUsers().stream().map(this::convert).collect(Collectors.toList()));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id){
		try {
			return ResponseEntity.ok(convert(service.getUserById(id)));
		} catch (SalesException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("email/{email}")
	public ResponseEntity<?> getByEmail(@PathVariable String email){
		try {
			return ResponseEntity.ok(convert(service.getUserByEmail(email)));
		} catch (SalesException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("company/{company}")
	public ResponseEntity<?> getByComapny(@PathVariable String company){
		try {
			return ResponseEntity.ok(convert(service.getUserByCompany(company)));
		} catch (SalesException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("auth")
	public ResponseEntity<?> createUser(@RequestBody UserDTO dto){
		try {
			service.createUser(dto);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch(SalesException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("auth/login")
	public ResponseEntity<?> login(@RequestBody LoginDTO dto){
		try {
			return ResponseEntity.ok(service.login(dto));
		} catch (SalesException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO dto){
		try {
			return ResponseEntity.ok(convert(service.updateUser(id, dto)));
		} catch(SalesException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id){
		service.deleteUser(id);
		return ResponseEntity.noContent().build();
	}
	
	private UserResponse convert (User user) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(user, UserResponse.class);
	}


}
