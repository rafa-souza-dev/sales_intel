package com.example.salesIntel.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.salesIntel.controller.responses.SaleResponse;
import com.example.salesIntel.model.Sale;
import com.example.salesIntel.model.dtos.SaleDTO;
import com.example.salesIntel.service.SaleService;
import com.example.salesIntel.utils.SalesException;

import lombok.RequiredArgsConstructor;

@RequestMapping("/sale")
@RestController
@RequiredArgsConstructor
public class SaleController {
	
	private final SaleService service;
	
	@GetMapping
	public ResponseEntity<List<SaleResponse>> getAll(){
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
	
	@PostMapping
	public ResponseEntity<?> createSale(@RequestBody SaleDTO sale){
		service.createSale(sale);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	private SaleResponse convert (Sale sale) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(sale, SaleResponse.class);
	}

}
