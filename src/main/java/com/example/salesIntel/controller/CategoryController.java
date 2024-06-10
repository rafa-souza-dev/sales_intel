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

import com.example.salesIntel.controller.responses.CategoryResponse;
import com.example.salesIntel.model.Category;
import com.example.salesIntel.model.dtos.CategoryDTO;
import com.example.salesIntel.service.CategoryService;
import com.example.salesIntel.utils.SalesException;

import lombok.RequiredArgsConstructor;

@RequestMapping("/category")
@RestController
@RequiredArgsConstructor
public class CategoryController {
	
	private final CategoryService service;
	
	@GetMapping
	public ResponseEntity<List<CategoryResponse>> getAll(){
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
	
	@GetMapping("/name/{name}")
	public ResponseEntity<?> getByName(@PathVariable String name){
		try {
			return ResponseEntity.ok(convert(service.getByName(name)));
		} catch (SalesException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping
	public ResponseEntity<?> createCategory(@RequestBody CategoryDTO dto){
		try {
			service.createCategory(dto);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (SalesException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO dto){
		try {
			return ResponseEntity.ok(convert(service.updateCategory(id, dto)));
		} catch (SalesException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable Long id){
		try {
			service.deleteCategory(id);
			return ResponseEntity.noContent().build();
		} catch (SalesException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	private CategoryResponse convert (Category category) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(category, CategoryResponse.class);
	}

}
