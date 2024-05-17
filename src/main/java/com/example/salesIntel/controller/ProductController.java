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

import com.example.salesIntel.controller.responses.ProductResponse;
import com.example.salesIntel.model.Product;
import com.example.salesIntel.model.dtos.ProductDTO;
import com.example.salesIntel.service.ProductService;
import com.example.salesIntel.utils.SalesException;

import lombok.RequiredArgsConstructor;

@RequestMapping("/products")
@RestController
@RequiredArgsConstructor
public class ProductController {
	
	private final ProductService service;
	
	
	@GetMapping
	public ResponseEntity<List<ProductResponse>> getAll(){
		return ResponseEntity.ok(service.getAll().stream().map(this::convert).collect(Collectors.toList()));
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id){
		try {
			return ResponseEntity.ok(convert(service.getById(id)));
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
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable Long id){
		try {
			service.deleteProduct(id);
			return ResponseEntity.noContent().build();
		} catch (SalesException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	private ProductResponse convert (Product product) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(product, ProductResponse.class);
	}

}
