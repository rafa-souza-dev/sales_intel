package com.example.salesIntel.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.example.salesIntel.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.example.salesIntel.config.JwtService;
import com.example.salesIntel.controller.responses.ProductResponse;
import com.example.salesIntel.model.Product;
import com.example.salesIntel.model.dtos.ProductDTO;
import com.example.salesIntel.service.ProductService;
import com.example.salesIntel.utils.SalesException;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequestMapping("/products")
@RestController
@RequiredArgsConstructor
public class ProductController {
	
	private final ProductService service;
	
	private final JwtService jwtService;
	
	
	@GetMapping
	public ResponseEntity<List<ProductResponse>> getAll(@AuthenticationPrincipal User user){
		return ResponseEntity.ok(service.getAllByUserId(user.getId()).stream().map(this::convert).collect(Collectors.toList()));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id){
		try {
			return ResponseEntity.ok(convert(service.getById(id)));
		} catch (SalesException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	@GetMapping("/token")
	public ResponseEntity<?> getByToken(HttpServletRequest request){
		String email = jwtService.extractUsername(request.getHeader("Authorization").substring(7));
		try {
			return ResponseEntity.ok(service.getProductByUsername(email).stream().map(this::convert).collect(Collectors.toList()));
		} catch (SalesException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	@GetMapping("user/{id}")
	public ResponseEntity<?> getByUser(@PathVariable Long id){
		try {
			return ResponseEntity.ok(service.getProductByUserId(id).stream().map(this::convert).collect(Collectors.toList()));
		} catch (SalesException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("category/{id}")
	public ResponseEntity<?> getByCategory(@PathVariable Long id){
		try {
			return ResponseEntity.ok(service.getProductByCategoryId(id).stream().map(this::convert).collect(Collectors.toList()));
		} catch (SalesException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping
	public ResponseEntity<?> createProduct(@RequestBody ProductDTO dto){
		try {
			service.createProduct(dto);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (SalesException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductDTO dto){
		try {
			return ResponseEntity.ok(convert(service.updateProduct(id, dto)));
		} catch (SalesException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> disableProduct(@PathVariable Long id){
		try {
			service.disableProduct(id);
			return ResponseEntity.noContent().build();
		} catch (SalesException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@Deprecated
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable Long id){
		return ResponseEntity.status(HttpStatus.GONE).build();
	}
	
	private ProductResponse convert (Product product) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(product, ProductResponse.class);
	}

}
