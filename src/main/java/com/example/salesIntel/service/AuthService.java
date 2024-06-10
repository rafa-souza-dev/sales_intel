package com.example.salesIntel.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.example.salesIntel.config.JwtService;
import com.example.salesIntel.controller.responses.LoginResponse;
import com.example.salesIntel.model.User;
import com.example.salesIntel.model.dtos.LoginDTO;
import com.example.salesIntel.repository.UserRepository;
import com.example.salesIntel.utils.SalesException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final UserRepository repository;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	
	public LoginResponse login(LoginDTO login) throws SalesException{
		User user = repository.findByEmail(login.getEmail()).orElseThrow(() -> new SalesException("There is no user associated with this email"));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));
        String jwtToken = jwtService.generateToken(user);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);

        return loginResponse;
	}

}
