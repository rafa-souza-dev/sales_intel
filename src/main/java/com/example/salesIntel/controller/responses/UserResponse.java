package com.example.salesIntel.controller.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
	
	private Long id;
	
	private String company;
	
	private String email;
	
	private String password;
}
