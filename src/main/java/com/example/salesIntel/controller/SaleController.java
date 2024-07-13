package com.example.salesIntel.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.salesIntel.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
	public ResponseEntity<List<SaleResponse>> getAll(@AuthenticationPrincipal User user){
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
	
	@PostMapping
	public ResponseEntity<?> createSale(@RequestBody SaleDTO sale){
		try {
			service.createSale(sale);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (SalesException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/csv")
	public ResponseEntity<?> createSalesCsv(@AuthenticationPrincipal User user){
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("text/csv"));
			headers.setContentDisposition(ContentDisposition.attachment()
					.filename("relatorio-" + LocalDateTime.now() + ".csv").build());
			return ResponseEntity.ok().headers(headers).body(service.generateSalesCsv(user.getId()));
		} catch (SalesException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> createSalesCsv(@PathVariable Long id){
		try {
			service.deleteSales(id);
			return ResponseEntity.noContent().build();
		} catch (SalesException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	private SaleResponse convert (Sale sale) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(sale, SaleResponse.class);
	}

}
