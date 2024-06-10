package com.example.salesIntel.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.salesIntel.model.Category;
import com.example.salesIntel.model.User;
import com.example.salesIntel.model.Product;
import com.example.salesIntel.model.dtos.ProductDTO;
import com.example.salesIntel.repository.ProductRepository;
import com.example.salesIntel.utils.SalesException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	
	private final ProductRepository repository;
	
	private final CategoryService categoryService;
	
	private final UserService userService;
	
	public List<Product> getAll(){
		return repository.findAll();
	}
	
	public Product getById(Long id) throws SalesException {
		return repository.findById(id).orElseThrow(() -> 
			new SalesException("There is not product associated with this id"));
	}
	
	public List<Product> getProductByUserId(Long id) throws SalesException {
		User user = userService.getUserById(id);		
		return user.getProducts();		
	}
	
	public List<Product> getProductByUsername(String email) throws SalesException {
		User user = userService.getUserByEmail(email);		
		return user.getProducts();		
	}
		
	public List<Product> getProductByCategoryId(Long id) throws SalesException {
		Category category = categoryService.getById(id);
		return category.getProducts();
	}

	public void createProduct(ProductDTO dto) throws SalesException {
		User user = userService.getUserById(dto.getUserId());
		
		Category cat = categoryService.getById(dto.getCategoryId());		
		
		Product product = new Product();
		product.setName(dto.getName());
		product.setQuantity(dto.getQuantity());
		product.setExpiration(dto.getExpiration());
		product.setPurchasePrice(dto.getPurchasePrice());
		product.setSalePrice(dto.getSalePrice());
		product.setUnit(dto.getUnit());
		product.setBatch(dto.getBatch());
		product.setCategory(cat);
		product.setUser(user);
		repository.save(product);
	}
	
	public Product updateProduct(Long id, ProductDTO dto) throws SalesException {
		User user = userService.getUserById(dto.getUserId());
		
		Category cat = categoryService.getById(dto.getCategoryId());		
		
		Product product = getById(id);
		
		product.setName(dto.getName());
		product.setQuantity(dto.getQuantity());
		product.setExpiration(dto.getExpiration());
		product.setPurchasePrice(dto.getPurchasePrice());
		product.setSalePrice(dto.getSalePrice());
		product.setUnit(dto.getUnit());
		product.setBatch(dto.getBatch());
		product.setCategory(cat);
		product.setUser(user);
		repository.save(product);
		
		return product;
	}
	
	public void deleteProduct(Long id) throws SalesException {
		Product product = getById(id);
		repository.delete(product);
	}
	
}
