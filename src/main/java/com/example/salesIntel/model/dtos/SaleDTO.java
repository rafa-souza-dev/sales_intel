package com.example.salesIntel.model.dtos;

import java.util.List;

import com.example.salesIntel.model.SalesProducts;

import lombok.Getter;

@Getter
public class SaleDTO {
	
	private float value;
	
	private List<SalesProducts> salesProducts;

}
