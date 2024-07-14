package com.example.salesIntel.service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import com.example.salesIntel.model.FinancialVO;
import com.example.salesIntel.model.SaleVO;
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
	
	
	public List<Sale> getAllByUserId(Long userId){
		return repository.getAllByUserId(userId);
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
		if (product.getQuantity() < dto.getQuantity()) {
			throw new SalesException("The product has less than " + dto.getQuantity());
		}
		product.setQuantity(product.getQuantity() - dto.getQuantity());
		sale.setQuantity(dto.getQuantity());
		float value = product.getSalePrice() * dto.getQuantity();
		sale.setValue(value);
		repository.save(sale);
	}

	public byte[] generateSalesCsv(Long userId) throws SalesException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		OutputStreamWriter file = new OutputStreamWriter(outputStream);
		List<Product> products = productService.getAllByUserId(userId);
		try {
			StatefulBeanToCsv<SaleVO> beanToCsv = new StatefulBeanToCsvBuilder<SaleVO>(file).build();
			List<SaleVO> saleVOs = products.stream().map(SaleVO::new).toList();
			beanToCsv.write(saleVOs);
			beanToCsv.write(new SaleVO(saleVOs));
			file.flush();
			file.close();
			return outputStream.toByteArray();
		} catch (Exception e) {
			throw new SalesException("Error generating csv file!");
		}
	}

	public byte[] generateFinancialCsv(Long userId) throws SalesException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		OutputStreamWriter file = new OutputStreamWriter(outputStream);
		List<Sale> sales = getAllByUserId(userId);
		sales.sort(Comparator.comparing(Sale::getCreatedAt));
		Sale saleFist = sales.get(0);
		if (saleFist == null) {
			throw new SalesException("No sale enough to generate the report!");
		}
		String monthYear = sales.get(0).getCreatedAt().getMonth() + "/" + sales.get(0).getCreatedAt().getYear();
		List<Sale> salesMonthYear = new ArrayList<>();
		List<FinancialVO> financialVOS = new ArrayList<>();
		for (Sale sale : sales) {
			if ((sale.getCreatedAt().getMonth() + "/" + sale.getCreatedAt().getYear()).equals(monthYear)) {
				salesMonthYear.add(sale);
			} else {
				financialVOS.add(new FinancialVO(monthYear, salesMonthYear));
				monthYear = sale.getCreatedAt().getMonth() + "/" + sale.getCreatedAt().getYear();
				salesMonthYear = new ArrayList<>();
				salesMonthYear.add(sale);
			}
		}
		financialVOS.add(new FinancialVO(monthYear, salesMonthYear));

		try {
			StatefulBeanToCsv<FinancialVO> beanToCsv = new StatefulBeanToCsvBuilder<FinancialVO>(file).build();
			beanToCsv.write(financialVOS);
			file.flush();
			file.close();
			return outputStream.toByteArray();
		} catch (Exception e) {
			throw new SalesException("Error generating csv file!");
		}
	}

	public void deleteSales(Long id) {
		Sale sale = getById(id);
		repository.delete(sale);
	}
}
