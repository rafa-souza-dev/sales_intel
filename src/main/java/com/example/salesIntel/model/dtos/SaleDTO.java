package com.example.salesIntel.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SaleDTO {
	
	private float value;

	private int quantity;
	
	private Long productId;

}
