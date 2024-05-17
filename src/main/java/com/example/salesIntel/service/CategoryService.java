package com.example.salesIntel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.salesIntel.model.Category;
import com.example.salesIntel.model.dtos.CategoryDTO;
import com.example.salesIntel.repository.CategoryRepository;
import com.example.salesIntel.utils.SalesException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryRepository repository;
	
	public List<Category> getAll(){
		return repository.findAll();
	}
	
	public Category getById(Long id) throws SalesException {
		return repository.findById(id).orElseThrow(
				() -> new SalesException("There is no category associated with this id")) ;
	}
	
	public Category getByName(String name) throws SalesException {
		return repository.findByName(name).orElseThrow(
				() -> new SalesException("There is no category associated with this id")) ;
	}
	
	public void createCategory(CategoryDTO dto) throws SalesException {
		Optional<Category> exist = repository.findByName(dto.getName());
		if(exist.isPresent()) {
			throw new SalesException("This category has already been created");
		}
		Category newCat = new Category();
		newCat.setName(dto.getName());
		repository.save(newCat);
	}
	
	public Category updateCategory(Long id, CategoryDTO dto) throws SalesException {
		Category cat = getById(id);
		Optional<Category> exist = repository.findByName(dto.getName());
		if(exist.isPresent()) {
			throw new SalesException("This category has already been registered");
		}
		cat.setName(dto.getName());		
		return cat;
		
	}
	
	public void deleteCategory(Long id) throws SalesException {
		Category category = getById(id); 
		
		if(!category.getProducts().isEmpty()) {
			throw new SalesException("You cant delete a category with products associated to it");			
		}
		
		repository.delete(category);
	}
	
}
