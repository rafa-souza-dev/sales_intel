package com.example.salesIntel.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.salesIntel.model.Sale;
import com.example.salesIntel.model.SalesProducts;
import com.example.salesIntel.model.dtos.SaleDTO;
import com.example.salesIntel.repository.SaleRepository;
import com.example.salesIntel.utils.SalesException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaleService {
	
	private final SaleRepository repository;
	
	
	public List<Sale> getAll(){
		return repository.findAll();		
	}
	
	public Sale getById(Long id) throws SalesException {
		return repository.findById(id).orElseThrow(() 
				-> new SalesException("There is no sale associated to this id"));
	}
	
	public void createSale(SaleDTO dto) {
		Sale sale = new Sale();
		float value = 0;
		
		sale.setSalesProducts(dto.getSalesProducts());
		
		for(SalesProducts sales : dto.getSalesProducts()) {
			value += sales.getProduct().getSalePrice() * sales.getQuantity();			
		}
		sale.setValue(value);
		
		repository.save(sale);
	}
	

 
}
