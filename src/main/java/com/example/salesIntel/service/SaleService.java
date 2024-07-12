package com.example.salesIntel.service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import com.example.salesIntel.controller.responses.SaleResponse;
import com.example.salesIntel.model.SaleCsv;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.springframework.stereotype.Service;

import com.example.salesIntel.model.Product;
import com.example.salesIntel.model.Sale;
import com.example.salesIntel.model.dtos.SaleDTO;
import com.example.salesIntel.repository.SaleRepository;
import com.example.salesIntel.utils.SalesException;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SaleService {
	
	private final SaleRepository repository;

	private final ProductService productService;
	
	
	public List<Sale> getAll(){
		return repository.findAll();		
	}
	
	public Sale getById(Long id) throws SalesException {
		return repository.findById(id).orElseThrow(() 
				-> new SalesException("There is no sale associated to this id"));
	}
	
	@Transactional
    public void createSale(SaleDTO dto) throws SalesException {
		Sale sale = new Sale();
		Product product = productService.getById(dto.getProductId());
		sale.setProduct(product);
		sale.setQuantity(dto.getQuantity());
		float value = product.getSalePrice() * dto.getQuantity();
		sale.setValue(value);
		repository.save(sale);
	}

	public byte[] generateSalesCsv() throws SalesException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		OutputStreamWriter file = new OutputStreamWriter(outputStream);
		List<Sale> sales = repository.findAll();

		try {
			StatefulBeanToCsv<SaleCsv> beanToCsv = new StatefulBeanToCsvBuilder<SaleCsv>(file).build();
			beanToCsv.write(sales.stream().map(SaleCsv::new));
			file.flush();
			file.close();
			return outputStream.toByteArray();
		} catch (Exception e) {
			throw new SalesException("Error generating csv file");
		}
	}
}
