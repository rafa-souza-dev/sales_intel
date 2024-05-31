package com.example.salesIntel.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.salesIntel.model.Product;
import com.example.salesIntel.model.Sale;
import com.example.salesIntel.model.SalesProducts;
import com.example.salesIntel.model.dtos.SalesProductsDTO;
import com.example.salesIntel.repository.SalesProductsRepository;
import com.example.salesIntel.utils.SalesException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SalesProductsService {

	private final SalesProductsRepository repository;
	
	private final ProductService productService;
	
	private final SaleService saleService;
	
	public List<SalesProducts> getAll(){
		return repository.findAll();
	}
	
	public SalesProducts getById(Long id) throws SalesException {
		return repository.findById(id).orElseThrow(() 
				-> new SalesException("There is no Sales Products associated with this id"));
	}
	
	public List<SalesProducts> getSalesProductsBySale(Long id) throws SalesException{
		Sale sale = saleService.getById(id);
		return sale.getSalesProducts();
	}	
	
	
	public SalesProducts createSalesProducts(SalesProductsDTO dto) throws SalesException {		
		Product product = productService.getById(dto.getProductId());
		
		if(dto.getQuantity() > product.getQuantity()) {
			throw new SalesException("Quantity unavailable");
		}
		
		SalesProducts saleP = new SalesProducts();
		
		saleP.setQuantity(dto.getQuantity());
		saleP.setProduct(product);
		repository.save(saleP);
		
		return saleP;
	}
	
	
}
