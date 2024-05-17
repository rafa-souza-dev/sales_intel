package com.example.salesIntel.controller.responses;

import com.example.salesIntel.model.Sale;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesProductsResponse {
	
    private Long id;

    private Integer quantity;

    private ProductResponse product;

    private Sale sale;

}
